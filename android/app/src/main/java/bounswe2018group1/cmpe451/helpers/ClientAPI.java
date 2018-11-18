package bounswe2018group1.cmpe451.helpers;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

public class ClientAPI {

    private static ClientAPI mInstance = null;
    private VolleySingleton volleySingleton;

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
                new MyResponseListener(view),
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        System.err.println("writeAuthor returned error response!");
                        error.printStackTrace();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq, VolleySingleton.Tags.PROFILE_REQ_TAG, context);

    }

    private class MyResponseListener implements Response.Listener {
        TextView view;

        MyResponseListener(TextView view) {
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
                } catch (JSONException e) {
                    view.setText("(user not found)");
                    e.printStackTrace();
                }
            } else {
                System.err.println("writeAuthor unexpected response!");
                System.err.println("Response: " + response.toString());
            }
        }
    }

}
