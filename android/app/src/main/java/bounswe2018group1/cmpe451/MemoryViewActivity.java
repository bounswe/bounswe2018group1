package bounswe2018group1.cmpe451;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import bounswe2018group1.cmpe451.helpers.ServerCallBack;
import bounswe2018group1.cmpe451.helpers.StringUtility;

public class MemoryViewActivity extends AppCompatActivity {

    private JsonArray itemDataSource = null;
    private JsonObject memory = null;
    private ImageView avatar = null;
    private TextView authorName = null, postDate = null,
            memoryDate = null, memoryLocation = null, memoryTitle = null;
    private ListView itemListView = null;
    private LinearLayout memoryTagLayout = null;
    private Button editButton = null;
    private ImageView like;
    private int likeAmount;
    private TextView likesText;
    private ClientAPI clientAPI = null;

    private void initLocalVariables() {
        if (avatar == null) avatar = findViewById(R.id.avatar);
        if (authorName == null) authorName = findViewById(R.id.authorName);
        if (postDate == null) postDate = findViewById(R.id.postDate);
        if (memoryDate == null) memoryDate = findViewById(R.id.memoryDate);
        if (memoryLocation == null) memoryLocation = findViewById(R.id.memoryLocation);
        if (memoryTitle == null) memoryTitle = findViewById(R.id.memoryTitle);
        if (itemListView == null) itemListView = findViewById(R.id.itemListView);
        if (memoryTagLayout == null) memoryTagLayout = findViewById(R.id.memoryTagLayout);
        if (editButton == null) editButton = findViewById(R.id.editButton);
        if (like == null) like = findViewById(R.id.like);
        if (likesText == null) likesText = findViewById(R.id.likesText);
        if (clientAPI == null) clientAPI = ClientAPI.getInstance(this);

        if (memory == null) {
            memory = new JsonParser().parse(
                    getIntent().getStringExtra("memory")
            ).getAsJsonObject();
        }
    }

    private void initMemoryTags() {
        if(memory.get("listOfTags").getAsJsonArray().size() == 0) {
            this.memoryTagLayout.setVisibility(View.INVISIBLE);
        } else {
            for(int i = 0, tag_size = memory.get("listOfTags").getAsJsonArray().size(); i < tag_size; ++i) {
                TextView tag_text = new TextView(this);
                tag_text.setText(memory.get("listOfTags").getAsJsonArray().get(i).
                        getAsJsonObject().get("text").getAsString());
                tag_text.setTypeface(null, Typeface.BOLD);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                Resources r = this.getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        8,  // 8dp
                        r.getDisplayMetrics()
                );
                params.setMarginEnd(px);
                tag_text.setLayoutParams(params);
                tag_text.setBackgroundResource(R.drawable.rounded_red_border);
                this.memoryTagLayout.addView(tag_text);
            }
        }
    }

    private void initEventHandlers() {
        this.itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: action for when an item is clicked
            }
        });
        this.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MemoryViewActivity.this, MemoryEditActivity.class);
                i.putExtra("memory", memory.toString());
                startActivity(i);
            }
        });
        this.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: SEND THE ACTUAL LIKE REQUEST TO BACKEND
                if(like.getTag().equals(R.drawable.ic_thumb_up_black_24dp)) {
                    like.setImageResource(R.drawable.ic_thumb_up_blue_24dp);
                    like.setTag(R.drawable.ic_thumb_up_blue_24dp);
                    likesText.setText("" + ++likeAmount + " likes");
                } else if(like.getTag().equals(R.drawable.ic_thumb_up_blue_24dp)) {
                    like.setImageResource(R.drawable.ic_thumb_up_black_24dp);
                    like.setTag(R.drawable.ic_thumb_up_black_24dp);
                    likesText.setText("" + --likeAmount + " likes");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_view);
        this.initLocalVariables();

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
        String[] fullName = new String[]{
                memory.get("userFirstName").getAsString(),
                memory.get("userLastName").getAsString()};
        // TODO: GET THE AMOUNT OF LIKES AND LIKE STATUS OF THE CURRENT USER
        likeAmount = 1731;
        clientAPI.printAvatar(this.avatar, memory.get("userId").getAsString(), this);
        this.authorName.setText(StringUtility.join(" ", fullName));
        this.postDate.setText("Posted " + formattedTime);
        this.memoryDate.setText(StringUtility.memoryDate(memory));
        this.memoryLocation.setText(
                StringUtility.memoryLocation(memory.get("listOfLocations").getAsJsonArray()));
        this.memoryTitle.setText(memoryTitle);
        //Prepare items
        if (itemDataSource == null) itemDataSource = memory.getAsJsonArray("listOfItems");
        final ItemAdapter adapter = new ItemAdapter(this, R.layout.item_row, itemDataSource);
        this.itemListView.setAdapter(adapter);
        this.initMemoryTags();
        // Show 'Edit' button if the current user owns it
        if(!memory.get("userId").isJsonNull()) {
            clientAPI.getCurrentUser(new EditButtonServerCallBack(editButton,
                    memory.get("userId").getAsInt()), this);
        }
        // Initially the like button is grey (not liked yet)
        this.like.setTag(R.drawable.ic_thumb_up_black_24dp);
        this.likesText.setText("" + likeAmount + " likes");

        // TODO: ListView inside ScrollView does not expand, FIX IT!!!
//        this.itemListView.setOnTouchListener(new View.OnTouchListener() {
//            // Setting on Touch Listener for handling the touch inside ScrollView
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // Disallow the touch request for parent scroll on touch of child view
//                //v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
//        setListViewHeightBasedOnChildren(this.itemListView);

        this.initEventHandlers();
    }

    private class EditButtonServerCallBack implements ServerCallBack {

        private Button editButton;
        private int memoryUserId;

        EditButtonServerCallBack(Button editButton, int memoryUserId) {
            this.editButton = editButton;
            this.memoryUserId = memoryUserId;
        }

        @Override
        public void onSuccess(org.json.JSONObject result) {
            try {
                if(result.getInt("id") == memoryUserId) {
                    editButton.setVisibility(View.VISIBLE);
                }
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError() {}

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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        BaseAdapter listAdapter = (BaseAdapter) listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            view.measure(0, 0);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
