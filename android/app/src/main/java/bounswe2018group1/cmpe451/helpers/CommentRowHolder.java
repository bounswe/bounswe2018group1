package bounswe2018group1.cmpe451.helpers;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import bounswe2018group1.cmpe451.R;

public class CommentRowHolder {
    public ImageView avatar;
    public ImageButton commentOptionsButton, commentEditButton, commentEditSend;
    public TextView authorName, postDate, commentText, btnSeeMore;
    public EditText commentEditText;
    public boolean expanded, isEditing;
    public int id;

    public CommentRowHolder(View v) {
        avatar = v.findViewById(R.id.avatar);
        commentOptionsButton = v.findViewById(R.id.commentOptionsButton);
        authorName = v.findViewById(R.id.authorName);
        postDate = v.findViewById(R.id.postDate);
        commentText = v.findViewById(R.id.commentText);
        btnSeeMore = v.findViewById(R.id.btnSeeMore);
        commentEditText = v.findViewById(R.id.commentEditText);
        expanded = true;
        isEditing = false;
    }

    public void hookCommentText() {
        this.commentText.getViewTreeObserver().addOnGlobalLayoutListener(
                new CommentTextOnGlobalLayoutListener(this)
        );
    }

    public void initButtonSeeMore() {
        if (this.commentText.getLineCount() > 3) {
            this.expanded = false;
            this.btnSeeMore.setVisibility(View.VISIBLE);
            this.commentText.setMaxLines(3);
            this.btnSeeMore.setText("View more");
        }
    }

    public void removeButtonSeeMore() {
        this.expanded = true;
        this.btnSeeMore.setVisibility(View.GONE);
        this.commentText.setMaxLines(Integer.MAX_VALUE);
    }

    public void alternateButtonSeeMore() {
        if (!this.expanded) {
            this.commentText.setMaxLines(Integer.MAX_VALUE);
            this.btnSeeMore.setText("View less");
        } else {
            this.commentText.setMaxLines(3);
            this.btnSeeMore.setText("View more");
        }
        this.expanded = !this.expanded;
    }

    class CommentTextOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        CommentRowHolder holder;

        CommentTextOnGlobalLayoutListener(CommentRowHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onGlobalLayout() {
            holder.initButtonSeeMore();
            holder.commentText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }
}
