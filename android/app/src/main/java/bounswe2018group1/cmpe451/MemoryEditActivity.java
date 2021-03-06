package bounswe2018group1.cmpe451;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bounswe2018group1.cmpe451.helpers.ClientAPI;
import bounswe2018group1.cmpe451.helpers.ItemAdapter;
import bounswe2018group1.cmpe451.helpers.RealPathUtil;
import bounswe2018group1.cmpe451.helpers.StringUtility;
import bounswe2018group1.cmpe451.helpers.URLs;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MemoryEditActivity extends Activity {

    private static final int PICK_IMAGE = 1;
    private static final int PICK_VIDEO = 2;
    private static final int PICK_AUDIO = 3;
    private static final int PICK_MAP_POINT = 999;

    private JsonObject memory = null;
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
    private EditText tagList;
    private Button putMemory;
    private Button addImage;
    private Button addVideo;
    private Button addAudio;
    private Button addText;
    private Spinner intervalTime;
    private ClientAPI clientAPI;
    private int flag = 0;
    private Uri imageUri;
    private Uri videoUri;
    private Uri audioUri;
    private String sessionID;

    private ArrayList<Pair<String , String> > items = new ArrayList<>();

    protected void addLocationFunc() {
        // Horizontal layout for location bar
        locationLayoutList.add(new LinearLayout(MemoryEditActivity.this));
        locationLayoutList.get(locationLayoutList.size() - 1).setTag("L" + locationTag);
        locationLayoutList.get(locationLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
        locationLayoutList.get(locationLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // EditText for location
        locationTextList.add(new EditText(MemoryEditActivity.this));
        locationTextList.get(locationTextList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(4 * locationList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
        locationTextList.get(locationTextList.size() - 1).setHint("Location");
        // Button for deletion
        locationButtonList.add(new Button(MemoryEditActivity.this));
        locationButtonList.get(locationButtonList.size() - 1).setTag("L" + locationTag + "B");
        locationButtonList.get(locationButtonList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(locationList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
        locationButtonList.get(locationButtonList.size() - 1).setText("X");
        // Button for map
        locationMapList.add(new Button(MemoryEditActivity.this));
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
        locationMapList.get(locationMapList.size() - 1).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Open map and send tag to map activity
                        Intent pickPointIntent = new Intent(MemoryEditActivity.this, MapsCreateActivity.class);
                        pickPointIntent.putExtra("TagOfMap", view.getTag().toString().trim());
                        startActivityForResult(pickPointIntent, PICK_MAP_POINT);
                    }
                });
    }

    protected void addTextFunc() {
        if(flag == 1)
            return;
        items.add(new Pair<String, String>("text", ""));
        itemLayoutList.add(new LinearLayout(MemoryEditActivity.this));
        itemLayoutList.get(itemLayoutList.size() - 1).setTag("L" + itemTag);
        itemLayoutList.get(itemLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
        itemLayoutList.get(itemLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // EditText for story
        itemViewList.add(new EditText(MemoryEditActivity.this));
        itemViewList.get(itemViewList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(4 * itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
        // Button for deletion
        itemButtonList.add(new Button(MemoryEditActivity.this));
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
                if(flag==1)
                    return;
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

    protected void addImageFunc(String url) {
        Uri imageUri = Uri.parse(url);

        itemLayoutList.add(new LinearLayout(this));
        itemLayoutList.get(itemLayoutList.size() - 1).setTag("L" + itemTag);
        itemLayoutList.get(itemLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
        itemLayoutList.get(itemLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // Image view for image
        itemViewList.add(new ImageView(this));
        itemViewList.get(itemViewList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(4 * itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
        //((ImageView)itemViewList.get(itemViewList.size()-1)).setImageURI(imageUri);
        // Button for deletion
        itemButtonList.add(new Button(this));
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
                if(flag == 1)
                    return;
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
        Picasso.get()
                .load(imageUri)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fit()
                .centerInside()
                .into(((ImageView)itemViewList.get(itemViewList.size()-1)));

        items.add(new Pair<String, String>("photo" , url));
    }

    protected void addVideoFunc(String url) {
        Uri videoUri = Uri.parse(url);

        itemLayoutList.add(new LinearLayout(this));
        itemLayoutList.get(itemLayoutList.size() - 1).setTag("L" + itemTag);
        itemLayoutList.get(itemLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
        itemLayoutList.get(itemLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // Video view for image
        itemViewList.add(new VideoView(this));
        itemViewList.get(itemViewList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(4 * itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
        ((VideoView)itemViewList.get(itemViewList.size()-1)).setVideoURI(videoUri);
        // Button for deletion
        itemButtonList.add(new Button(this));
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
                if(flag==1)
                    return;
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

        items.add(new Pair<String, String>("video" , url));
    }

    protected void addAudioFunc(String url) {
        Uri audioUri = Uri.parse(url);

        itemLayoutList.add(new LinearLayout(this));
        itemLayoutList.get(itemLayoutList.size() - 1).setTag("L" + itemTag);
        itemLayoutList.get(itemLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
        itemLayoutList.get(itemLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // Text view for audio file's name
        itemViewList.add(new TextView(this));
        itemViewList.get(itemViewList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(4 * itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
        ((TextView)itemViewList.get(itemViewList.size()-1)).setText((new File(audioUri.toString())).getName());
        // Button for deletion
        itemButtonList.add(new Button(this));
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
                if(flag==1)
                    return;
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

        items.add(new Pair<String, String>("audio" , url));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // Pick image from gallery
        if(PICK_IMAGE == requestCode && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();

            File imageFile = new File(RealPathUtil.getRealPath(this, imageUri));

            // Prepare request and send
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", imageFile.getName(), RequestBody.create(MediaType.parse("image"), imageFile))  // TODO add extension to image, .bmp .png .jpg
                    .build();

            Request request = new Request.Builder()
                    .url(URLs.URL_MEDIA)
                    .addHeader("Cookie", ("JSESSIONID=" + sessionID))
                    .post(body)
                    .build();

            OkHttpClient client = new OkHttpClient();

            //rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            flag = 1;

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.err.println("Create Fragment:  picture upload failed in send");
                }

                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()) {
                        System.out.println("Create Fragment:  picture upload success");
                        try {
                            String url = response.body().string();
                            items.add(new Pair<String, String>("photo" , url));
                            System.out.println("Uploaded picture url -> " + url);
                            //enableUI();
                            flag = 0;

                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println("Create Fragment:  picture upload failed in return");
                    }
                }
            });

            itemLayoutList.add(new LinearLayout(this));
            itemLayoutList.get(itemLayoutList.size() - 1).setTag("L" + itemTag);
            itemLayoutList.get(itemLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
            itemLayoutList.get(itemLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            // Image view for image
            itemViewList.add(new ImageView(this));
            itemViewList.get(itemViewList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(4 * itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
            ((ImageView)itemViewList.get(itemViewList.size()-1)).setImageURI(imageUri);
            // Button for deletion
            itemButtonList.add(new Button(this));
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
                    if(flag == 1)
                        return;
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
            File videoFile = new File(RealPathUtil.getRealPath(this, videoUri));

            // Prepare request and send
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", videoFile.getName(), RequestBody.create(MediaType.parse("video"), videoFile))  // TODO add extension to image, .bmp .png .jpg
                    .build();

            Request request = new Request.Builder()
                    .url(URLs.URL_MEDIA)
                    .addHeader("Cookie", ("JSESSIONID=" + sessionID))
                    .post(body)
                    .build();

            OkHttpClient client = new OkHttpClient();
            //rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            flag = 1;

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.err.println("Create Fragment:  video upload failed in send");
                }

                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()) {
                        System.out.println("Create Fragment: video upload success");
                        try {
                            String url = response.body().string();
                            items.add(new Pair<String, String>("video" , url));
                            System.out.println("Uploaded video url -> " + url);
                            //enableUI();
                            flag = 0;
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println("Create Fragment:  video upload failed in return");
                    }
                }
            });
            itemLayoutList.add(new LinearLayout(this));
            itemLayoutList.get(itemLayoutList.size() - 1).setTag("L" + itemTag);
            itemLayoutList.get(itemLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
            itemLayoutList.get(itemLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            // Video view for image
            itemViewList.add(new VideoView(this));
            itemViewList.get(itemViewList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(4 * itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
            ((VideoView)itemViewList.get(itemViewList.size()-1)).setVideoURI(videoUri);
            // Button for deletion
            itemButtonList.add(new Button(this));
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
                    if(flag==1)
                        return;
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
            File audioFile = new File(RealPathUtil.getRealPath(this, audioUri));

            // Prepare request and send
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", audioFile.getName(), RequestBody.create(MediaType.parse("audio"), audioFile))  // TODO add extension to image, .bmp .png .jpg
                    .build();

            Request request = new Request.Builder()
                    .url(URLs.URL_MEDIA)
                    .addHeader("Cookie", ("JSESSIONID=" + sessionID))
                    .post(body)
                    .build();

            OkHttpClient client = new OkHttpClient();

            //rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            flag = 1;

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.err.println("Create Fragment:  audio upload failed in send");
                }

                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()) {
                        System.out.println("Create Fragment:  audio upload success");
                        try {
                            String url = response.body().string();
                            items.add(new Pair<String, String>("audio" , url));
                            System.out.println("Uploaded audio url -> " + url);
                            //enableUI();
                            flag = 0;
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println("Create Fragment:  audio upload failed in return");
                    }
                }
            });
            itemLayoutList.add(new LinearLayout(this));
            itemLayoutList.get(itemLayoutList.size() - 1).setTag("L" + itemTag);
            itemLayoutList.get(itemLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
            itemLayoutList.get(itemLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            // Text view for audio file's name
            itemViewList.add(new TextView(this));
            itemViewList.get(itemViewList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(4 * itemList.getWidth() / 6, ViewGroup.LayoutParams.MATCH_PARENT));
            ((TextView)itemViewList.get(itemViewList.size()-1)).setText((new File(audioUri.toString())).getName());
            // Button for deletion
            itemButtonList.add(new Button(this));
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
                    if(flag==1)
                        return;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_edit);

        if (memory == null) {
            memory = new JsonParser().parse(
                    getIntent().getStringExtra("memory")
            ).getAsJsonObject();
        }
        sessionID = getIntent().getStringExtra("sessionID");
        // Server calls
        if (clientAPI == null) clientAPI = ClientAPI.getInstance(this);
        //rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        // Connect fields
        headline = this.findViewById(R.id.activity_headline);
        startDateYYYY = this.findViewById(R.id.activity_startDateYYYY);
        startDateMM = this.findViewById(R.id.activity_startDateMM);
        startDateDD = this.findViewById(R.id.activity_startDateDD);
        startDateHH = this.findViewById(R.id.activity_startDateHH);
        endDateYYYY = this.findViewById(R.id.activity_endDateYYYY);
        endDateMM = this.findViewById(R.id.activity_endDateMM);
        endDateDD = this.findViewById(R.id.activity_endDateDD);
        endDateHH = this.findViewById(R.id.activity_endDateHH);
        tagList = this.findViewById(R.id.activity_tags);
        addLocation = this.findViewById(R.id.activity_addLocation);
        locationList = this.findViewById(R.id.activity_locationList);
        itemList = this.findViewById(R.id.activity_itemList);
        putMemory = this.findViewById(R.id.activity_put);
        addImage = this.findViewById(R.id.activity_addImage);
        addAudio = this.findViewById(R.id.activity_addAudio);
        addText = this.findViewById(R.id.activity_addText);
        addVideo = this.findViewById(R.id.activity_addVideo);
        intervalTime = this.findViewById(R.id.activity_intervals);
        // List of location fields
        locationLayoutList = new ArrayList<LinearLayout>();
        locationTextList = new ArrayList<EditText>();
        locationButtonList = new ArrayList<Button>();
        locationMapList = new ArrayList<Button>();

        List<Integer> list = new ArrayList<>();
        for(int i = 1900; i < 2018 ; i+=10)
            list.add(i);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalTime.setAdapter(adapter);

        headline.setText(memory.get("headline").getAsString(), TextView.BufferType.EDITABLE);
        startDateYYYY.setText(String.valueOf(memory.get("startDateYYYY").getAsInt()), TextView.BufferType.EDITABLE);
        startDateMM.setText(String.valueOf(memory.get("startDateMM").getAsInt()), TextView.BufferType.EDITABLE);
        startDateDD.setText(String.valueOf(memory.get("startDateDD").getAsInt()), TextView.BufferType.EDITABLE);
        startDateHH.setText(String.valueOf(memory.get("startDateHH").getAsInt()), TextView.BufferType.EDITABLE);
        endDateYYYY.setText(String.valueOf(memory.get("endDateYYYY").getAsInt()), TextView.BufferType.EDITABLE);
        endDateMM.setText(String.valueOf(memory.get("endDateMM").getAsInt()), TextView.BufferType.EDITABLE);
        endDateDD.setText(String.valueOf(memory.get("endDateDD").getAsInt()), TextView.BufferType.EDITABLE);
        endDateHH.setText(String.valueOf(memory.get("endDateHH").getAsInt()), TextView.BufferType.EDITABLE);
        locationList.getViewTreeObserver().addOnGlobalLayoutListener(
                new LocationListOnGlobalLayoutListener());
        JsonArray listOfTags = memory.getAsJsonArray("listOfTags");
        String[] tags = new String[listOfTags.size()];
        for(int i = 0; i < tags.length; ++i) {
            tags[i] = listOfTags.get(i).getAsJsonObject().get("text").getAsString();
        }
        tagList.setText(StringUtility.join(",", tags), TextView.BufferType.EDITABLE);
        itemList.getViewTreeObserver().addOnGlobalLayoutListener(
                new ItemListOnGlobalLayoutListener());

        addLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addLocationFunc();
            }
        });

        itemLayoutList = new ArrayList<LinearLayout>();
        itemButtonList = new ArrayList<Button>();
        itemViewList = new ArrayList<View>();
        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextFunc();
            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1)
                    return;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1)
                    return;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(Intent.createChooser(intent, "Select Video") , PICK_VIDEO);
            }
        });

        addAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1)
                    return;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(Intent.createChooser(intent, "Select Audio") , PICK_AUDIO);

            }
        });

        putMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1)
                    return;
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
                if((startDateDDInt+startDateHHInt+startDateMMInt+startDateYYYYInt) == 0 ){
                    startDateYYYYInt = (int)intervalTime.getSelectedItem();
                }
                String[] locations = new String[locationTextList.size()];
                for (int i = 0; i < locationTextList.size(); i ++) {
                    locations[i] = locationTextList.get(i).getText().toString();
                }

                for (int i = 0; i < items.size() ; i++){
                    if( items.get(i).first.equals("text")){
                        System.out.println(i);
                        items.set(i, new Pair<String, String>("text" ,((EditText)itemViewList.get(i)).getText().toString()));
                    }
                }

                clientAPI.putMemory(memory.get("id").getAsInt(),
                        startDateYYYYInt, startDateMMInt, startDateDDInt, startDateHHInt,
                        endDateYYYYInt, endDateMMInt, endDateDDInt, endDateHHInt,
                        headline.getText().toString(), items, locations, MemoryEditActivity.this,
                        tagList.getText().toString().split(","));
            }
        });
    }

    class LocationListOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            ArrayList<String> locations = StringUtility.memoryLocationList(
                    memory.getAsJsonArray("listOfLocations"));
            for(int i = 0, size = locations.size(); i < size; ++i) {
                addLocationFunc();
                locationTextList.get(locationTextList.size() - 1).setText(locations.get(i), TextView.BufferType.EDITABLE);
            }
            locationList.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    class ItemListOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            JsonArray listOfItems = memory.getAsJsonArray("listOfItems");
            for(int i = 0, size = listOfItems.size(); i < size; ++i) {
                JsonObject item = listOfItems.get(i).getAsJsonObject();
                if(!item.get("body").isJsonNull() && !item.get("body").getAsString().isEmpty()) {
                    addTextFunc();
                    ((EditText)itemViewList.get(itemViewList.size() - 1)).setText(
                            item.get("body").getAsString(), TextView.BufferType.EDITABLE);
                } else if(!item.get("url").isJsonNull()) {
                    Uri itemUri = Uri.parse(item.get("url").getAsString());
                    String type = ItemAdapter.getMimeType(itemUri, MemoryEditActivity.this);
                    if(type.startsWith("image")) {
                        addImageFunc(itemUri.toString());
                    } else if(type.startsWith("video")) {
                        addVideoFunc(itemUri.toString());
                    } else if(type.startsWith("audio")) {
                        addAudioFunc(itemUri.toString());
                    } else {
                        // TODO: HANDLE UNKNOWN FILE TYPE IN ITEM URL!!!
                    }
                } else {
                    // TODO: HANDLE EMPTY MEMORY ITEM!!!
                }
            }
            itemList.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

}
