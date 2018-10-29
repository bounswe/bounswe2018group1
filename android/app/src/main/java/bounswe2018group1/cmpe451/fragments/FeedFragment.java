package bounswe2018group1.cmpe451.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import bounswe2018group1.cmpe451.R;
import bounswe2018group1.cmpe451.helpers.MemoryAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {

    private JsonArray dataSource;
    private ListView listView;

    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        listView = rootView.findViewById(R.id.listView);

        InputStream is = getResources().openRawResource(R.raw.memory_json_file);
        try {
            dataSource = new JsonParser().parse(
                    new BufferedReader(new InputStreamReader(is, "UTF-8"))
            ).getAsJsonArray();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MemoryAdapter adapter = new MemoryAdapter(rootView.getContext(), R.layout.memory_row, dataSource);
        listView.setAdapter(adapter);
        return rootView;
    }

}
