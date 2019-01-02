package bounswe2018group1.cmpe451.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import bounswe2018group1.cmpe451.MainActivity;
import bounswe2018group1.cmpe451.MapsSearchActivity;
import bounswe2018group1.cmpe451.R;
import bounswe2018group1.cmpe451.SearchActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private static final int PICK_MAP_POINT = 999;

    private Button searchMap;
    private Button searchButton;
    private TextView searchText;
    private TextView searchTag;
    private TextView searchLocation;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        searchMap = v.findViewById(R.id.searchMap);
        searchButton = v.findViewById(R.id.searchButton);
        searchText = v.findViewById(R.id.searchText);
        searchTag = v.findViewById(R.id.searchTag);
        searchLocation = v.findViewById(R.id.searchLocation);

        searchMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPointIntent = new Intent(getActivity(), MapsSearchActivity.class);
                startActivityForResult(pickPointIntent, PICK_MAP_POINT);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("SessionID", ((MainActivity) getActivity()).getSessionID());
                i.putExtra("Text", searchText.getText().toString());
                //i.putExtra("S")
                startActivity(i);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}
