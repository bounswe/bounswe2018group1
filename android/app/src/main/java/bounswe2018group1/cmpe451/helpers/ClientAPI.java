package bounswe2018group1.cmpe451.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import bounswe2018group1.cmpe451.LoginActivity;
import bounswe2018group1.cmpe451.MainActivity;
import bounswe2018group1.cmpe451.R;
import bounswe2018group1.cmpe451.fragments.ProfileFragment;

public class ClientAPI {

    private static ClientAPI mInstance = null;
    private VolleySingleton volleySingleton;

    public static class Tags {
        public static final String LOGIN_REQ_TAG = "login_tag";
        public static final String REGISTER_REQ_TAG = "register_tag";
        public static final String LOGOUT_REQ_TAG = "logout_tag";
        public static final String USER_REQ_TAG = "user_tag";
        public static final String USER_INFO_TAG = "user_info_tag";
        public static final String MEMORY_UPD_TAG = "memory_update_tag";
        public static final String MEMORY_CREATE_TAG = "memory_create_tag";
    }

    private ClientAPI(Context context) {
        this.volleySingleton = VolleySingleton.getInstance(context);
    }

    public static synchronized ClientAPI getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ClientAPI(context);
        }
        return mInstance;
    }

    public void printAvatar(ImageView view, String userId, Context context) {
        if(userId == null || userId.isEmpty()) return;
        org.json.JSONObject postParams = new org.json.JSONObject();
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(Request.Method.GET, URLs.URL_USER_withID(userId), postParams,
                new PrintAvatarResponseListener(view),
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        System.err.println("printAvatar returned error response!");
                        if (error.networkResponse.data != null) {
                            try {
                                String jsonString = new String(error.networkResponse.data,
                                        HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                                System.err.println(jsonString);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        error.printStackTrace();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq, Tags.USER_REQ_TAG, context);

    }

    private class PrintAvatarResponseListener implements Response.Listener {
        private ImageView imageView;

        PrintAvatarResponseListener(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public void onResponse(Object response) {
            if (response == null) {
                System.err.println("printAvatar failed!");
            } else if (response instanceof org.json.JSONObject) {
                //Success Callback
                org.json.JSONObject r = (org.json.JSONObject) response;
                System.out.println("Response: " + r.toString());
                if(!r.isNull("profilePictureUrl")) {
                    try {
                        Uri itemUri = Uri.parse(r.getString("profilePictureUrl"));
                        Picasso.get()
                                .load(itemUri)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.error)
                                .fit()
                                .centerInside()
                                .transform(new CircleTransform())
                                .into(imageView);
                    } catch (org.json.JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.err.println("printAvatar unexpected response!");
                System.err.println("Response: " + response.toString());
            }
        }
    }

    public void cancelAll(@NotNull Object tag, Context context) {
        volleySingleton.getRequestQueue(context).cancelAll(tag);
    }

    public void sendLoginRequest(String loginName, String password, final Context context) {
        org.json.JSONObject postParams = new org.json.JSONObject();
        try {
            postParams.put("password", password);
            // Check if user has entered nick or email
            if (loginName.contains(".")) {
                postParams.put("nickname", "");
                postParams.put("email", loginName.trim());
            } else {
                postParams.put("nickname", loginName.trim());
                postParams.put("email", "");
            }
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(
                Request.Method.POST,
                URLs.URL_LOGIN, postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response == null) {
                            System.err.println("sendLoginRequest failed!");
                        } else if (response instanceof org.json.JSONObject) {
                            //Success Callback
                            org.json.JSONObject r = (org.json.JSONObject) response;
                            System.out.println("Response: " + r.toString());
                            // Launch member activity
                            try {
                                Intent i = new Intent(context, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.putExtra("SessionID", r.getString("jsessionID"));
                                context.startActivity(i);
                            } catch (org.json.JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.err.println("sendLoginRequest unexpected response!");
                            System.out.println("Response: " + response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        Toast.makeText(context, "Failed to login!", Toast.LENGTH_LONG).show();
                        System.err.println("sendLoginRequest returned error response!");
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String jsonString = new String(error.networkResponse.data,
                                        HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                                System.err.println(jsonString);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        error.printStackTrace();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq, Tags.LOGIN_REQ_TAG, context);
    }

    public void sendRegisterRequest(String email, String firstName, String lastName,
                                     String nickname, String password, final Context context)
            throws org.json.JSONException {
        org.json.JSONObject postParams = new org.json.JSONObject();
        postParams.put("email", email.trim());
        postParams.put("firstName", firstName.trim());
        postParams.put("lastName", lastName.trim());
        postParams.put("nickname", nickname.trim());
        postParams.put("password", password);

        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(Request.Method.POST,
                URLs.URL_REGISTER, postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_LONG).show();
                        if (response == null) {
                            System.err.println("sendRegisterRequest failed!");
                        } else if (response instanceof org.json.JSONObject) {
                            //Success Callback
                            org.json.JSONObject r = (org.json.JSONObject) response;
                            System.out.println("Response: " + r.toString());
                        } else {
                            System.err.println("sendRegisterRequest unexpected response!");
                            System.err.println("Response: " + response.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        Toast.makeText(context, "Failed to register!", Toast.LENGTH_LONG).show();
                        System.err.println("sendRegisterRequest returned error response!");
                        if (error.networkResponse.data != null) {
                            try {
                                String jsonString = new String(error.networkResponse.data,
                                        HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                                System.err.println(jsonString);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        error.printStackTrace();
                    }
                });
        volleySingleton.addToRequestQueue(jsonObjReq, Tags.REGISTER_REQ_TAG, context);
    }

    public void logout(final Context context) {
        org.json.JSONObject postParams = new org.json.JSONObject();
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(
                Request.Method.POST,
                URLs.URL_LOGOUT, postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response == null) {
                            // Clear and return to login
                            Intent i = new Intent(context, LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(i);
                        } else if (response instanceof org.json.JSONObject) {
                            //Success Callback
                            org.json.JSONObject r = (org.json.JSONObject) response;
                            System.out.println("Response: " + r.toString());
                        } else {
                            System.err.println("logout unexpected response!");
                            System.err.println("Response: " + response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        Toast.makeText(context, "Logout fail!", Toast.LENGTH_LONG).show();
                        System.err.println("logout returned error response!");
                        if (error.networkResponse.data != null) {
                            try {
                                String jsonString = new String(error.networkResponse.data,
                                        HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                                System.err.println(jsonString);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        error.printStackTrace();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq, Tags.LOGOUT_REQ_TAG, context);
    }

    /*public void loadProfile(final ProfileFragment profileFragment) {
        org.json.JSONObject postParams = new org.json.JSONObject();
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(Request.Method.GET, URLs.URL_USER, postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response == null) {
                            System.err.println("Empty response from URL_USER");
                        } else if (response instanceof org.json.JSONObject) {
                            //Success Callback
                            org.json.JSONObject r = (org.json.JSONObject) response;
                            System.out.println("Response: " + r.toString());
                            try {
                                // Set fields
                                profileFragment.setFields(
                                        r.getString("firstName"),
                                        r.getString("lastName"),
                                        r.getString("nickname"),
                                        r.getString("email"),
                                        r.getString("bio"),
                                        r.getString("birthday"),
                                        r.getString("gender"),
                                        r.getJSONArray("listOfLocations"));
                            } catch (org.json.JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.err.println("loadProfile unexpected response!");
                            System.err.println("Response: " + response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        Toast.makeText(profileFragment.getContext(), "Profile load fail!", Toast.LENGTH_LONG).show();
                        System.err.println("loadProfile returned error response!");
                        if (error.networkResponse.data != null) {
                            try {
                                String jsonString = new String(error.networkResponse.data,
                                        HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                                System.err.println(jsonString);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        error.printStackTrace();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq, Tags.USER_REQ_TAG, profileFragment.getContext());

    }*/

    /*public void updateProfile(String $firstName, String $lastName, String $nickname, String $bio, String $birth, String $gender, String $locations, String $email, String $oldPassword, String $newPassword, final ProfileFragment profileFragment) {
        org.json.JSONObject postParams = new org.json.JSONObject();
        try {
            postParams.put("firstName", $firstName);
            postParams.put("lastName", $lastName);
            postParams.put("nickname", $nickname);
            postParams.put("bio", $bio);
            JSONArray locationArray = new JSONArray();
            JSONObject location = new JSONObject();
            location.put("locationName", $locations);
            locationArray.put(location);
            postParams.put("listOfLocations", locationArray);
            postParams.put("birthday", $birth);
            postParams.put("gender", $gender);
            postParams.put("email", $email);
            if (!$oldPassword.isEmpty() && !$newPassword.isEmpty() && !$oldPassword.equals($newPassword)) {
                postParams.put("oldPassword", $oldPassword);
                postParams.put("newPassword", $newPassword);
            }
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(Request.Method.PUT, URLs.URL_USER_INFO, postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response == null) {
                            profileFragment.loadProfile();
                        } else if (response instanceof org.json.JSONObject) {
                            //Success Callback
                            org.json.JSONObject r = (org.json.JSONObject) response;
                            System.out.println("Response: " + r.toString());
                        } else {
                            System.err.println("updateProfile unexpected response!");
                            System.err.println("Response: " + response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        System.err.println("updateProfile returned error response!");
                        if (error.networkResponse.data != null) {
                            try {
                                String jsonString = new String(error.networkResponse.data,
                                        HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                                System.err.println(jsonString);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        error.printStackTrace();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq, Tags.USER_INFO_TAG, profileFragment.getContext());
    }*/

    public void createMemory(int startDateYYYY, int startDateMM, int startDateDD, int startDateHH,
                             int endDateYYYY, int endDateMM, int endDateDD, int endDateHH,
                             String headline, ArrayList<String> listOfItems, String[] listOfLocations, final Context context) {
        // Get current date and time
        DateFormat createTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String createDate = createTime.format(Calendar.getInstance().getTime());
        org.json.JSONObject postParams = new org.json.JSONObject();
        try {
            postParams.put("dateOfCreation", createDate);
            postParams.put("startDateYYYY", startDateYYYY);
            postParams.put("startDateMM", startDateMM);
            postParams.put("startDateDD", startDateDD);
            postParams.put("startDateHH", startDateHH);
            postParams.put("endDateYYYY", endDateYYYY);
            postParams.put("endDateMM", endDateMM);
            postParams.put("endDateDD", endDateDD);
            postParams.put("endDateHH", endDateHH);
            postParams.put("headline", headline);
            postParams.put("updatedTime", createDate);
            // TODO Do or remove from backend, Tags
            JSONArray tagArray = new JSONArray();
            JSONObject tag = new JSONObject();
            tag.put("text", "Tag");
            tagArray.put(tag);
            postParams.put("listOfTags", tagArray);
            // Convert item strings to JSON Array
            JSONArray itemArray = new JSONArray();
            for (String item : listOfItems) {
                JSONObject items = new JSONObject();
                items.put("body", item);
                itemArray.put(items);
            }
            postParams.put("listOfItems", itemArray);
            // Convert location strings to JSON Array
            JSONArray locationArray = new JSONArray();
            for (String location : listOfLocations) {
                JSONObject locations = new JSONObject();
                locations.put("locationName", location);
                locationArray.put(locations);
            }
            postParams.put("listOfLocations", locationArray);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(Request.Method.POST, URLs.URL_MEMORY, postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response == null) {
                            System.err.println("ID did not return");
                        } else if (response instanceof org.json.JSONObject) {
                            //Success Callback
                            org.json.JSONObject r = (org.json.JSONObject) response;
                            System.out.println("Response: " + r.toString());
                        } else {
                            System.err.println("createMemory unexpected response!");
                            System.err.println("Response: " + response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        System.err.println("createMemory returned error response!");
                        if (error.networkResponse.data != null) {
                            try {
                                String jsonString = new String(error.networkResponse.data,
                                        HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                                System.err.println(jsonString);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        error.printStackTrace();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq, Tags.MEMORY_CREATE_TAG, context);
    }

    public void getMemoryAll(int pageNum, int pageSize, final ServerCallBack serverCallBack,
                                  final Context context) {
        org.json.JSONObject postParams = new org.json.JSONObject();
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(Request.Method.GET,
                URLs.URL_MEMORY_ALL + "?page=" + pageNum + "&size=" + pageSize, postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response == null) {
                            System.err.println("getMemoryAll failed!");
                            serverCallBack.onError();
                        } else if (response instanceof org.json.JSONObject) {
                            //Success Callback
                            org.json.JSONObject r = (org.json.JSONObject) response;
                            serverCallBack.onSuccess(r);
                            System.out.println("Response: " + r.toString());
                        } else {
                            System.err.println("getMemoryAll unexpected response!");
                            System.err.println("Response: " + response.toString());
                            serverCallBack.onError();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        System.err.println("getMemoryAll returned error response!");
                        if (error.networkResponse.data != null) {
                            try {
                                String jsonString = new String(error.networkResponse.data,
                                        HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                                System.err.println(jsonString);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        error.printStackTrace();
                        serverCallBack.onError();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq, Tags.MEMORY_UPD_TAG, context);
    }

    public void getCurrentUser(final ServerCallBack serverCallBack,
                             final Context context) {
        org.json.JSONObject postParams = new org.json.JSONObject();
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(Request.Method.GET, URLs.URL_USER, postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response == null) {
                            System.err.println("getCurrentUser failed!");
                            serverCallBack.onError();
                        } else if (response instanceof org.json.JSONObject) {
                            //Success Callback
                            org.json.JSONObject r = (org.json.JSONObject) response;
                            serverCallBack.onSuccess(r);
                            System.out.println("Response: " + r.toString());
                        } else {
                            System.err.println("getCurrentUser unexpected response!");
                            System.err.println("Response: " + response.toString());
                            serverCallBack.onError();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        System.err.println("getCurrentUser returned error response!");
                        if (error.networkResponse.data != null) {
                            try {
                                String jsonString = new String(error.networkResponse.data,
                                        HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                                System.err.println(jsonString);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        error.printStackTrace();
                        serverCallBack.onError();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq, Tags.USER_REQ_TAG, context);
    }

}
