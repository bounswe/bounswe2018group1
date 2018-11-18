package bounswe2018group1.cmpe451;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import bounswe2018group1.cmpe451.helpers.ClientAPI;

public class RegisterActivity extends AppCompatActivity {

    private TextView editText = null;
    private TextView editTextRFirstName = null;
    private TextView editTextRSurname = null;
    private TextView editTextEmail = null;
    private TextView editTextPassword = null;
    private TextView editTextPassword2 = null;
    private TextView textViewAlreadyRegistered = null;
    private Button buttonRegister = null;
    private ClientAPI clientAPI = null;
    private InputMethodManager inputManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (editText == null) editText = findViewById(R.id.editText);
        if (editTextRFirstName == null) editTextRFirstName = findViewById(R.id.editTextRFirstName);
        if (editTextRSurname == null) editTextRSurname = findViewById(R.id.editTextRSurname);
        if (editTextEmail == null) editTextEmail = findViewById(R.id.editTextEmail);
        if (editTextPassword == null) editTextPassword = findViewById(R.id.editTextPassword);
        if (editTextPassword2 == null) editTextPassword2 = findViewById(R.id.editTextPassword2);
        if (textViewAlreadyRegistered == null)
            textViewAlreadyRegistered = findViewById(R.id.textViewAlreadyRegistered);
        if (buttonRegister == null) buttonRegister = findViewById(R.id.buttonRegister);
        if (clientAPI == null) clientAPI = ClientAPI.getInstance(this);
        if (inputManager == null)
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

                // Email validity check
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches()) {
                    editTextEmail.setError("Not a valid email!");
                    editTextEmail.requestFocus();
                    return;
                }
                {   // Password validity check
                    boolean longerThan8Character = (editTextPassword.getText().toString().length() >= 8);
                    boolean hasUpperCase = false, hasLowerCase = false, hasDigit = false;
                    for (char ch : editTextPassword.getText().toString().toCharArray()) {
                        if (Character.isUpperCase(ch)) {
                            hasUpperCase = true;
                        } else if (Character.isLowerCase(ch)) {
                            hasLowerCase = true;
                        } else if (Character.isDigit(ch)) {
                            hasDigit = true;
                        }
                    }
                    if (!(longerThan8Character && hasUpperCase && hasLowerCase && hasDigit)) {
                        editTextPassword.setText("");
                        editTextPassword2.setText("");
                        editTextPassword.setError("Password has to be longer than 8 characters, contain uppercase letter, lowercase letter and digit!");
                        editTextPassword.requestFocus();
                        return;
                    }
                }
                // Confirm password validity check
                if (!editTextPassword.getText().toString().equals(editTextPassword2.getText().toString())) {
                    editTextPassword2.setText("");
                    editTextPassword2.setError("Passwords do not match!");
                    editTextPassword2.requestFocus();
                    return;
                }

                try {
                    // Remove keyboard
                    if (getCurrentFocus() != null)
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    clientAPI.sendRegisterRequest(
                            editTextEmail.getText().toString(),
                            editTextRFirstName.getText().toString(),
                            editTextRSurname.getText().toString(),
                            editText.getText().toString(),
                            editTextPassword.getText().toString(),
                            v.getContext()
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

        clientAPI.cancelAll(ClientAPI.Tags.REGISTER_REQ_TAG, this);
    }

}
