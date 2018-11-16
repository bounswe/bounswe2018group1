package bounswe2018group1.cmpe451.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import bounswe2018group1.cmpe451.MainActivity;
import bounswe2018group1.cmpe451.R;

public class ProfileFragment extends Fragment {

    private TextView name;
    private TextView bio;
    private TextView birth;
    private TextView gender;
    private TextView locations;
    private Button editProfile;
    private Button logOut;
    private LinearLayout editProfileLayout;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editNickname;
    private EditText editEmail;
    private EditText editOldPassword;
    private EditText editNewPassword;
    private Button editProfileSend;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        // Connect fields
        name = v.findViewById(R.id.profileName);
        bio = v.findViewById(R.id.profileBio);
        birth = v.findViewById(R.id.profileBirth);
        gender = v.findViewById(R.id.profileGender);
        locations = v.findViewById(R.id.profileLocations);
        editProfile = v.findViewById(R.id.editProfile);
        logOut = v.findViewById(R.id.logOut);
        editProfileLayout = v.findViewById(R.id.editProfileLayout);
        editFirstName = v.findViewById(R.id.editFirstName);
        editLastName = v.findViewById(R.id.editLastName);
        editNickname = v.findViewById(R.id.editNickName);
        editEmail = v.findViewById(R.id.editEmail);
        editNewPassword = v.findViewById(R.id.editNewPassword);
        editOldPassword = v.findViewById(R.id.editOldPassword);
        editProfileSend = v.findViewById(R.id.editProfileSend);

        // Hide edits
        editProfileLayout.setVisibility(View.GONE);

        // Set Buttons
        logOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity) getActivity()).logout();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editProfileLayout.getVisibility() == View.GONE) {
                    editProfileLayout.setVisibility(View.VISIBLE);
                }
                else {
                    editProfileLayout.setVisibility(View.GONE);
                }
            }
        });

        editProfileSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity) getActivity()).updateProfile(editFirstName.getText().toString().trim(), editLastName.getText().toString().trim(), editNickname.getText().toString().trim(), editEmail.getText().toString().trim(), editOldPassword.getText().toString().trim(), editNewPassword.getText().toString().trim());
            }
        });

        return v;
    }

    public void setFields(String $first, String $last, String $nick, String $email) {
        name.setText($first + " " + $last + " | " + $nick);
        editFirstName.setText($first);
        editLastName.setText($last);
        editNickname.setText($nick);
        editEmail.setText($email);
    }

}