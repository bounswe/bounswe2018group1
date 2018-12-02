package bounswe2018group1.cmpe451.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bounswe2018group1.cmpe451.MapsSearchActivity;
import bounswe2018group1.cmpe451.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private static final int PICK_MAP_POINT = 999;

    private Button mapSearch;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        mapSearch = v.findViewById(R.id.mapSearch);

        mapSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPointIntent = new Intent(getActivity(), MapsSearchActivity.class);
                startActivityForResult(pickPointIntent, PICK_MAP_POINT);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}
