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

import bounswe2018group1.cmpe451.helpers.ClientAPI;

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
        if (inputManager == null)
            inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

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

                clientAPI.sendLoginRequest(editTextLName.getText().toString(), editTextLPassword.getText().toString(), v.getContext());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        clientAPI.cancelAll(ClientAPI.Tags.LOGIN_REQ_TAG, this);
    }

}
