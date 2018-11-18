package bounswe2018group1.cmpe451.helpers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.net.CookieHandler;
import java.net.CookieManager;

public class VolleySingleton {
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;

    public static class Tags {
        public static final String LOGIN_REQ_TAG = "login_tag";
        public static final String REGISTER_REQ_TAG = "register_tag";
        public static final String LOGOUT_REQ_TAG = "logout_tag";
        public static final String PROFILE_REQ_TAG = "profile_tag";
        public static final String PROFILE_UPD_TAG = "profile_update_tag";
    }

    private VolleySingleton(Context context) {
        mRequestQueue = getRequestQueue(context);
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            CookieHandler.setDefault(new CookieManager());
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag, Context context) {
        req.setTag(tag);
        getRequestQueue(context).add(req);
    }

    public <T> void addToRequestQueue(Request<T> req, Context context) {
        getRequestQueue(context).add(req);
    }
}
