package bounswe2018group1.cmpe451;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // TODO Map tab may not be implemented and removed
    private Button tabWall;
    private Button tabMemory;
    private Button tabProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabWall = findViewById(R.id.tabWall);
        tabMemory = findViewById(R.id.tabMemory);
        tabProfile = findViewById(R.id.tabProfile);

        // Fragments
        //final WallFragment wallFragment = new WallFragment(); TODO implement
        final MemoryFragment memoryFragment = new MemoryFragment();
        final ProfileFragment profileFragment = new ProfileFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainLayout, memoryFragment, "Memory");
        fragmentTransaction.add(R.id.mainLayout, profileFragment, "Profile");
        fragmentTransaction.commit();

        /*fragmentTransaction = fragmentManager.beginTransaction();
        ProfileFragment profileFragment2 = new ProfileFragment();
        fragmentTransaction.replace(R.id.mainLayout, profileFragment2, "a");
        fragmentTransaction.addToBackStack("c");
        fragmentTransaction.commit();*/
        //profileFragment.a();

        // Tabs
        tabWall.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("Profile");

                fragment.a();

            }
        });

        tabMemory.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransactionM = getSupportFragmentManager().beginTransaction();
                fragmentTransactionM.replace(R.id.mainLayout, memoryFragment);
                fragmentTransactionM.addToBackStack(null);
                fragmentTransactionM.commit();

            }
        });

        tabProfile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransactionP = getSupportFragmentManager().beginTransaction();
                fragmentTransactionP.replace(R.id.mainLayout, profileFragment);
                fragmentTransactionP.addToBackStack(null);
                fragmentTransactionP.commit();

            }
        });







    }
}
