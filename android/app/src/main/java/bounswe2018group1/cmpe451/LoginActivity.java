package bounswe2018group1.cmpe451;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (textViewSignup == null) textViewSignup = findViewById(R.id.textViewSignup);
        if (editTextLName == null) editTextLName = findViewById(R.id.editTextLName);
        if (editTextLPassword == null) editTextLPassword = findViewById(R.id.editTextLPassword);
        if (buttonLogin == null) buttonLogin = findViewById(R.id.buttonLogin);
        if (volleySingleton == null) volleySingleton = VolleySingleton.getInstance(this);
        if (inputManager == null)
            inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        setSupportActionBar(toolbar);
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
                    //do what you want on the press of 'done'
                    buttonLogin.performClick();
                }
                return false;
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Remove keyboard
                if (getCurrentFocus() != null)
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                sendLoginRequest();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSettings:
                // TODO: open settings
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            if (editTextLName.getText().toString().contains("@")) {
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
                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show(); // TODO remove!
                        if (response == null) {

                        } else if (response instanceof JSONObject) {
                            //Success Callback
                            JSONObject r = (JSONObject) response;
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
