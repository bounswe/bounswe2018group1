package bounswe2018group1.cmpe451.helpers;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;

import bounswe2018group1.cmpe451.LoginActivity;
import bounswe2018group1.cmpe451.MainActivity;
import bounswe2018group1.cmpe451.fragments.ProfileFragment;

public class ClientAPI {

    private static ClientAPI mInstance = null;
    private VolleySingleton volleySingleton;

    public static class Tags {
        public static final String LOGIN_REQ_TAG = "login_tag";
        public static final String REGISTER_REQ_TAG = "register_tag";
        public static final String LOGOUT_REQ_TAG = "logout_tag";
        public static final String PROFILE_REQ_TAG = "profile_tag";
        public static final String PROFILE_UPD_TAG = "profile_update_tag";
        public static final String MEMORY_UPD_TAG = "memory_update_tag";
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

    public void writeAuthor(TextView view, String userId, Context context) {
        org.json.JSONObject postParams = new org.json.JSONObject();
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(Request.Method.GET, URLs.URL_USER_withID(userId), postParams,
                new WriteAuthorResponseListener(view),
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        System.err.println("writeAuthor returned error response!");
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
        volleySingleton.addToRequestQueue(jsonObjReq, Tags.PROFILE_REQ_TAG, context);

    }

    private class WriteAuthorResponseListener implements Response.Listener {
        private TextView view;

        WriteAuthorResponseListener(TextView view) {
            this.view = view;
        }

        @Override
        public void onResponse(Object response) {
            if (response == null) {
                System.err.println("writeAuthor failed!");
            } else if (response instanceof org.json.JSONObject) {
                //Success Callback
                org.json.JSONObject r = (org.json.JSONObject) response;
                System.out.println("Response: " + r.toString());
                try {
                    String[] fullName = new String[]{r.getString("firstName"), r.getString("lastName")};
                    view.setText(StringUtility.join(" ", fullName));
                } catch (org.json.JSONException e) {
                    view.setText("(user not found)");
                    e.printStackTrace();
                }
            } else {
                System.err.println("writeAuthor unexpected response!");
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
                        System.err.println("sendLoginRequest returned error response!");
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
                        Toast.makeText(context, "Registration fail!", Toast.LENGTH_LONG).show();
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

    public void loadProfile(final ProfileFragment profileFragment) {
        org.json.JSONObject postParams = new org.json.JSONObject();
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(Request.Method.GET, URLs.URL_USER, postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response == null) {
                            System.err.println("loadProfile failed!");
                        } else if (response instanceof org.json.JSONObject) {
                            //Success Callback
                            org.json.JSONObject r = (org.json.JSONObject) response;
                            System.out.println("Response: " + r.toString());
                            try {
                                // Set fields
                                profileFragment.setFields(r.getString("firstName"), r.getString("lastName"), r.getString("nickname"), r.getString("email"));
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
        volleySingleton.addToRequestQueue(jsonObjReq, Tags.PROFILE_REQ_TAG, profileFragment.getContext());

    }

    public void updateProfile(String $firstName, String $lastName, String $nickname, String $email,
                              String $oldPassword, String $newPassword, final Context context) {

        org.json.JSONObject postParams = new org.json.JSONObject();
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
                            System.out.println("updateProfile success!");
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
                        Toast.makeText(context, "Profile update fail!", Toast.LENGTH_LONG).show();
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
        volleySingleton.addToRequestQueue(jsonObjReq, Tags.PROFILE_UPD_TAG, context);
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

}