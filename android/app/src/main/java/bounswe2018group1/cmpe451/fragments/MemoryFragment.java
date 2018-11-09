package bounswe2018group1.cmpe451.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import bounswe2018group1.cmpe451.MemoryCreateActivity;
import bounswe2018group1.cmpe451.R;


public class MemoryFragment extends DialogFragment {

    private EditText mEditText;

    public MemoryFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static MemoryFragment newInstance(String title) {
        MemoryFragment frag = new MemoryFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);



        return rootView;
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = view.findViewById(R.id.dateStart);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button b = view.findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ds = view.findViewById(R.id.dateStart);
                EditText df = view.findViewById(R.id.dateFinish);
                EditText st = view.findViewById(R.id.story);
                MemoryCreateActivity mca = (MemoryCreateActivity) getActivity();
                //TODO
                mca.addStory(new MemoryCreateActivity.Story(st.getText().toString(), ds.getText().toString(), df.getText().toString()));
                MemoryFragment.this.dismiss();
            }
        });
    }
}