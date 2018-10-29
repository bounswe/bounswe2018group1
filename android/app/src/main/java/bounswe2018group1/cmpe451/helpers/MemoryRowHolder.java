package bounswe2018group1.cmpe451.helpers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bounswe2018group1.cmpe451.R;

public class MemoryRowHolder {
    public ImageView avatar;
    public TextView authorName, postDate, memoryTitle, memoryDesc, btnSeeMore;
    public boolean expanded;

    public MemoryRowHolder(View v) {
        avatar = v.findViewById(R.id.avatar);
        authorName = v.findViewById(R.id.authorName);
        postDate = v.findViewById(R.id.postDate);
        memoryTitle = v.findViewById(R.id.memoryTitle);
        memoryDesc = v.findViewById(R.id.memoryDesc);
        btnSeeMore = v.findViewById(R.id.btnSeeMore);
        expanded = true;
    }
}
