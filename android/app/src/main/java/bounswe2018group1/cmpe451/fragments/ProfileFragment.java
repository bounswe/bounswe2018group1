package bounswe2018group1.cmpe451.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import bounswe2018group1.cmpe451.R;
import bounswe2018group1.cmpe451.helpers.ClientAPI;

public class ProfileFragment extends Fragment {

    private TextView name = null;
    private TextView bio = null;
    private TextView birth = null;
    private TextView gender = null;
    private TextView locations = null;
    private Button editProfile = null;
    private Button logOut = null;
    private LinearLayout editProfileLayout = null;
    private EditText editFirstName = null;
    private EditText editLastName = null;
    private EditText editNickname = null;
    private EditText editBio;
    private RadioGroup genderGroup;
    private RadioButton genderFemale;
    private RadioButton genderMale;
    private RadioButton genderOther;
    private RadioButton genderNo;
    private EditText editEmail = null;
    private EditText editOldPassword = null;
    private EditText editNewPassword = null;
    private Button editProfileSend = null;
    private ClientAPI clientAPI = null;
    private InputMethodManager inputManager = null;
    private ProfileFragment here;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        // Server calls
        if (clientAPI == null) clientAPI = ClientAPI.getInstance(getContext());

        // Keyboard
        if(inputManager == null)
            inputManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        // Connect fields
        if (name == null) name = v.findViewById(R.id.profileName);
        if (bio == null) bio = v.findViewById(R.id.profileBio);
        if (birth == null) birth = v.findViewById(R.id.profileBirth);
        if (gender == null) gender = v.findViewById(R.id.profileGender);
        if (locations == null) locations = v.findViewById(R.id.profileLocations);
        if (editProfile == null) editProfile = v.findViewById(R.id.editProfile);
        if (logOut == null) logOut = v.findViewById(R.id.logOut);
        if (editProfileLayout == null) editProfileLayout = v.findViewById(R.id.editProfileLayout);
        if (editFirstName == null) editFirstName = v.findViewById(R.id.editFirstName);
        if (editLastName == null) editLastName = v.findViewById(R.id.editLastName);
        if (editNickname == null) editNickname = v.findViewById(R.id.editNickName);
        editBio = v.findViewById(R.id.editBio);
        genderGroup = v.findViewById(R.id.genderGroup);
        genderFemale = v.findViewById(R.id.genderFemale);
        genderMale = v.findViewById(R.id.genderMale);
        genderOther = v.findViewById(R.id.genderOther);
        genderNo = v.findViewById(R.id.genderNo);
        if (editEmail == null) editEmail = v.findViewById(R.id.editEmail);
        if (editNewPassword == null) editNewPassword = v.findViewById(R.id.editNewPassword);
        if (editOldPassword == null) editOldPassword = v.findViewById(R.id.editOldPassword);
        if (editProfileSend == null) editProfileSend = v.findViewById(R.id.editProfileSend);

        // Hide edits
        editProfileLayout.setVisibility(View.GONE);

        // Load user information
        loadProfile();

        // Set Buttons
        logOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clientAPI.logout(v.getContext());
            }
        });

        // Save current fragment
        here = this;

        editProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editProfileLayout.getVisibility() == View.GONE) {
                    editProfileLayout.setVisibility(View.VISIBLE);
                } else {
                    editProfileLayout.setVisibility(View.GONE);
                }
            }
        });

        editProfileSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Remove keyboard
                if (getActivity().getCurrentFocus() != null)
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                String gender;
                int id = genderGroup.getCheckedRadioButtonId();
                if (genderFemale.getId() == id) {
                    gender = "FEMALE";
                }
                else if (genderMale.getId() == id) {
                    gender = "MALE";
                }
                else if (genderOther.getId() == id) {
                    gender = "OTHER";
                }
                else {
                    gender = "NOT_TO_DISCLOSE";
                }
                clientAPI.updateProfile(
                        editFirstName.getText().toString().trim(),
                        editLastName.getText().toString().trim(),
                        editNickname.getText().toString().trim(),
                        editBio.getText().toString().trim(),
                        gender,
                        editEmail.getText().toString().trim(),
                        editOldPassword.getText().toString().trim(),
                        editNewPassword.getText().toString().trim(),
                        here);
            }
        });

        return v;
    }

    public void loadProfile() {
        clientAPI.loadProfile(this);
    }

    public void setFields(String $first, String $last, String $nick, String $email, String $bio, String $birth, String $gender, JSONArray $locations) {
        name.setText($first + " " + $last + " | " + $nick);
        bio.setText($bio);
        birth.setText($birth);
        editFirstName.setText($first);
        editLastName.setText($last);
        editNickname.setText($nick);
        editBio.setText($bio);
        editEmail.setText($email);
        // Set gender
        if ($gender.equals("FEMALE")) {
            genderFemale.setChecked(true);
        }
        else if ($gender.equals("MALE")) {
            genderMale.setChecked(true);
        }
        else if ($gender.equals("OTHER")) {
            genderOther.setChecked(true);
        }
        else {
            genderNo.setChecked(true);
        }
        gender.setText($gender);
        // Set locations, ignore map points
        String locationList = "";
        for (int i = 0; i < $locations.length(); i ++) {
            String location = "";
            try {
                location = $locations.optJSONObject(i).getString("locationName");
            } catch (JSONException e) {
                e.printStackTrace();
                System.err.println("Profile load fail in location name parse");
            }
            if (!location.isEmpty()) {
                locationList = locationList + location + "\n";
            }
        }
        if (locationList.length() > 2 && locationList.charAt(locationList.length() - 1) == 'n' && locationList.charAt(locationList.length() - 2) == '\\') {
            locationList = locationList.substring(0, locationList.charAt(locationList.length() - 3));
        }
        locations.setText(locationList);
    }

}
