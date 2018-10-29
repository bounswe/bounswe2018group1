package bounswe2018group1.cmpe451;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bounswe2018group1.cmpe451.helpers.StoryAdapter;

public class MemoryViewActivity extends AppCompatActivity {

    private JsonArray storyDataSource = null;
    private JsonObject memory = null;
    private ImageView avatar = null;
    private TextView authorName = null, postDate = null, memoryTitle = null, memoryDesc = null;
    private ListView storyListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (avatar == null) avatar = findViewById(R.id.avatar);
        if (authorName == null) authorName = findViewById(R.id.authorName);
        if (postDate == null) postDate = findViewById(R.id.postDate);
        if (memoryTitle == null) memoryTitle = findViewById(R.id.memoryTitle);
        if (memoryDesc == null) memoryDesc = findViewById(R.id.memoryDesc);
        if (storyListView == null) storyListView = findViewById(R.id.storyListView);

        setSupportActionBar(toolbar);
        if (memory == null) {
            memory = new JsonParser().parse(
                    getIntent().getStringExtra("memory")
            ).getAsJsonObject();
        }
        String memoryTitleText = memory.get("headline").getAsString();
        String memoryDescText = memory.get("description").getAsString();
        memoryTitle.setText(memoryTitleText);
        memoryDesc.setText(memoryDescText);
        if (storyDataSource == null) storyDataSource = memory.getAsJsonArray("storyList");
        final StoryAdapter adapter = new StoryAdapter(this, R.layout.story_row, storyDataSource);
        storyListView.setAdapter(adapter);
        storyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: action for when a story is clicked
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSettings:
                // TODO: open settings
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
