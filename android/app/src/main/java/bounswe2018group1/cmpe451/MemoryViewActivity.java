package bounswe2018group1.cmpe451;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bounswe2018group1.cmpe451.helpers.CircleTransform;
import bounswe2018group1.cmpe451.helpers.ClientAPI;
import bounswe2018group1.cmpe451.helpers.CommentAdapter;
import bounswe2018group1.cmpe451.helpers.ItemAdapter;
import bounswe2018group1.cmpe451.helpers.ServerCallBack;
import bounswe2018group1.cmpe451.helpers.StringUtility;

public class MemoryViewActivity extends AppCompatActivity {

    private JsonArray itemDataSource = null;
    private JsonObject memory = null;
    private ImageView avatar = null;
    private TextView authorName = null, postDate = null,
            memoryDate = null, memoryLocation = null, memoryTitle = null;
    private LinearLayout itemListView = null;
    private LinearLayout memoryTagLayout = null;
    private Button editButton = null;
    private Button addCommentButton = null;
    private ImageView like = null;
    private int likeAmount;
    private TextView likesText = null;
    private TextView commentsText = null;
    private LinearLayout commentLayout = null;
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
        if (addCommentButton == null) addCommentButton = findViewById(R.id.addCommentButton);
        if (like == null) like = findViewById(R.id.like);
        if (likesText == null) likesText = findViewById(R.id.likesText);
        if (commentsText == null) commentsText = findViewById(R.id.commentsText);
        if (commentLayout == null) commentLayout = findViewById(R.id.commentLayout);
        if (clientAPI == null) clientAPI = ClientAPI.getInstance(this);

        if (memory == null) {
            memory = new JsonParser().parse(
                    getIntent().getStringExtra("memory")
            ).getAsJsonObject();
        }
    }

    private void initProfilePicture() {
        if(!memory.get("userProfilePicUrl").isJsonNull() &&
                !memory.get("userProfilePicUrl").getAsString().isEmpty()) {
            Uri itemUri = Uri.parse(memory.get("userProfilePicUrl").getAsString());
            Picasso.get()
                    .load(itemUri)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .fit()
                    .centerInside()
                    .transform(new CircleTransform())
                    .into(this.avatar);
        }
    }

    private void initItems(ItemAdapter adapter) {
        for(int i = 0, sz = adapter.getCount(); i < sz; ++i) {
            View itemRow = adapter.getView(i, null, this.itemListView);
            this.itemListView.addView(itemRow);
        }
    }

    private void initMemoryTags() {
        if(memory.getAsJsonArray("listOfTags").size() == 0) {
            this.memoryTagLayout.setVisibility(View.GONE);
        } else {
            for(int i = 0, tag_size = memory.getAsJsonArray("listOfTags").size(); i < tag_size; ++i) {
                TextView tag_text = new TextView(this);
                tag_text.setText(memory.getAsJsonArray("listOfTags").get(i).
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

    private void initComments(CommentAdapter adapter) {
        if(adapter.getCount() == 0) { // If there are no comments
            this.commentsText.setVisibility(View.GONE);
            this.commentLayout.setVisibility(View.GONE);
        } else {
            for(int i = 0, sz = adapter.getCount(); i < sz; ++i) {
                View commentRow = adapter.getView(i, null, this.commentLayout);
                this.commentLayout.addView(commentRow);
            }
        }
    }

    private void initEventHandlers() {
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
        this.initProfilePicture();
        this.authorName.setText(StringUtility.join(" ", fullName));
        this.postDate.setText("Posted " + formattedTime);
        this.memoryDate.setText(StringUtility.memoryDate(memory));
        this.memoryLocation.setText(
                StringUtility.memoryLocation(memory.getAsJsonArray("listOfLocations")));
        this.memoryTitle.setText(memoryTitle);
        //Prepare items
        if (itemDataSource == null) itemDataSource = memory.getAsJsonArray("listOfItems");
        this.initItems(new ItemAdapter(this, R.layout.item_row, itemDataSource));
        this.initMemoryTags();
        // Show 'Edit' button if the current user owns it
        if(!memory.get("userId").isJsonNull()) {
            clientAPI.getCurrentUser(new EditButtonServerCallBack(editButton,
                    memory.get("userId").getAsInt()), this);
        }
        // TODO: GET LIKE STATUS OF THE CURRENT USER
        this.like.setTag(R.drawable.ic_thumb_up_black_24dp);
        this.likesText.setText("" + likeAmount + " likes");
        // TODO: GET THE ACTUAL COMMENTS BELONGING TO THIS MEMORY
        JsonArray commentDataSource = null;
        InputStream is = getResources().openRawResource(R.raw.comment_json_file);
        try {
            commentDataSource = new JsonParser().parse(
                    new BufferedReader(new InputStreamReader(is, "UTF-8"))
            ).getAsJsonArray();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.initComments(new CommentAdapter(this, R.layout.comment_row, commentDataSource));

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

}
