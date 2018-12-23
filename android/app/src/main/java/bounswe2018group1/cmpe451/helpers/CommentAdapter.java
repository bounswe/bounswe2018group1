package bounswe2018group1.cmpe451.helpers;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bounswe2018group1.cmpe451.R;

public class CommentAdapter extends BaseAdapter {

    protected Context context;
    private int layoutResource;
    private JsonArray commentDataSource;
    private ClientAPI clientAPI;

    public CommentAdapter(Context context, int resource, JsonArray commentDataSource) {
        this.context = context;
        this.layoutResource = resource;
        this.commentDataSource = commentDataSource;
        this.clientAPI = ClientAPI.getInstance(context);
    }

    @Override
    public int getCount() {
        return commentDataSource.size();
    }

    @Override
    public JsonObject getItem(int position) {
        return commentDataSource.get(position).getAsJsonObject();
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).get("id").getAsLong();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        CommentRowHolder holder;
        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResource, null);
            holder = new CommentRowHolder(row);
            row.setTag(holder);
        } else {
            holder = (CommentRowHolder) row.getTag();
        }

        JsonObject comment = getItem(position);
        String[] fullName = new String[]{
                comment.get("userFirstName").getAsString(),
                comment.get("userLastName").getAsString()};
        String postDate = comment.get("dateOfCreation").getAsString();
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
        String commentText = comment.get("text").getAsString();
        this.initProfilePicture(comment, holder.avatar);
        holder.authorName.setText(StringUtility.join(" ", fullName));
        holder.postDate.setText("Posted " + formattedTime);
        holder.commentText.setText(commentText);
        holder.hookCommentText();
        holder.id = comment.get("id").getAsInt();

        // TODO: CHECK IF THE CURRENT USER OWNS THIS COMMENT ????????????????????????????????????????????????
        holder.commentOptionsButton.setOnClickListener(
                new CommentOptionsButtonOnClickListener(holder, parent, row));
        clientAPI.getCurrentUser(
                new CommentOptionsButtonServerCallBack(holder, comment.get("userId").getAsInt()),
                context);
//        holder.commentEditButton.setOnClickListener(new CommentEditButtonOnClickListener(holder));
//        holder.commentEditButton.setVisibility(View.VISIBLE);
//        holder.commentEditSend.setOnClickListener(new CommentEditSendOnClickListener(holder));

        holder.btnSeeMore.setOnClickListener(
                new ButtonSeeMoreOnClickListener(holder)
        );

        return row;
    }

    private void initProfilePicture(JsonObject comment, ImageView avatar) {
        if(!comment.get("userProfilePicUrl").isJsonNull() &&
                !comment.get("userProfilePicUrl").getAsString().isEmpty()) {
            Uri itemUri = Uri.parse(comment.get("userProfilePicUrl").getAsString());
            Picasso.get()
                    .load(itemUri)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .fit()
                    .centerInside()
                    .transform(new CircleTransform())
                    .into(avatar);
        }
    }

    class ButtonSeeMoreOnClickListener implements View.OnClickListener {
        CommentRowHolder holder;

        public ButtonSeeMoreOnClickListener(CommentRowHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            holder.alternateButtonSeeMore();
        }
    }

    class CommentOptionsButtonServerCallBack implements ServerCallBack {

        CommentRowHolder holder;
        private int commentUserId;

        CommentOptionsButtonServerCallBack(CommentRowHolder holder, int commentUserId) {
            this.holder = holder;
            this.commentUserId = commentUserId;
        }

        @Override
        public void onSuccess(JSONObject result) {
            try {
                if(result.getInt("id") == commentUserId) {
                    holder.commentOptionsButton.setVisibility(View.VISIBLE);
                }
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError() {}
    }

    class CommentOptionsButtonOnClickListener implements View.OnClickListener {

        CommentRowHolder holder;
        ViewGroup parent;
        View row;

        CommentOptionsButtonOnClickListener(CommentRowHolder holder, ViewGroup parent, View row) {
            this.holder = holder;
            this.parent = parent;
            this.row = row;
        }

        @Override
        public void onClick(View v) {
            PopupMenu dropDownMenu = new PopupMenu(context, holder.commentOptionsButton);
            dropDownMenu.getMenuInflater().inflate(R.menu.comment_menu, dropDownMenu.getMenu());
            dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // In case there are more than 1 menu items in the future
                    switch (item.getItemId()) {
                        case R.id.delete_item:
                            clientAPI.deleteComment(holder.id,
                                    new DeleteCommentServerCallBack(), context);
                            break;
                    }
                    return true;
                }
            });
            dropDownMenu.show();
        }

        class DeleteCommentServerCallBack implements ServerCallBack {

            @Override
            public void onSuccess(JSONObject result) {
                if(parent.indexOfChild(row) != -1) {
                    parent.removeView(row);
                }
            }

            @Override
            public void onError() {
                Toast.makeText(context, "Failed to delete comment!", Toast.LENGTH_LONG).show();
            }
        }
    }

/*
    class CommentEditButtonOnClickListener implements View.OnClickListener {

        CommentRowHolder holder;

        CommentEditButtonOnClickListener(CommentRowHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            if(!holder.isEditing) {
                holder.commentEditText.setText(holder.commentText.getText(),
                        TextView.BufferType.EDITABLE);
                holder.commentText.setVisibility(View.GONE);
                holder.removeButtonSeeMore();
                holder.commentEditText.setVisibility(View.VISIBLE);
                holder.commentEditSend.setVisibility(View.VISIBLE);
            } else {
                holder.commentEditText.setVisibility(View.GONE);
                holder.commentEditSend.setVisibility(View.GONE);
                holder.commentText.setVisibility(View.VISIBLE);
                holder.initButtonSeeMore();
            }
            holder.isEditing = !holder.isEditing;
        }
    }

    class CommentEditSendOnClickListener implements View.OnClickListener {

        CommentRowHolder holder;

        CommentEditSendOnClickListener(CommentRowHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            if(holder.isEditing) {
                holder.commentText.setText(holder.commentEditText.getText().toString());
                holder.commentEditText.setVisibility(View.GONE);
                holder.commentEditSend.setVisibility(View.GONE);
                holder.commentText.setVisibility(View.VISIBLE);
                holder.isEditing = false;
                holder.initButtonSeeMore();
                // TODO: TAKE THE 'commentEditText' AND UPDATE THIS COMMENT
            }
        }
    }*/

}
