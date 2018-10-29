package bounswe2018group1.cmpe451.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MemoryAdapter extends BaseAdapter {

    protected Context context;
    protected int layoutResource;
    protected JsonArray dataSource;

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
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MemoryViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResource, parent, false);
            holder = new MemoryViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (MemoryViewHolder) row.getTag();
        }

        // TODO: change all views in each memory_row
        JsonObject memory = dataSource.get(position).getAsJsonObject();
        String memoryTitle = memory.get("headline").getAsString();
        String memoryDesc = memory.get("description").getAsString();
        holder.memoryTitle.setText(memoryTitle);
        holder.memoryDesc.setText(memoryDesc);

        return row;
    }
}
