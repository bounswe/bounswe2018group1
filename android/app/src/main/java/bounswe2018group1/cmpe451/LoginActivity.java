package bounswe2018group1.cmpe451;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import bounswe2018group1.cmpe451.helpers.NullResponseJsonObjectRequest;
import bounswe2018group1.cmpe451.helpers.URLs;
import bounswe2018group1.cmpe451.helpers.VolleySingleton;

public class LoginActivity extends AppCompatActivity {

    private TextView textViewSignup = null;
    private TextView editTextLName = null;
    private TextView editTextLPassword = null;
    private Button buttonLogin = null;
    private VolleySingleton volleySingleton = null;
    private InputMethodManager inputManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (textViewSignup == null) textViewSignup = findViewById(R.id.textViewSignup);
        if (editTextLName == null) editTextLName = findViewById(R.id.editTextLName);
        if (editTextLPassword == null) editTextLPassword = findViewById(R.id.editTextLPassword);
        if (buttonLogin == null) buttonLogin = findViewById(R.id.buttonLogin);
        if (volleySingleton == null) volleySingleton = VolleySingleton.getInstance(this);
        if (inputManager == null) inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        textViewSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        editTextLPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    buttonLogin.performClick();
                }
                return false;
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Remove keyboard
                if (getCurrentFocus() != null) {
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                sendLoginRequest();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (volleySingleton != null) {
            volleySingleton.getRequestQueue().cancelAll(VolleySingleton.Tags.LOGIN_REQ_TAG);
        }
    }

    private void sendLoginRequest() {
        JSONObject postParams = new JSONObject();
        try {
            postParams.put("password", editTextLPassword.getText().toString());
            // Check if user has entered nick or email
            if (editTextLName.getText().toString().contains(".")) {
                postParams.put("nickname", "");
                postParams.put("email", editTextLName.getText().toString().trim());
            } else {
                postParams.put("nickname", editTextLName.getText().toString().trim());
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

                        } else if (response instanceof JSONObject) {
                            //Success Callback
                            JSONObject r = (JSONObject) response;
                            System.out.println("Response: " + r.toString());
                            // Launch member activity
                            try {
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.putExtra("SessionID", r.getString("jsessionID"));
                                startActivity(i);
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
                        Toast.makeText(getApplicationContext(), "Login fail!", Toast.LENGTH_LONG).show();
                        System.err.println("Error in login!");
                        error.printStackTrace();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq, VolleySingleton.Tags.LOGIN_REQ_TAG);
    }

}
