package bounswe2018group1.cmpe451.helpers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bounswe2018group1.cmpe451.R;

public class CommentRowHolder {
    public ImageView avatar;
    public TextView authorName, postDate, commentText, btnSeeMore;
    public boolean expanded;

    public CommentRowHolder(View v) {
        avatar = v.findViewById(R.id.avatar);
        authorName = v.findViewById(R.id.authorName);
        postDate = v.findViewById(R.id.postDate);
        commentText = v.findViewById(R.id.commentText);
        btnSeeMore = v.findViewById(R.id.btnSeeMore);
        expanded = true;
    }
}
