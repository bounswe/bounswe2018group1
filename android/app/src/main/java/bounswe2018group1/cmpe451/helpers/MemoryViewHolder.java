package bounswe2018group1.cmpe451.helpers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bounswe2018group1.cmpe451.R;

public class MemoryViewHolder {
    public ImageView avatar;
    public TextView authorName, postDate, memoryTitle, memoryDesc;

    public MemoryViewHolder(View v) {
        avatar = v.findViewById(R.id.avatar);
        authorName = v.findViewById(R.id.authorName);
        postDate = v.findViewById(R.id.postDate);
        memoryTitle = v.findViewById(R.id.memoryTitle);
        memoryDesc = v.findViewById(R.id.memoryDesc);
    }
}
