package bounswe2018group1.cmpe451.helpers;

import android.view.View;
import android.widget.TextView;

import bounswe2018group1.cmpe451.R;

public class StoryRowHolder {
    public TextView storyAuthorName, storyDate, storyLocation, storyTitle, storyDesc;

    public StoryRowHolder(View v) {
        storyAuthorName = v.findViewById(R.id.storyAuthorName);
        storyDate = v.findViewById(R.id.storyDate);
        storyLocation = v.findViewById(R.id.storyLocation);
        storyTitle = v.findViewById(R.id.storyTitle);
        storyDesc = v.findViewById(R.id.storyDesc);
    }
}
