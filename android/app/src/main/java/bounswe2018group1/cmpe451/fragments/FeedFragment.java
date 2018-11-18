package bounswe2018group1.cmpe451.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.concurrent.Semaphore;

import bounswe2018group1.cmpe451.MemoryViewActivity;
import bounswe2018group1.cmpe451.R;
import bounswe2018group1.cmpe451.helpers.ClientAPI;
import bounswe2018group1.cmpe451.helpers.MemoryAdapter;
import bounswe2018group1.cmpe451.helpers.ServerCallBack;

public class FeedFragment extends Fragment {

    private JsonArray dataSource = null;
    private Semaphore dataSourceUpdateLock = null;
    private ListView listView = null;
    private ClientAPI clientAPI = null;
    private int pageSize = 4;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        if (listView == null) listView = rootView.findViewById(R.id.listView);
        if(clientAPI == null) clientAPI = ClientAPI.getInstance(getContext());

        if (dataSource == null) dataSource = new JsonArray();
        if(dataSourceUpdateLock == null) dataSourceUpdateLock = new Semaphore(1);
        final MemoryAdapter adapter = new MemoryAdapter(rootView.getContext(), R.layout.memory_row, dataSource);
        listView.setAdapter(adapter);

        updateListView(adapter);
        implementScrollListener(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), MemoryViewActivity.class);
                i.putExtra("memory", adapter.getItem(position).toString());
                startActivity(i);
            }
        });

        return rootView;
    }

    // Implement scroll listener
    private void implementScrollListener(MemoryAdapter adapter) {
        listView.setOnScrollListener(new MyOnScrollListener(adapter));
    }

    private class MyOnScrollListener implements AbsListView.OnScrollListener {

        private boolean userScrolled;
        private MemoryAdapter adapter;

        MyOnScrollListener(MemoryAdapter adapter) {
            this.userScrolled = false;
            this.adapter = adapter;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // If scroll state is touch scroll then set userScrolled
            // true
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                userScrolled = true;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // Now check if userScrolled is true and also check if
            // the item is end then update list view and set
            // userScrolled to false
            if (userScrolled
                    && firstVisibleItem + visibleItemCount == totalItemCount) {

                userScrolled = false;
                updateListView(adapter);
            }
        }
    }

    // Method for repopulating recycler view
    private void updateListView(MemoryAdapter adapter) {
        if(dataSourceUpdateLock.tryAcquire()){

            clientAPI.getMemoryAll(dataSource.size()/pageSize, pageSize,
                    new MyServerCallBack(adapter), getContext());

        }
    }

    private class MyServerCallBack implements ServerCallBack {

        private MemoryAdapter adapter;

        MyServerCallBack(MemoryAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onSuccess(org.json.JSONObject result) {
            try {
                JsonArray content = new Gson().fromJson(
                        result.getJSONArray("content").toString(),
                        JsonArray.class);
                JsonArray contentDifference = new JsonArray();
                for(int i = dataSource.size()%pageSize, size = content.size(); i < size; ++i){
                    contentDifference.add(content.get(i));
                }
                dataSource.addAll(contentDifference);
                adapter.notifyDataSetChanged();
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
            dataSourceUpdateLock.release();
        }

        @Override
        public void onError() {
            dataSourceUpdateLock.release();
        }

    }

}
