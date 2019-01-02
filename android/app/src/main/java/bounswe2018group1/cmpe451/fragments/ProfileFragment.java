package bounswe2018group1.cmpe451.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import bounswe2018group1.cmpe451.MainActivity;
import bounswe2018group1.cmpe451.R;
import bounswe2018group1.cmpe451.helpers.ClientAPI;
import bounswe2018group1.cmpe451.helpers.RealPathUtil;
import bounswe2018group1.cmpe451.helpers.URLs;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

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
    private DatePicker editBirth;
    private RadioGroup genderGroup;
    private RadioButton genderFemale;
    private RadioButton genderMale;
    private RadioButton genderOther;
    private RadioButton genderNo;
    private EditText editLocations;
    private EditText editEmail = null;
    private EditText editOldPassword = null;
    private EditText editNewPassword = null;
    private Button editProfileSend = null;
    private ClientAPI clientAPI = null;
    private InputMethodManager inputManager = null;
    private Button editPicture;
    private static final int PICK_IMAGE = 1;
    private Uri imageUri;
    private ImageView picture;
    private static final int MESSAGE_LOAD_PROFILE_PICTURE = 111;
    private Handler updateUIHandler = null;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        // Pick image from gallery
        if (PICK_IMAGE == requestCode && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            File imageFile = new File(RealPathUtil.getRealPath(getContext(), imageUri));

            // Prepare request and send
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", imageFile.getName(), RequestBody.create(MediaType.parse("image"), imageFile))  // TODO add extension to image, .bmp .png .jpg
                    .build();

            Request request = new Request.Builder()
                    .url(URLs.URL_MEDIA)
                    .addHeader("Cookie", ("JSESSIONID=" + ((MainActivity) getActivity()).getSessionID()))
                    .post(body)
                    .build();

            OkHttpClient client = new OkHttpClient();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.err.println("Profile Fragment: Profile picture upload failed in send");
                }

                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()) {
                        System.out.println("Profile Fragment: Profile picture upload success");
                        try {
                            String url = response.body().string();
                            clientAPI.updateProfilePicture(url, getThis());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println("Profile Fragment: Profile picture upload failed in return");
                    }
                }
            });
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        // Server calls
        if (clientAPI == null) clientAPI = ClientAPI.getInstance(getContext());

        // Keyboard
        if (inputManager == null)
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
        editBirth = v.findViewById(R.id.editBirth);
        genderGroup = v.findViewById(R.id.genderGroup);
        genderFemale = v.findViewById(R.id.genderFemale);
        genderMale = v.findViewById(R.id.genderMale);
        genderOther = v.findViewById(R.id.genderOther);
        genderNo = v.findViewById(R.id.genderNo);
        editLocations = v.findViewById(R.id.editLocations);
        if (editEmail == null) editEmail = v.findViewById(R.id.editEmail);
        if (editNewPassword == null) editNewPassword = v.findViewById(R.id.editNewPassword);
        if (editOldPassword == null) editOldPassword = v.findViewById(R.id.editOldPassword);
        if (editProfileSend == null) editProfileSend = v.findViewById(R.id.editProfileSend);
        editPicture = v.findViewById(R.id.editPicture);
        picture = v.findViewById(R.id.picture);

        // Child thread cannot reach parent thread views
        createUpdateUiHandler();

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

        editProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Remove keyboard
                if (getActivity().getCurrentFocus() != null)
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                // Toggle visibility of edit layout
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
                } else if (genderMale.getId() == id) {
                    gender = "MALE";
                } else if (genderOther.getId() == id) {
                    gender = "OTHER";
                } else {
                    gender = "NOT_TO_DISCLOSE";
                }
                String birth = "" + editBirth.getYear() + "-";
                if (editBirth.getMonth() + 1 < 10) {
                    birth = birth + "0";
                }
                birth = birth + (editBirth.getMonth() + 1) + "-";
                if (editBirth.getDayOfMonth() < 10) {
                    birth = birth + "0";
                }
                birth = birth + editBirth.getDayOfMonth();
                clientAPI.updateProfile(
                        editFirstName.getText().toString().trim(),
                        editLastName.getText().toString().trim(),
                        editNickname.getText().toString().trim(),
                        editBio.getText().toString().trim(),
                        birth,
                        gender,
                        editLocations.getText().toString().trim(),
                        editEmail.getText().toString().trim(),
                        editOldPassword.getText().toString().trim(),
                        editNewPassword.getText().toString().trim(),
                        getThis());
            }
        });

        editPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        return v;
    }

    public void loadProfile() {
        clientAPI.loadProfile(this);
    }

    public ProfileFragment getThis() {
        return this;
    }

    public void setFields(String $first, String $last, String $nick, String $email, String $bio, String $birth, String $gender, JSONArray $locations) {
        name.setText($first + " " + $last + " | " + $nick);
        bio.setText($bio);
        birth.setText($birth);
        if ($birth.equals("null")) {
            birth.setText("Birthday");
        } else {
            int year;
            int month;
            int day;
            try {
                year = Integer.parseInt($birth.substring(0, $birth.indexOf('-')));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.err.println("Year parsing has failed.");
                year = 1900;
            }
            try {
                month = Integer.parseInt($birth.substring($birth.indexOf('-') + 1, $birth.lastIndexOf('-'))) - 1;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.err.println("Month parsing has failed.");
                month = 0;
            }
            try {
                day = Integer.parseInt($birth.substring($birth.lastIndexOf('-') + 1));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.err.println("Day parsing has failed.");
                day = 1;
            }
            editBirth.updateDate(year, month, day);
        }
        editFirstName.setText($first);
        editLastName.setText($last);
        editNickname.setText($nick);
        editBio.setText($bio);
        if ($bio.equals("")) {
            bio.setText("Your Bio");
            editBio.setText("Your Bio");
        }
        editEmail.setText($email);
        // Set gender
        if ($gender.equals("FEMALE")) {
            gender.setText("Female");
            genderFemale.setChecked(true);
        } else if ($gender.equals("MALE")) {
            gender.setText("Male");
            genderMale.setChecked(true);
        } else if ($gender.equals("OTHER")) {
            gender.setText("Other");
            genderOther.setChecked(true);
        } else {
            gender.setText("Not to disclose");
            genderNo.setChecked(true);
        }
        // Set locations, ignore map points
        String locationList = "";
        for (int i = 0; i < $locations.length(); i++) {
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
        if (locationList.length() > 1 && locationList.charAt(locationList.length() - 1) == '\n') {
            locationList = locationList.substring(0, (locationList.length() - 1));
        }
        locations.setText(locationList);
        if (locationList.equals("")) {
            locations.setText("Locations");
        }
        editLocations.setText(locationList);
    }

    public void loadProfilePicture(String url) {
        if (url.equals("null")) {
            System.err.println("Profile Picture Url is null");
            return;
        }
        // Prepare request and send
        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.err.println("Profile Fragment: Load profile picture failed in send");
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    System.out.println("Profile Fragment: Load profile picture success");
                    InputStream inputStream = response.body().byteStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                    // Send to parent
                    Message message = new Message();
                    message.what = MESSAGE_LOAD_PROFILE_PICTURE;
                    message.obj = bitmap;
                    updateUIHandler.sendMessage(message);
                } else {
                    System.err.println("Profile Fragment: Load profile picture failed in return");
                }
            }
        });
    }

    private void createUpdateUiHandler() {
        if (updateUIHandler == null) {
            updateUIHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == MESSAGE_LOAD_PROFILE_PICTURE) {
                        Bitmap bitmap = (Bitmap) msg.obj;
                        picture.setImageBitmap(bitmap);
                    }
                }
            };
        }
    }

}