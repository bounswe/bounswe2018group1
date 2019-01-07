package bounswe2018group1.cmpe451.helpers;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import bounswe2018group1.cmpe451.R;

public class ItemRowHolder {
    public TextView itemBody;
    public ImageView itemImage;
    public VideoView itemVideo;
    public MediaController mediaController;

    public ItemRowHolder(View v, Context context) {
        itemBody = v.findViewById(R.id.itemBody);
        itemImage = v.findViewById(R.id.itemImage);
        itemVideo = v.findViewById(R.id.itemVideo);
        mediaController = new MediaController(context);
    }

    public void setAllGone() {
        itemBody.setVisibility(View.GONE);
        itemImage.setVisibility(View.GONE);
        itemVideo.setVisibility(View.GONE);
    }
}
