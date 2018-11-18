package bounswe2018group1.cmpe451.helpers;

import android.view.View;
import android.widget.TextView;

import bounswe2018group1.cmpe451.R;

public class ItemRowHolder {
    public TextView itemBody;

    public ItemRowHolder(View v) {
        itemBody = v.findViewById(R.id.itemBody);
    }
}
