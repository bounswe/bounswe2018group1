package bounswe2018group1.cmpe451.helpers;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ItemAdapter extends BaseAdapter {

    protected Context context;
    private int layoutResource;
    private JsonArray itemDataSource;

    public ItemAdapter(Context context, int resource, JsonArray itemDataSource) {
        this.context = context;
        this.layoutResource = resource;
        this.itemDataSource = itemDataSource;
    }

    @Override
    public int getCount() {
        return itemDataSource.size();
    }

    @Override
    public JsonObject getItem(int position) {
        return itemDataSource.get(position).getAsJsonObject();
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).get("id").getAsLong();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        ItemRowHolder holder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResource, parent, false);
            holder = new ItemRowHolder(row);
            row.setTag(holder);
        } else {
            holder = (ItemRowHolder) row.getTag();
        }

        JsonObject memory = getItem(position);
        String body = memory.get("body").getAsString();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.itemBody.setText(Html.fromHtml(body, Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.itemBody.setText(Html.fromHtml(body));
        }

        return row;
    }
}
