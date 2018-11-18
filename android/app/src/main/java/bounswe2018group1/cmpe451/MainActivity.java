package bounswe2018group1.cmpe451;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import bounswe2018group1.cmpe451.fragments.FeedFragment;
import bounswe2018group1.cmpe451.fragments.MapFragment;
import bounswe2018group1.cmpe451.fragments.ProfileFragment;
import bounswe2018group1.cmpe451.fragments.SearchFragment;
import bounswe2018group1.cmpe451.helpers.NullResponseJsonObjectRequest;
import bounswe2018group1.cmpe451.helpers.URLs;
import bounswe2018group1.cmpe451.helpers.VolleySingleton;

public class MainActivity extends AppCompatActivity {

    private FeedFragment fragmentFeed;
    private MapFragment fragmentMap;
    private ProfileFragment fragmentProfile;
    private SearchFragment fragmentSearch;
    private InputMethodManager inputManager = null;
    private TabLayout tabLayout = null;
    private VolleySingleton volleySingleton = null;

    private boolean doubleBackToExitPressedOnce = false;
    private boolean isProfileLoaded = false;
    private String sessionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Messages from old activity (Login)
        sessionID = getIntent().getExtras().getString("SessionID");

        // Keyboard
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // Tabs
        if (tabLayout == null) tabLayout = findViewById(R.id.tabLayout);
        if (volleySingleton == null) volleySingleton = VolleySingleton.getInstance(this);

        tabLayout.addTab(tabLayout.newTab().setText("Feed"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.addTab(tabLayout.newTab().setText("Profıle"));
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Create"));

        // Create fragments
        fragmentFeed = new FeedFragment();
        fragmentMap = new MapFragment();
        fragmentProfile = new ProfileFragment();
        fragmentSearch = new SearchFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.mainLayout, fragmentFeed, "Feed");
        transaction.add(R.id.mainLayout, fragmentMap, "Map");
        transaction.add(R.id.mainLayout, fragmentProfile, "Profile");
        transaction.add(R.id.mainLayout, fragmentSearch, "Search");
        transaction.hide(fragmentMap);
        transaction.hide(fragmentProfile);
        transaction.hide(fragmentSearch);
        transaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                // Remove keyboard while switching tabs
                if (getCurrentFocus() != null)
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(fragmentFeed);
                        break;
                    case 1:
                        replaceFragment(fragmentMap);
                        break;
                    case 2:
                        if (!isProfileLoaded) {
                            loadProfile();
                        }
                        replaceFragment(fragmentProfile);
                        break;
                    case 3:
                        replaceFragment(fragmentSearch);
                        break;
                    case 4:
                        replaceFragment(null);
                        Intent i = new Intent(MainActivity.this, MemoryCreateActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragmentFeed);
        transaction.hide(fragmentMap);
        transaction.hide(fragmentProfile);
        transaction.hide(fragmentSearch);
        if (fragment != null) {
            transaction.show(fragment);
        }
        transaction.commit();
    }

    public void logout() {
        JSONObject postParams = new JSONObject();
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(
                Request.Method.POST,
                URLs.URL_LOGOUT, postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response == null) {
                            // Clear and return to login
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        } else if (response instanceof JSONObject) {
                            //Success Callback
                            JSONObject r = (JSONObject) response;
                            System.out.println("Response: " + r.toString());
                        } else {
                            System.out.println("Response: " + response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        Toast.makeText(getApplicationContext(), "Logout fail!", Toast.LENGTH_LONG).show();
                        System.err.println("Error in logout!");
                        error.printStackTrace();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq, VolleySingleton.Tags.LOGOUT_REQ_TAG, this);
    }

    private void loadProfile() {
        JSONObject postParams = new JSONObject();
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(Request.Method.GET, URLs.URL_USER, postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response == null) {

                        } else if (response instanceof JSONObject) {
                            //Success Callback
                            JSONObject r = (JSONObject) response;
                            System.out.println("Response: " + r.toString());
                            try {
                                // Set fields
                                fragmentProfile.setFields(r.getString("firstName"), r.getString("lastName"), r.getString("nickname"), r.getString("email"));
                                isProfileLoaded = true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Response: " + response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        Toast.makeText(getApplicationContext(), "Profile load fail!", Toast.LENGTH_LONG).show();
                        System.err.println("Error in profile!");
                        error.printStackTrace();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq, VolleySingleton.Tags.PROFILE_REQ_TAG, this);

    }

    public void updateProfile(String $firstName, String $lastName, String $nickname, String $email, String $oldPassword, String $newPassword) {
        // Remove keyboard
        if (getCurrentFocus() != null)
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        JSONObject postParams = new JSONObject();
        try {
            postParams.put("firstName", $firstName);
            postParams.put("lastName", $lastName);
            postParams.put("nickname", $nickname);
            postParams.put("email", $email);
            postParams.put("oldPassword", $oldPassword);
            postParams.put("newPassword", $newPassword);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(Request.Method.GET, URLs.URL_USER, postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response == null) {

                        } else if (response instanceof JSONObject) {
                            //Success Callback
                            JSONObject r = (JSONObject) response;
                            System.out.println("Response: " + r.toString());
                        } else {
                            System.out.println("Response: " + response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        Toast.makeText(getApplicationContext(), "Profile update fail!", Toast.LENGTH_LONG).show();
                        System.err.println("Error in update profile!");
                        error.printStackTrace();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq, VolleySingleton.Tags.PROFILE_UPD_TAG, this);
    }

}
