package bounswe2018group1.cmpe451;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class RegisterActivity extends AppCompatActivity {

    private TextView editText = null;
    private TextView editTextRFirstName = null;
    private TextView editTextRSurname = null;
    private TextView editTextEmail = null;
    private TextView editTextPassword = null;
    private TextView editTextPassword2 = null;
    private TextView textViewAlreadyRegistered = null;
    private Button buttonRegister = null;
    private VolleySingleton volleySingleton = null;
    private InputMethodManager inputManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText = findViewById(R.id.editText);
        editTextRFirstName = findViewById(R.id.editTextRFirstName);
        editTextRSurname = findViewById(R.id.editTextRSurname);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPassword2 = findViewById(R.id.editTextPassword2);
        textViewAlreadyRegistered = findViewById(R.id.textViewAlreadyRegistered);
        buttonRegister = findViewById(R.id.buttonRegister);
        volleySingleton = VolleySingleton.getInstance(this);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        textViewAlreadyRegistered.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!editTextPassword.getText().toString().equals(editTextPassword2.getText().toString())) {
                    editTextPassword.setText("");
                    editTextPassword2.setText("");
                    Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    // Remove keyboard
                    if (getCurrentFocus() != null)
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    sendRegisterRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (volleySingleton != null) {
            volleySingleton.getRequestQueue().cancelAll(VolleySingleton.Tags.REGISTER_REQ_TAG);
        }
    }

    private void sendRegisterRequest() throws JSONException {
        JSONObject postParams = new JSONObject();
        postParams.put("email", editTextEmail.getText().toString().trim());
        postParams.put("firstName", editTextRFirstName.getText().toString().trim());
        postParams.put("lastName", editTextRSurname.getText().toString().trim());
        postParams.put("nickname", editText.getText().toString().trim());
        postParams.put("password", editTextPassword.getText().toString());

        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(Request.Method.POST,
                URLs.URL_REGISTER, postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                        if (response == null) return;
                        if (response instanceof JSONObject) {
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
                        Toast.makeText(getApplicationContext(), "Registration fail!", Toast.LENGTH_LONG).show();
                        System.err.println("Error!!!!");
                        error.printStackTrace();
                    }
                });
        volleySingleton.addToRequestQueue(jsonObjReq, VolleySingleton.Tags.REGISTER_REQ_TAG);
    }
}
