package bounswe2018group1.cmpe451.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MemoryAdapter extends BaseAdapter {

    protected Context context;
    private int layoutResource;
    private JsonArray dataSource;

    public MemoryAdapter(Context context, int resource, JsonArray dataSource) {
        this.context = context;
        this.layoutResource = resource;
        this.dataSource = dataSource;
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
        String memoryTitle = memory.get("headline").getAsString();
        String memoryDesc = memory.get("description").getAsString();
        holder.memoryTitle.setText(memoryTitle);
        holder.memoryDesc.setText(memoryDesc);
        holder.memoryDesc.getViewTreeObserver().addOnGlobalLayoutListener(
                new MyOnGlobalLayoutListener(holder)
        );
        holder.btnSeeMore.setOnClickListener(
                new MyOnClickListener(holder)
        );

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
                if (holder.memoryDesc.getLineCount() > 3) {
                    holder.expanded = false;
                    holder.btnSeeMore.setVisibility(View.VISIBLE);
                    holder.memoryDesc.setMaxLines(3);
                }
            }
            holder.memoryDesc.getViewTreeObserver().removeOnGlobalLayoutListener(this);
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
            holder.memoryDesc.setMaxLines(10);
            holder.btnSeeMore.setText("View less");
        } else {
            holder.expanded = false;
            holder.memoryDesc.setMaxLines(3);
            holder.btnSeeMore.setText("View more");
        }
    }
}

}
