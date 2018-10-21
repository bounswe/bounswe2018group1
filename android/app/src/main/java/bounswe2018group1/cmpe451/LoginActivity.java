package bounswe2018group1.cmpe451;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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

                // Remove keyboard
                if(getCurrentFocus() != null)
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                sendLoginRequest();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.option_user_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogin:
                Toast.makeText(getApplicationContext(),"Login Clicked",Toast.LENGTH_LONG).show();
                return true;

            case R.id.menuRegister:
                Toast.makeText(getApplicationContext(),"Register Clicked",Toast.LENGTH_LONG).show();
                return true;

            case R.id.menuReset:
                Toast.makeText(getApplicationContext(),"Reset Password Clicked",Toast.LENGTH_LONG).show();
                return true;

            case R.id.menuSearch:
                Toast.makeText(getApplicationContext(),"Memory Search Clicked",Toast.LENGTH_LONG).show();
                return true;

            default:

                super.onOptionsItemSelected(item);

        }
        return true;

    }

    @Override
    protected void onStop() {
        super.onStop();

        if(volleySingleton != null) {
            volleySingleton.getRequestQueue().cancelAll(VolleySingleton.Tags.LOGIN_REQ_TAG);
        }
    }

    private void sendLoginRequest() {

        JSONObject postParams = new JSONObject();
        try {
            postParams.put("password", editTextLPassword.getText().toString());
            // Check if user has entered nick or email
            if (editTextLName.getText().toString().contains("@")) {
                postParams.put("nickname", "");
                postParams.put("email", editTextLName.getText().toString().trim());
            }
            else{
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
                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show(); // TODO remove!
                    if(response == null) {

                    }
                    else if(response instanceof JSONObject) {
                        //Success Callback
                        JSONObject r = (JSONObject)response;
                        System.out.println("Response: " + r.toString());
                    } else {
                        System.out.println("Response: " + response.toString());
                    }
                    // Launch member activity
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
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