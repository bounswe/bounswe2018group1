package bounswe2018group1.cmpe451;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import bounswe2018group1.cmpe451.fragments.FeedFragment;
import bounswe2018group1.cmpe451.fragments.MapFragment;
import bounswe2018group1.cmpe451.fragments.ProfileFragment;
import bounswe2018group1.cmpe451.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {
    // TODO Map tab may not be implemented and removed
    private Button tabFeed;
    private Button tabMap;
    private Button tabProfile;
    private Button tabSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabFeed = findViewById(R.id.tabFeed);
        tabMap = findViewById(R.id.tabMap);
        tabProfile = findViewById(R.id.tabProfile);
        tabSearch = findViewById(R.id.tabSearch);

        // Fragments
        //final WallFragment wallFragment = new WallFragment(); TODO implement
        final FeedFragment feedFragment = new FeedFragment();
        final MapFragment mapFragment = new MapFragment();
        final ProfileFragment profileFragment = new ProfileFragment();
        final SearchFragment searchFragment = new SearchFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainLayout, feedFragment, "Feed");
        fragmentTransaction.commit();

        //ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("Profile");
        //fragment.a();

        // Tabs
        tabFeed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransactionF = getSupportFragmentManager().beginTransaction();
                fragmentTransactionF.replace(R.id.mainLayout, feedFragment);
                fragmentTransactionF.addToBackStack(null);
                fragmentTransactionF.commit();

            }
        });

        tabMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransactionM = getSupportFragmentManager().beginTransaction();
                fragmentTransactionM.replace(R.id.mainLayout, mapFragment);
                fragmentTransactionM.addToBackStack(null);
                fragmentTransactionM.commit();

            }
        });

        tabProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransactionP = getSupportFragmentManager().beginTransaction();
                fragmentTransactionP.replace(R.id.mainLayout, profileFragment);
                fragmentTransactionP.addToBackStack(null);
                fragmentTransactionP.commit();

            }
        });

        tabSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransactionS = getSupportFragmentManager().beginTransaction();
                fragmentTransactionS.replace(R.id.mainLayout, searchFragment);
                fragmentTransactionS.addToBackStack(null);
                fragmentTransactionS.commit();

            }
        });

    }
}
