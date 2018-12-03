package bounswe2018group1.cmpe451;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapsSearchActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions marker1;
    private MarkerOptions marker2;
    private boolean marker1On;
    private boolean marker2On;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        marker1 = new MarkerOptions();
        marker1.zIndex(1.0f);
        marker1.draggable(true);
        marker1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        marker2 = new MarkerOptions();
        marker2.zIndex(2.0f);
        marker2.draggable(true);
        marker2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (!marker1On) {
                    marker1On = true;
                    marker1.position(latLng);
                    mMap.addMarker(marker1);
                }
                else if (!marker2On) {
                    marker2On = true;
                    marker2.position(latLng);
                    mMap.addMarker(marker2);
                }
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (marker1On && marker2On) {
                    mMap.clear();
                    mMap.addMarker(marker1);
                    mMap.addMarker(marker2);
                    PolygonOptions rectangle = new PolygonOptions();
                    rectangle.add(new LatLng(marker1.getPosition().latitude, marker1.getPosition().longitude));
                    rectangle.add(new LatLng(marker1.getPosition().latitude, marker2.getPosition().longitude));
                    rectangle.add(new LatLng(marker2.getPosition().latitude, marker2.getPosition().longitude));
                    rectangle.add(new LatLng(marker2.getPosition().latitude, marker1.getPosition().longitude));
                    rectangle.clickable(true);
                    rectangle.visible(true);
                    rectangle.fillColor(0x40FF0000);
                    rectangle.strokeWidth(0.0f);
                    mMap.addPolygon(rectangle);
                }
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getZIndex() == 1.0) {
                    marker1On = false;
                }
                else {
                    marker2On = false;
                }
                mMap.clear();
                if (marker1On) {
                    mMap.addMarker(marker1);
                }
                else if (marker2On) {
                    mMap.addMarker(marker2);
                }
                return true;
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                if (marker.getZIndex() == 1.0f) {
                    marker1.position(marker.getPosition());
                }
                else {
                    marker2.position(marker.getPosition());
                }
                mMap.clear();
                mMap.addMarker(marker1);
                mMap.addMarker(marker2);
            }
        });

        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("PickedPoint1", marker1.getPosition());
                returnIntent.putExtra("PickedPoint2", marker2.getPosition());
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
