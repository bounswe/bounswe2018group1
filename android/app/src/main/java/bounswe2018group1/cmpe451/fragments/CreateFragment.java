package bounswe2018group1.cmpe451.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import bounswe2018group1.cmpe451.MainActivity;
import bounswe2018group1.cmpe451.MapsCreateActivity;
import bounswe2018group1.cmpe451.R;
import bounswe2018group1.cmpe451.helpers.ClientAPI;

import static android.app.Activity.RESULT_OK;

public class CreateFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private static final int PICK_VIDEO = 2;
    private static final int PICK_AUDIO = 3;
    private static final int PICK_MAP_POINT = 999;

    private LinearLayout itemList;
    private ArrayList<LinearLayout> itemLayoutList;
    private ArrayList<View> itemViewList;
    private ArrayList<Button> itemButtonList;
    private LinearLayout locationList;
    private ArrayList<LinearLayout> locationLayoutList;
    private ArrayList<EditText> locationTextList;
    private ArrayList<Button> locationButtonList;
    private ArrayList<Button> locationMapList;
    private int locationTag = 0;
    private int itemTag = 0;
    private Button addLocation;
    private EditText headline;
    private EditText startDateYYYY;
    private EditText startDateMM;
    private EditText startDateDD;
    private EditText startDateHH;
    private EditText endDateYYYY;
    private EditText endDateMM;
    private EditText endDateDD;
    private EditText endDateHH;
    private Button share;
    private Button addImage;
    private Button addVideo;
    private Button addAudio;
    private Button addText;
    private ClientAPI clientAPI;

    private Uri imageUri;
    private Uri videoUri;
    private Uri audioUri;
    private ArrayList<String> items = new ArrayList<String>();

    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // Pick image from gallery
        if(PICK_IMAGE == requestCode && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            items.add(imageUri.toString());
            itemLayoutList.add(new LinearLayout(getContext()));
            itemLayoutList.get(itemLayoutList.size() - 1).setTag("L" + itemTag);
            itemLayoutList.get(itemLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
            itemLayoutList.get(itemLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            // Image view for image
            itemViewList.add(new ImageView(getContext()));
            itemViewList.get(itemViewList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(4 * itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
            ((ImageView)itemViewList.get(itemViewList.size()-1)).setImageURI(imageUri);
            // Button for deletion
            itemButtonList.add(new Button(getContext()));
            itemButtonList.get(itemButtonList.size() - 1).setTag("L" + itemTag + "B");
            itemButtonList.get(itemButtonList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
            itemButtonList.get(itemButtonList.size() - 1).setText("X");
            // Add them all
            itemLayoutList.get(itemLayoutList.size() - 1).addView(itemViewList.get(itemViewList.size() - 1));
            itemLayoutList.get(itemLayoutList.size() - 1).addView(itemButtonList.get(itemButtonList.size() - 1));
            itemList.addView(itemLayoutList.get(itemLayoutList.size() - 1));
            // Increment item counter
            itemTag = itemTag + 1;
            // Deletion function at button
            itemButtonList.get(itemButtonList.size() - 1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Find and remove pressed button and item
                    for (int i = 0; i < itemLayoutList.size(); i++) {
                        if (view.getTag().equals(itemLayoutList.get(i).getTag() + "B")) {
                            itemList.removeView(itemList.findViewWithTag(itemLayoutList.get(i).getTag()));
                            itemLayoutList.remove(i);
                            itemViewList.remove(i);
                            itemButtonList.remove(i);
                            items.remove(i);
                            break;
                        }
                    }
                }
            });
        }
        else if(PICK_VIDEO == requestCode &&resultCode == RESULT_OK && data != null && data.getData() != null){
            videoUri = data.getData();
            items.add(videoUri.toString());
            itemLayoutList.add(new LinearLayout(getContext()));
            itemLayoutList.get(itemLayoutList.size() - 1).setTag("L" + itemTag);
            itemLayoutList.get(itemLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
            itemLayoutList.get(itemLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            // Video view for image
            itemViewList.add(new VideoView(getContext()));
            itemViewList.get(itemViewList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(4 * itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
            ((VideoView)itemViewList.get(itemViewList.size()-1)).setVideoURI(videoUri);
            // Button for deletion
            itemButtonList.add(new Button(getContext()));
            itemButtonList.get(itemButtonList.size() - 1).setTag("L" + itemTag + "B");
            itemButtonList.get(itemButtonList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
            itemButtonList.get(itemButtonList.size() - 1).setText("X");
            // Add them all
            itemLayoutList.get(itemLayoutList.size() - 1).addView(itemViewList.get(itemViewList.size() - 1));
            itemLayoutList.get(itemLayoutList.size() - 1).addView(itemButtonList.get(itemButtonList.size() - 1));
            itemList.addView(itemLayoutList.get(itemLayoutList.size() - 1));
            // Increment item counter
            itemTag = itemTag + 1;
            // Deletion function at button
            itemButtonList.get(itemButtonList.size() - 1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Find and remove pressed button and item
                    for (int i = 0; i < itemLayoutList.size(); i++) {
                        if (view.getTag().equals(itemLayoutList.get(i).getTag() + "B")) {
                            itemList.removeView(itemList.findViewWithTag(itemLayoutList.get(i).getTag()));
                            itemLayoutList.remove(i);
                            itemViewList.remove(i);
                            itemButtonList.remove(i);
                            items.remove(i);
                            break;
                        }
                    }
                }
            });
        }
        else if(PICK_AUDIO == requestCode &&resultCode == RESULT_OK && data != null && data.getData() != null) {
            audioUri = data.getData();
            items.add(audioUri.toString());
            itemLayoutList.add(new LinearLayout(getContext()));
            itemLayoutList.get(itemLayoutList.size() - 1).setTag("L" + itemTag);
            itemLayoutList.get(itemLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
            itemLayoutList.get(itemLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            // Text view for audio file's name
            itemViewList.add(new TextView(getContext()));
            itemViewList.get(itemViewList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(4 * itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
            ((TextView)itemViewList.get(itemViewList.size()-1)).setText((new File(audioUri.toString())).getName());
            // Button for deletion
            itemButtonList.add(new Button(getContext()));
            itemButtonList.get(itemButtonList.size() - 1).setTag("L" + itemTag + "B");
            itemButtonList.get(itemButtonList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
            itemButtonList.get(itemButtonList.size() - 1).setText("X");
            // Add them all
            itemLayoutList.get(itemLayoutList.size() - 1).addView(itemViewList.get(itemViewList.size() - 1));
            itemLayoutList.get(itemLayoutList.size() - 1).addView(itemButtonList.get(itemButtonList.size() - 1));
            itemList.addView(itemLayoutList.get(itemLayoutList.size() - 1));
            // Increment item counter
            itemTag = itemTag + 1;
            // Deletion function at button
            itemButtonList.get(itemButtonList.size() - 1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Find and remove pressed button and item
                    for (int i = 0; i < itemLayoutList.size(); i++) {
                        if (view.getTag().equals(itemLayoutList.get(i).getTag() + "B")) {
                            itemList.removeView(itemList.findViewWithTag(itemLayoutList.get(i).getTag()));
                            itemLayoutList.remove(i);
                            itemViewList.remove(i);
                            itemButtonList.remove(i);
                            items.remove(i);
                            break;
                        }
                    }
                }
            });

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

        // Server calls
        if (clientAPI == null) clientAPI = ClientAPI.getInstance(getContext());
        rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        // Connect fields
        headline = rootView.findViewById(R.id.headline);
        startDateYYYY = rootView.findViewById(R.id.startDateYYYY);
        startDateMM = rootView.findViewById(R.id.startDateMM);
        startDateDD = rootView.findViewById(R.id.startDateDD);
        startDateHH = rootView.findViewById(R.id.startDateHH);
        endDateYYYY = rootView.findViewById(R.id.endDateYYYY);
        endDateMM = rootView.findViewById(R.id.endDateMM);
        endDateDD = rootView.findViewById(R.id.endDateDD);
        endDateHH = rootView.findViewById(R.id.endDateHH);
        addLocation = rootView.findViewById(R.id.addLocation);
        locationList = rootView.findViewById(R.id.locationList);
        itemList = rootView.findViewById(R.id.itemList);
        share = rootView.findViewById(R.id.share);
        addImage = rootView.findViewById(R.id.addImage);
        addAudio = rootView.findViewById(R.id.addAudio);
        addText = rootView.findViewById(R.id.addText);
        addVideo = rootView.findViewById(R.id.addVideo);
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
                        Intent pickPointIntent = new Intent(getActivity(), MapsCreateActivity.class);
                        pickPointIntent.putExtra("TagOfMap", view.getTag().toString().trim());
                        startActivityForResult(pickPointIntent, PICK_MAP_POINT);
                    }
                });
            }
        });

        itemLayoutList = new ArrayList<LinearLayout>();
        itemButtonList = new ArrayList<Button>();
        itemViewList = new ArrayList<View>();
        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add("*");
                itemLayoutList.add(new LinearLayout(getContext()));
                itemLayoutList.get(itemLayoutList.size() - 1).setTag("L" + itemTag);
                itemLayoutList.get(itemLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
                itemLayoutList.get(itemLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                // EditText for story
                itemViewList.add(new EditText(getContext()));
                itemViewList.get(itemViewList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(4 * itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
                // Button for deletion
                itemButtonList.add(new Button(getContext()));
                itemButtonList.get(itemButtonList.size() - 1).setTag("L" + itemTag + "B");
                itemButtonList.get(itemButtonList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
                itemButtonList.get(itemButtonList.size() - 1).setText("X");
                // Add them all
                itemLayoutList.get(itemLayoutList.size() - 1).addView(itemViewList.get(itemViewList.size() - 1));
                itemLayoutList.get(itemLayoutList.size() - 1).addView(itemButtonList.get(itemButtonList.size() - 1));
                itemList.addView(itemLayoutList.get(itemLayoutList.size() - 1));
                // Increment item counter
                itemTag = itemTag + 1;
                // Deletion function at button
                itemButtonList.get(itemButtonList.size() - 1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Find and remove pressed button and item
                        for (int i = 0; i < itemLayoutList.size(); i++) {
                            if (view.getTag().equals(itemLayoutList.get(i).getTag() + "B")) {
                                itemList.removeView(itemList.findViewWithTag(itemLayoutList.get(i).getTag()));
                                itemLayoutList.remove(i);
                                itemViewList.remove(i);
                                itemButtonList.remove(i);
                                items.remove(i);
                                break;
                            }
                        }
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
        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(Intent.createChooser(intent, "Select Video") , PICK_VIDEO);
            }
        });

        addAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(Intent.createChooser(intent, "Select Audio") , PICK_AUDIO);

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startDateYYYYInt;
                int startDateMMInt;
                int startDateDDInt;
                int startDateHHInt;
                int endDateYYYYInt;
                int endDateMMInt;
                int endDateDDInt;
                int endDateHHInt;
                try {
                    startDateYYYYInt = Integer.parseInt(startDateYYYY.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    startDateYYYYInt = 0;
                }
                try {
                    startDateMMInt = Integer.parseInt(startDateMM.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    startDateMMInt = 0;
                }
                try {
                    startDateDDInt = Integer.parseInt(startDateDD.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    startDateDDInt = 0;
                }
                try {
                    startDateHHInt = Integer.parseInt(startDateHH.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    startDateHHInt = 0;
                }
                try {
                    endDateYYYYInt = Integer.parseInt(endDateYYYY.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    endDateYYYYInt = 0;
                }
                try {
                    endDateMMInt = Integer.parseInt(endDateMM.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    endDateMMInt = 0;
                }
                try {
                    endDateDDInt = Integer.parseInt(endDateDD.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    endDateDDInt = 0;
                }
                try {
                    endDateHHInt = Integer.parseInt(endDateHH.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    endDateHHInt = 0;
                }
                
                String[] locations = new String[locationTextList.size()];
                for (int i = 0; i < locationTextList.size(); i ++) {
                    locations[i] = locationTextList.get(i).getText().toString();
                }

                for (int i = 0; i < items.size() ; i++){
                    if( items.get(i).equals("*")){
                        items.set(i, ((EditText)itemViewList.get(i)).getText().toString());
                    }
                    else{

                         //TODO upload files
                    }
                }
                clientAPI.createMemory(startDateYYYYInt, startDateMMInt, startDateDDInt, startDateHHInt,
                        endDateYYYYInt, endDateMMInt, endDateDDInt, endDateHHInt,
                        headline.getText().toString(), items, locations, getContext());
            }
        });

        return rootView;
    }

}
