package bounswe2018group1.cmpe451;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.concurrent.Semaphore;

import bounswe2018group1.cmpe451.helpers.ClientAPI;
import bounswe2018group1.cmpe451.helpers.MemoryAdapter;
import bounswe2018group1.cmpe451.helpers.Pointer;
import bounswe2018group1.cmpe451.helpers.ServerCallBack;

public class SearchActivity extends AppCompatActivity {

    private Pointer<JsonArray> dataSourcePtr = null;
    private Semaphore dataSourceUpdateLock = null;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private ListView listView = null;
    private MemoryAdapter adapter = null;
    private ClientAPI clientAPI = null;
    private int pageSize = 3;
    private static int MY_CHILD_ACTIVITY = MemoryViewActivity.class.hashCode() & 0xFFFF;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        text = bundle.getString("Text");

        if (swipeRefreshLayout == null) swipeRefreshLayout = this.findViewById(R.id.swipeRefreshLayout);
        if (listView == null) listView = this.findViewById(R.id.listView);
        if(clientAPI == null) clientAPI = ClientAPI.getInstance(this);

        if (dataSourcePtr == null) dataSourcePtr = new Pointer<>(new JsonArray());
        if(dataSourceUpdateLock == null) dataSourceUpdateLock = new Semaphore(1);
        if(adapter == null) adapter = new MemoryAdapter(this, R.layout.memory_row, dataSourcePtr);
        listView.setAdapter(adapter);

        updateListView(adapter);
        //implementScrollListener(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayoutOnRefreshListener(adapter));
        // Scheme colors for animation
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );

        listView.setOnItemClickListener(new ListViewOnItemClickListener(adapter));
    }

    class SwipeRefreshLayoutOnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {

        private MemoryAdapter memoryAdapter;

        SwipeRefreshLayoutOnRefreshListener(MemoryAdapter memoryAdapter) {
            this.memoryAdapter = memoryAdapter;
        }

        @Override
        public void onRefresh() {
            try {
                dataSourceUpdateLock.acquire();
                dataSourcePtr.data = new JsonArray();
                memoryAdapter.notifyDataSetChanged();
                dataSourceUpdateLock.release();
                updateListView(memoryAdapter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Handler().postDelayed(new Runnable() {
                @Override public void run() {
                    // Stop animation (This will be after 3 seconds)
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 500); // Delay in millis
        }
    }

    class ListViewOnItemClickListener implements AdapterView.OnItemClickListener {

        private MemoryAdapter memoryAdapter;

        ListViewOnItemClickListener(MemoryAdapter memoryAdapter) {
            this.memoryAdapter = memoryAdapter;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(SearchActivity.this, MemoryViewActivity.class);
            i.putExtra("memory", memoryAdapter.getItem(position).toString());
            i.putExtra("memoryIndex", position);
            startActivityForResult(i, MY_CHILD_ACTIVITY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_CHILD_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                // TODO Extract the data returned from the child Activity.
                boolean isDeleted = data.getBooleanExtra("isDeleted", false);
                if(isDeleted) {
                    int deletedIndex = data.getIntExtra("deletedIndex", -1);
                    dataSourcePtr.data.remove(deletedIndex);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    // Method for repopulating recycler view
    private void updateListView(MemoryAdapter adapter) {
        if(dataSourceUpdateLock.tryAcquire()){

            clientAPI.filterMemory(text,
                    new MyServerCallBack(adapter), this);

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
                for(int i = dataSourcePtr.data.size()%pageSize, size = content.size(); i < size; ++i){
                    contentDifference.add(content.get(i));
                }
                dataSourcePtr.data.addAll(contentDifference);
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
