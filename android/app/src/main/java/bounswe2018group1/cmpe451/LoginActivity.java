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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bounswe2018group1.cmpe451.helpers.ClientAPI;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import bounswe2018group1.cmpe451.helpers.URLs;

public class LoginActivity extends AppCompatActivity {

    private TextView textViewSignup = null;
    private TextView editTextLName = null;
    private TextView editTextLPassword = null;
    private Button buttonLogin = null;
    private ClientAPI clientAPI = null;
    private InputMethodManager inputManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (textViewSignup == null) textViewSignup = findViewById(R.id.textViewSignup);
        if (editTextLName == null) editTextLName = findViewById(R.id.editTextLName);
        if (editTextLPassword == null) editTextLPassword = findViewById(R.id.editTextLPassword);
        if (buttonLogin == null) buttonLogin = findViewById(R.id.buttonLogin);
        if (clientAPI == null) clientAPI = ClientAPI.getInstance(this);
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

                // Prepare JSON Object for request
                JSONObject parameters = new JSONObject();
                String loginName = editTextLName.getText().toString().trim();
                String password = editTextLPassword.getText().toString().trim();
                try {
                    parameters.put("password", password);
                    // Check if user has entered nick or email
                    if (loginName.contains(".")) {
                        parameters.put("nickname", "");
                        parameters.put("email", loginName.trim());
                    } else {
                        parameters.put("nickname", loginName.trim());
                        parameters.put("email", "");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Prepare request and send
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), parameters.toString());

                Request request = new Request.Builder()
                        .url(URLs.URL_LOGIN)
                        .post(body)
                        .build();

                OkHttpClient client = new OkHttpClient();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.err.println("Login Activity: Login failed in send");
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject responseBody = new JSONObject(response.body().string());
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.putExtra("SessionID", responseBody.getString("jsessionID"));
                                startActivity(i);
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            System.err.println("Login Activity: Login failed in return");
                        }
                    }
                });
            }
        });
}

    protected void onStop() {
        super.onStop();
        clientAPI.cancelAll(ClientAPI.Tags.LOGIN_REQ_TAG, this);
    }

}
