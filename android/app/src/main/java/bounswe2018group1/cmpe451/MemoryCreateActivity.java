package bounswe2018group1.cmpe451;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import bounswe2018group1.cmpe451.fragments.MemoryFragment;
import bounswe2018group1.cmpe451.helpers.NullResponseJsonObjectRequest;
import bounswe2018group1.cmpe451.helpers.URLs;
import bounswe2018group1.cmpe451.helpers.VolleySingleton;

public class MemoryCreateActivity extends AppCompatActivity {

    private ArrayList<Story> stories;
    private String des;
    private String headline;
    private ContactsAdapter adapter;
    private VolleySingleton volleySingleton = null;
    private void sendReq(){
        JSONObject postParams = new JSONObject();
        try {
            postParams.put("description", des);
            postParams.put("headline", headline);
            JSONArray stlist = new JSONArray();
            System.out.println(stories.size()  + "------*********************************************--------------------");
            for(Story s : stories){
                JSONObject js = new JSONObject();
                try{
                    js.put("city" , "" );
                    js.put("country" ,"" );
                    js.put("county" , "" );
                    js.put("description" , s.text );
                    js.put("district" , "" );
                    js.put("headline" ,"" );
                    JSONObject jss = new JSONObject();
                    try {
                        jss.put("latitude" , 0);
                        jss.put("longitude" , 0);
                    }

                    catch (org.json.JSONException e){}
                    js.put("locationDto" , jss);
                    js.put("time" , 0);
                }
                catch (org.json.JSONException e){}
                stlist.put(js);
            }
            postParams.put("storyList",stlist );
        }
        catch (org.json.JSONException e){

        }
        System.out.println(postParams + "****************************************************$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        JsonObjectRequest jsonObjReq = new NullResponseJsonObjectRequest(
                Request.Method.POST,
                "http://52.7.87.173:5000/memory", postParams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        if (response == null) {

                        } else if (response instanceof JSONObject) {
                            //Success Callback
                            JSONObject r = (JSONObject) response;
                            System.out.println("Response: " + r.toString());
                        } else {
                            System.out.println("Response: " + response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                        Toast.makeText(getApplicationContext(), "Post fail!", Toast.LENGTH_LONG).show();
                        System.err.println("Error in Post!");
                        error.printStackTrace();
                    }
                }
        );
        volleySingleton.addToRequestQueue(jsonObjReq);
    }

    public void addStory(Story s) {
        stories.add(s);
        adapter.notifyItemInserted(stories.size() - 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_create);
        volleySingleton = VolleySingleton.getInstance(this);
        stories = new ArrayList<>();
        Button createStoryButton = findViewById(R.id.story_button);
        Button done = findViewById(R.id.button);
        RecyclerView rv = findViewById(R.id.recycle);
        adapter = new ContactsAdapter(stories);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        EditText hl = findViewById(R.id.header);
        headline = hl.getText().toString();
        EditText desc = findViewById(R.id.desc);
        des = desc.getText().toString();
        createStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(stories.size() + "--------------------------------------------");
                FragmentManager fm = getSupportFragmentManager();
                MemoryFragment editNameDialogFragment = MemoryFragment.newInstance("Some Title");
                editNameDialogFragment.show(fm, "fragment_edit_name");
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO send to server
                sendReq();
                finish();
            }
        });
    }

    public static class Story {
        public String text;
        public String[] time;

        public Story(String t, String ds, String df) {
            text = t;
            time = new String[2];
            time[0] = ds;
            time[1] = df;
        }

        public String getText() {
            return text;
        }
    }

    public class ContactsAdapter extends
            RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView nameTextView;
            public Button deleteButton;
            public MyViewHolder(View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.story_text);
                deleteButton = itemView.findViewById(R.id.delete_button);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        stories.remove(pos);
                        adapter.notifyItemRemoved(pos);
                    }
                });
                /*editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                    }
                });*/
            }
        }

        private List<Story> storyList;
        public ContactsAdapter(List<Story> contacts) {
            storyList = contacts;
        }
        @Override
        public ContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View contactView = inflater.inflate(R.layout.story_view_holder, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ContactsAdapter.MyViewHolder viewHolder, int position) {
            Story contact = storyList.get(position);
            TextView textView = viewHolder.nameTextView;
            textView.setText(contact.getText());
        }
        // Returns the total count of items in the list
        @Override
        public int getItemCount() {
            return storyList.size();
        }
    }
}
