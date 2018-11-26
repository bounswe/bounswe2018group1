package bounswe2018group1.cmpe451;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bounswe2018group1.cmpe451.helpers.ClientAPI;
import bounswe2018group1.cmpe451.helpers.ItemAdapter;
import bounswe2018group1.cmpe451.helpers.StringUtility;

public class MemoryViewActivity extends AppCompatActivity {

    private JsonArray itemDataSource = null;
    private JsonObject memory = null;
    private ImageView avatar = null;
    private TextView authorName = null, postDate = null,
            memoryDate = null, memoryLocation = null, memoryTitle = null;
    private ListView itemListView = null;
    private ClientAPI clientAPI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_view);

        if (avatar == null) avatar = findViewById(R.id.avatar);
        if (authorName == null) authorName = findViewById(R.id.authorName);
        if (postDate == null) postDate = findViewById(R.id.postDate);
        if (memoryDate == null) memoryDate = findViewById(R.id.memoryDate);
        if (memoryLocation == null) memoryLocation = findViewById(R.id.memoryLocation);
        if (memoryTitle == null) memoryTitle = findViewById(R.id.memoryTitle);
        if (itemListView == null) itemListView = findViewById(R.id.itemListView);
        if (clientAPI == null) clientAPI = ClientAPI.getInstance(this);

        if (memory == null) {
            memory = new JsonParser().parse(
                    getIntent().getStringExtra("memory")
            ).getAsJsonObject();
        }
        //Prepare memory
        String postDate = memory.get("dateOfCreation").getAsString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedTime;
        try {
            Date d = sdf.parse(postDate);
            formattedTime = output.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
            formattedTime = "(invalid date)";
        }
        String memoryTitle = memory.get("headline").getAsString();
        this.postDate.setText("Posted " + formattedTime);
        this.memoryDate.setText(StringUtility.memoryDate(memory));
        this.memoryLocation.setText(
                StringUtility.memoryLocation(memory.get("listOfLocations").getAsJsonArray()));
        this.memoryTitle.setText(memoryTitle);
        clientAPI.writeAuthor(this.authorName, memory.get("userId").getAsString(), this);
        //Prepare items
        if (itemDataSource == null) itemDataSource = memory.getAsJsonArray("listOfItems");
        final ItemAdapter adapter = new ItemAdapter(this, R.layout.item_row, itemDataSource);
        itemListView.setAdapter(adapter);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: action for when an item is clicked
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
