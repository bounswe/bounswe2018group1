package bounswe2018group1.cmpe451.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import bounswe2018group1.cmpe451.MemoryView;
import bounswe2018group1.cmpe451.R;
import bounswe2018group1.cmpe451.helpers.MemoryAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {

    private JsonArray dataSource = null;
    private ListView listView = null;

    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        if (listView == null) listView = rootView.findViewById(R.id.listView);

        if (dataSource == null) {
            InputStream is = getResources().openRawResource(R.raw.memory_json_file);
            try {
                dataSource = new JsonParser().parse(
                        new BufferedReader(new InputStreamReader(is, "UTF-8"))
                ).getAsJsonArray();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        final MemoryAdapter adapter = new MemoryAdapter(rootView.getContext(), R.layout.memory_row, dataSource);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), MemoryView.class);
                i.putExtra("memory", adapter.getItem(position).toString());
                startActivity(i);
            }
        });

        return rootView;
    }

}
