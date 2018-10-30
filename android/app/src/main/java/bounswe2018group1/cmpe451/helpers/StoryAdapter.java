package bounswe2018group1.cmpe451.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class StoryAdapter extends BaseAdapter {

    protected Context context;
    private int layoutResource;
    private JsonArray storyDataSource;

    public StoryAdapter(Context context, int resource, JsonArray storyDataSource) {
        this.context = context;
        this.layoutResource = resource;
        this.storyDataSource = storyDataSource;
    }

    @Override
    public int getCount() {
        return storyDataSource.size();
    }

    @Override
    public JsonObject getItem(int position) {
        return storyDataSource.get(position).getAsJsonObject();
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).get("id").getAsLong();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        StoryRowHolder holder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResource, parent, false);
            holder = new StoryRowHolder(row);
            row.setTag(holder);
        } else {
            holder = (StoryRowHolder) row.getTag();
        }

        JsonObject memory = getItem(position);
        String storyDate = memory.get("storyDate").getAsString();
        String country = memory.get("country").getAsString();
        String city = memory.get("city").getAsString();
        String county = memory.get("county").getAsString();
        String district = memory.get("district").getAsString();
        String storyTitle = memory.get("headline").getAsString();
        String storyDesc = memory.get("description").getAsString();
        holder.storyDate.setText(storyDate);
        holder.storyLocation.setText(StoryAdapter.join(", ", new String[]{
                district, county, city, country
        }));
        holder.storyTitle.setText(storyTitle);
        holder.storyDesc.setText(storyDesc);

        return row;
    }

    public static String join(String delimiter, String[] argStrings) {
        if (argStrings == null) {
            return "";
        }
        String ret = "";
        int i;
        for(i = 0; i < argStrings.length && (argStrings[i] == null || argStrings[i].isEmpty()); ++i);
        if (i < argStrings.length) {
            ret = argStrings[i];
        } // if
        for (i = i + 1; i < argStrings.length; i++) {
            ret += (argStrings[i] == null)
                    ? ""
                    : (argStrings[i].isEmpty()
                    ? ""
                    : (delimiter + argStrings[i]));
        } // for
        return ret;
    }
}
