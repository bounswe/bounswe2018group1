package bounswe2018group1.cmpe451;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
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

        textViewSignup = findViewById(R.id.textViewSignup);
        editTextLName = findViewById(R.id.editTextLName);
        editTextLPassword = findViewById(R.id.editTextLPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        volleySingleton = VolleySingleton.getInstance(this);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        textViewSignup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                try {
                    // Remove keyboard
                    if(getCurrentFocus() != null)
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    sendLoginRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(volleySingleton != null) {
            volleySingleton.getRequestQueue().cancelAll(VolleySingleton.Tags.LOGIN_REQ_TAG);
        }
    }

    private void sendLoginRequest() throws JSONException {

        JSONObject postParamsEmail = new JSONObject();
        postParamsEmail.put("email", editTextLName.getText().toString().trim());
        postParamsEmail.put("nickname", "");
        postParamsEmail.put("password", editTextLPassword.getText().toString());
        JSONObject postParamsNickname = new JSONObject();
        postParamsEmail.put("email", "");
        postParamsEmail.put("nickname", editTextLName.getText().toString().trim());
        postParamsEmail.put("password", editTextLPassword.getText().toString());

        final JsonObjectRequest jsonObjReqByNickname = new NullResponseJsonObjectRequest(
                Request.Method.POST,
                URLs.URL_LOGIN, postParamsNickname,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                        if(response == null) return;
                        if(response instanceof JSONObject) {
                            //Success Callback
                            JSONObject r = (JSONObject)response;
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
                        Toast.makeText(getApplicationContext(), "Login fail!", Toast.LENGTH_LONG).show();
                        System.err.println("Error!!!!");
                        error.printStackTrace();
                    }
                });
        JsonObjectRequest jsonObjReqByEmail = new NullResponseJsonObjectRequest(
                Request.Method.POST,
                URLs.URL_LOGIN, postParamsEmail,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                        if(response == null) return;
                        if(response instanceof JSONObject) {
                            //Success Callback
                            JSONObject r = (JSONObject)response;
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
                        volleySingleton.addToRequestQueue(jsonObjReqByNickname, VolleySingleton.Tags.LOGIN_REQ_TAG);
                    }
                });
        volleySingleton.addToRequestQueue(jsonObjReqByEmail, VolleySingleton.Tags.LOGIN_REQ_TAG);
    }
}
