package bounswe2018group1.cmpe451.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bounswe2018group1.cmpe451.R;

public class ProfileFragment extends Fragment {

    TextView nick;
    TextView name;
    TextView bio;
    TextView birth;
    TextView gender;
    private boolean isProfileLoaded = false;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        nick = v.findViewById(R.id.profileNick);
        name = v.findViewById(R.id.profileName);
        bio = v.findViewById(R.id.profileBio);
        birth = v.findViewById(R.id.profileBirth);
        gender = v.findViewById(R.id.profileGender);
        return v;
    }

    public boolean getProfileLoaded() {
        return isProfileLoaded;
    }

    public void setProfileLoaded(boolean profileLoaded) {
        isProfileLoaded = profileLoaded;
    }

    public void setFields(String $nick, String $first, String $last, String $bio, String $birth, String $gender) {
        nick.setText($nick);
        name.setText($first + " " + $last);
        bio.setText($bio);
        birth.setText($birth);
        gender.setText($gender);
    }

}
