package bounswe2018group1.cmpe451.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

import bounswe2018group1.cmpe451.MainActivity;
import bounswe2018group1.cmpe451.MapsActivity;
import bounswe2018group1.cmpe451.R;

import static android.app.Activity.RESULT_OK;

public class CreateFragment extends Fragment {

    private Button addLocation;
    private LinearLayout locationList;
    private Button addImage;
    private EditText story;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_MAP_POINT = 999;
    private ArrayList<LinearLayout> locationLayoutList;
    private ArrayList<EditText> locationTextList;
    private ArrayList<Button> locationButtonList;
    private ArrayList<Button> locationMapList;
    private int locationTag = 0;
    private Button share;

    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // Pick image from gallery
        if(PICK_IMAGE == requestCode && resultCode == RESULT_OK && data != null && data.getData() != null){
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            ssb.append(story.getText());
            //ssb.append("\n");
            String imgId = "[img]";
            int selStart = story.getSelectionStart();
            ssb.replace(story.getSelectionStart() +1 , story.getSelectionEnd()+1 , imgId);
            //Bitmap image = BitmapFactory.decodeResource(getResources() , R.drawable.group_icon);


            Uri contentURI = data.getData();
            Context applicationContext = MainActivity.getContextOfApplication();
            Bitmap image = null;
            try {
                image = MediaStore.Images.Media.getBitmap(applicationContext.getContentResolver(), contentURI);
            }
            catch (IOException e){
                return;
            }
            //Drawable image = ContextCompat.getDrawable(getActivity() , R.drawable.group_icon);
            //image.setBounds(0,0 ,300, 300);


            ssb.setSpan(new ImageSpan(getContext(), image) , selStart+1 , selStart+1+imgId.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //ssb.append("\n");
            story.setText(ssb);
        }
        // Pick point from map
        else if (PICK_MAP_POINT == requestCode && resultCode == RESULT_OK) {
            LatLng latLng = (LatLng) data.getParcelableExtra("PickedPoint");
            Bundle bundle = data.getExtras();
            for (int i = 0; i < locationLayoutList.size(); i ++) {
                if (bundle.getString("TagOfMap").equals(locationLayoutList.get(i).getTag() + "M")) {
                    locationTextList.get(i).setText(latLng.latitude + " " + latLng.longitude);
                    break;
                }
            }
        }
        else {
            System.err.println("CreateFragment has received an unknown request code.");
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create, container, false);

        // Connect fields
        addLocation = rootView.findViewById(R.id.addLocation);
        locationList = rootView.findViewById(R.id.locationList);
        addImage = rootView.findViewById(R.id.addImage);
        story = rootView.findViewById(R.id.story);
        share = rootView.findViewById(R.id.share);

        // List of location fields
        locationLayoutList = new ArrayList<LinearLayout>();
        locationTextList = new ArrayList<EditText>();
        locationButtonList = new ArrayList<Button>();
        locationMapList = new ArrayList<Button>();

        addLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Horizontal layout for location bar
                locationLayoutList.add(new LinearLayout(getContext()));
                locationLayoutList.get(locationLayoutList.size() - 1).setTag("L" + locationTag);
                locationLayoutList.get(locationLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
                locationLayoutList.get(locationLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                // EditText for location
                locationTextList.add(new EditText(getContext()));
                locationTextList.get(locationTextList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(4 * locationList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
                locationTextList.get(locationTextList.size() - 1).setHint("Location");
                // Button for deletion
                locationButtonList.add(new Button(getContext()));
                locationButtonList.get(locationButtonList.size() - 1).setTag("L" + locationTag + "B");
                locationButtonList.get(locationButtonList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(locationList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
                locationButtonList.get(locationButtonList.size() - 1).setText("X");
                // Button for map
                locationMapList.add(new Button(getContext()));
                locationMapList.get(locationMapList.size() - 1).setTag("L" + locationTag + "M");
                locationMapList.get(locationMapList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(locationList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
                locationMapList.get(locationMapList.size() - 1).setText("M");
                // Add them all
                locationLayoutList.get(locationLayoutList.size() - 1).addView(locationMapList.get(locationMapList.size() - 1));
                locationLayoutList.get(locationLayoutList.size() - 1).addView(locationTextList.get(locationTextList.size() - 1));
                locationLayoutList.get(locationLayoutList.size() - 1).addView(locationButtonList.get(locationButtonList.size() - 1));
                locationList.addView(locationLayoutList.get(locationLayoutList.size() - 1));
                // Increment location counter
                locationTag = locationTag + 1;
                // Deletion function at button
                locationButtonList.get(locationButtonList.size() - 1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Find and remove pressed button and location
                        for (int i = 0; i < locationLayoutList.size(); i++) {
                            if (view.getTag().equals(locationLayoutList.get(i).getTag() + "B")) {
                                locationList.removeView(locationList.findViewWithTag(locationLayoutList.get(i).getTag()));
                                locationLayoutList.remove(i);
                                locationTextList.remove(i);
                                locationButtonList.remove(i);
                                locationMapList.remove(i);
                                break;
                            }
                        }
                    }
                });
                // Map function at map button
                locationMapList.get(locationMapList.size() - 1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Open map and send tag to map activity
                        Intent pickPointIntent = new Intent(getActivity(), MapsActivity.class);
                        pickPointIntent.putExtra("TagOfMap", view.getTag().toString().trim());
                        startActivityForResult(pickPointIntent, PICK_MAP_POINT);
                    }
                });
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(story.getText());    // TODO DO İT!
            }
        });

        return rootView;
    }

}
