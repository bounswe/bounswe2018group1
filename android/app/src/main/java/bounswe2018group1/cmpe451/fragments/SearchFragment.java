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

import java.util.ArrayList;

import bounswe2018group1.cmpe451.R;

public class SearchFragment extends Fragment {

    private Button addLocation;
    private LinearLayout locationList;
    ArrayList<LinearLayout> locationLayoutList;
    ArrayList<EditText> locationTextList;
    ArrayList<Button> locationButtonList;
    int locationTag = 0;

    public SearchFragment() {
        // Required empty public constructor
    }
    int a = 13;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        addLocation = rootView.findViewById(R.id.addLocation);
        locationList = rootView.findViewById(R.id.locationList);

        locationLayoutList = new ArrayList<LinearLayout>();
        locationTextList = new ArrayList<EditText>();
        locationButtonList = new ArrayList<Button>();

        addLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Horizontal layout for location bar
                locationLayoutList.add(new LinearLayout(getContext()));
                locationLayoutList.get(locationLayoutList.size() - 1).setTag("L" + locationTag);
                locationLayoutList.get(locationLayoutList.size() - 1).setOrientation(LinearLayout.HORIZONTAL);
                locationLayoutList.get(locationLayoutList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                // EditText for location
                locationTextList.add(new EditText(getContext()));
                locationTextList.get(locationTextList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                locationTextList.get(locationTextList.size() - 1).setHint("Location");
                // Button for deletion
                locationButtonList.add(new Button(getContext()));
                locationButtonList.get(locationButtonList.size() - 1).setTag("L" + locationTag + "B");
                locationButtonList.get(locationButtonList.size() - 1).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 4.0f));
                locationButtonList.get(locationButtonList.size() - 1).setText("X");
                // Add them all
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
                        System.out.println("Bura: " + view.getTag()); // TODO Remove
                        for (int i = 0; i < locationLayoutList.size(); i ++) {
                            System.out.println("Bu: " + locationLayoutList.get(i).getTag()); // TODO Remove
                            if (view.getTag().equals(locationLayoutList.get(i).getTag() + "B")) {
                                locationList.removeView(locationList.findViewWithTag(locationLayoutList.get(i).getTag()));
                                locationLayoutList.remove(i);
                                locationTextList.remove(i);
                                locationButtonList.remove(i);
                                break;
                            }
                        }
                    }
                });
            }
        });

        return rootView;
    }



}
