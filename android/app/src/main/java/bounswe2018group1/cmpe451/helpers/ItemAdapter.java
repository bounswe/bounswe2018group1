package bounswe2018group1.cmpe451.helpers;

import android.content.ContentResolver;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import bounswe2018group1.cmpe451.R;

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
            holder = new ItemRowHolder(row, context);
            row.setTag(holder);
        } else {
            holder = (ItemRowHolder) row.getTag();
        }
        holder.setAllGone();

        JsonObject memory = getItem(position);
        String htmlBody = null;
        Boolean isHtml = true;
        if(!memory.get("body").isJsonNull() && !memory.get("body").getAsString().isEmpty()) {
            htmlBody = memory.get("body").getAsString();
        } else if(!memory.get("url").isJsonNull()) {
            Uri itemUri = Uri.parse(memory.get("url").getAsString());
            String type = getMimeType(itemUri, context);
            if(type.startsWith("image")) {
                isHtml = false;
                holder.itemImage.setVisibility(View.VISIBLE);
                holder.itemImage.getViewTreeObserver().addOnGlobalLayoutListener(
                        new MyOnGlobalLayoutListener(itemUri, holder.itemImage));
            } else if(type.startsWith("video")) {
                isHtml = false;
                holder.itemVideo.setVisibility(View.VISIBLE);
                //holder.mediaController.setVisibility(View.GONE);
                holder.mediaController.setAnchorView(holder.itemVideo);
                holder.mediaController.setMediaPlayer(holder.itemVideo);
                //holder.itemVideo.setMediaController(holder.mediaController);
                holder.itemVideo.setVideoURI(itemUri);
                holder.itemVideo.requestFocus();
                holder.itemVideo.seekTo(1);
                holder.itemVideo.setOnCompletionListener(
                        new ItemVideoOnCompletionListener(holder.itemVideo));
                holder.itemVideo.setOnPreparedListener(
                        new ItemVideoOnPreparedListener(holder.itemVideo, holder.mediaController));
            } else {
                // TODO: HANDLE UNKNOWN FILE TYPE IN ITEM URL!!!
                htmlBody = "(unknown content type)";
            }
        } else {
            // TODO: HANDLE EMPTY MEMORY ITEM!!!
            htmlBody = "(no content)";
        }
        if(isHtml) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                holder.itemBody.setText(Html.fromHtml(htmlBody, Html.FROM_HTML_MODE_LEGACY));
            } else {
                holder.itemBody.setText(Html.fromHtml(htmlBody));
            }
            holder.itemBody.setVisibility(View.VISIBLE);
        }

        return row;
    }

    class ItemVideoOnTouchListener implements View.OnTouchListener {

        private MediaController mediaController;
        private int CLICK_ACTION_THRESHOLD = 200;

        ItemVideoOnTouchListener(MediaController mediaContoller) {
            this.mediaController = mediaContoller;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    // ON_SCROLL
                    mediaController.setVisibility(View.GONE);
                    return true;
                case MotionEvent.ACTION_UP:
                    if (event.getEventTime() - event.getDownTime() <= CLICK_ACTION_THRESHOLD) {
                        // ON_CLICK
                        if (mediaController.getVisibility() != View.VISIBLE) {
                            mediaController.setVisibility(View.VISIBLE);
                        }
                        if(!mediaController.isShowing()) {
                            mediaController.show(1500);
                        } else {
                            mediaController.hide();
                        }
                    }
                    return false;
                default:
                    return true;
            }
        }
    }

    class ItemVideoOnCompletionListener implements MediaPlayer.OnCompletionListener {

        private VideoView itemVideo;

        ItemVideoOnCompletionListener(VideoView itemVideo) {
            this.itemVideo = itemVideo;
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            itemVideo.start();
        }
    }

    class ItemVideoOnPreparedListener implements MediaPlayer.OnPreparedListener {

        private VideoView itemVideo;
        private MediaController mediaController;

        ItemVideoOnPreparedListener(VideoView itemVideo, MediaController mediaContoller) {
            this.itemVideo = itemVideo;
            this.mediaController = mediaContoller;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            itemVideo.setOnTouchListener(
                    new ItemVideoOnTouchListener(mediaController));
        }
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        private Uri itemUri;
        private ImageView imageView;

        MyOnGlobalLayoutListener(Uri itemUri, ImageView imageView) {
            this.itemUri = itemUri;
            this.imageView = imageView;
        }

        @Override
        public void onGlobalLayout() {
            Picasso.get()
                    .load(itemUri)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .fit()
                    .centerInside()
                    .into(imageView);
            imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    public static String getMimeType(@NotNull Uri uri, Context context) {
        String mimeType;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = StringUtility.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }
}
