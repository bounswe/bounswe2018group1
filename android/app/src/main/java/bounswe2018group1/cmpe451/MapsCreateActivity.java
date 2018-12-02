package bounswe2018group1.cmpe451;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapsCreateActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String tagOfMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tagOfMapButton = bundle.getString("TagOfMap");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("PickedPoint", latLng);
                returnIntent.putExtra("TagOfMap", tagOfMapButton);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        // (Add a marker on TGNA) and move the camera
        LatLng home = new LatLng(39.9116, 32.8509);
        //mMap.addMarker(new MarkerOptions().position(home).title("Marker in TGNA"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
    }
}
