package bounswe2018group1.cmpe451.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MemoryAdapter extends BaseAdapter {

    protected Context context;
    private int layoutResource;
    private JsonArray dataSource;
    private ClientAPI clientAPI;

    public MemoryAdapter(Context context, int resource, JsonArray dataSource) {
        this.context = context;
        this.layoutResource = resource;
        this.dataSource = dataSource;
        this.clientAPI = ClientAPI.getInstance(context);
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public JsonObject getItem(int position) {
        return dataSource.get(position).getAsJsonObject();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MemoryRowHolder holder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResource, parent, false);
            holder = new MemoryRowHolder(row);
            row.setTag(holder);
        } else {
            holder = (MemoryRowHolder) row.getTag();
        }

        // TODO: change all views in each memory_row
        JsonObject memory = getItem(position);
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
        holder.postDate.setText("Posted " + formattedTime);
        holder.memoryDate.setText(StringUtility.memoryDate(memory));
        holder.memoryTitle.setText(memoryTitle);
        holder.memoryTitle.getViewTreeObserver().addOnGlobalLayoutListener(
                new MyOnGlobalLayoutListener(holder)
        );
        holder.btnSeeMore.setOnClickListener(
                new MyOnClickListener(holder)
        );
        clientAPI.writeAuthor(holder.authorName, memory.get("userId").getAsString(), context);

        return row;
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        MemoryRowHolder holder;

        public MyOnGlobalLayoutListener(MemoryRowHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onGlobalLayout() {
            if (holder.expanded) {
                if (holder.memoryTitle.getLineCount() > 3) {
                    holder.expanded = false;
                    holder.btnSeeMore.setVisibility(View.VISIBLE);
                    holder.memoryTitle.setMaxLines(3);
                }
            }
            holder.memoryTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    class MyOnClickListener implements View.OnClickListener {
        MemoryRowHolder holder;

        public MyOnClickListener(MemoryRowHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            if (!holder.expanded) {
                holder.expanded = true;
                holder.memoryTitle.setMaxLines(10);
                holder.btnSeeMore.setText("View less");
            } else {
                holder.expanded = false;
                holder.memoryTitle.setMaxLines(3);
                holder.btnSeeMore.setText("View more");
            }
        }
    }

}
