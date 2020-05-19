package freaktemplate.shopping.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import freaktemplate.shopping.R;
import freaktemplate.shopping.adapter.CustomInfoWindowGoogleMap;
import freaktemplate.shopping.utils.GPSTracker;
import freaktemplate.shopping.utils.SPmanager;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class LocationActivity extends AppCompatActivity implements GoogleMap.OnMarkerDragListener, OnMapReadyCallback {
    public static final String[] permission_location = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    public static final String[] permission_location1 = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final int PERMISSION_REQUEST_CODE = 1001, PERMISSION_REQUEST_CODE1 = 10011;
    private static final String TAG = "LocationActivity";
    private double latitudecur;
    private double longitudecur;
    private GoogleMap googleMap;
    private MarkerOptions marker;
    private ProgressDialog progressDialog;
    private String address;
    private EditText ed_location;
    private String city;
    private String pincode;
    private boolean ispress;
    private String jsondata;
    private String total;
    private String country;
    private String original;

    public static void requestPermission(Activity context) {
        ActivityCompat.requestPermissions(context, permission_location, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(context, permission_location1, PERMISSION_REQUEST_CODE1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        marker = new MarkerOptions();
        getintents();
        if (checkPermission()) {
            gettingLocation();
        } else {
            requestPermission(this);
        }
        initlise();
    }

    private void getintents() {
        Intent iv = getIntent();
        ispress = iv.getBooleanExtra("ispress", false);
        jsondata = iv.getStringExtra("json");
        total = iv.getStringExtra("total");
        original = iv.getStringExtra("original");

    }

    private void initlise() {
        ed_location = findViewById(R.id.ed_location);
        Button btn_confirm = findViewById(R.id.btn_confirm);
        MapsInitializer.initialize(getApplicationContext());
        initilizeMap();
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                ispress = true;
                if (!ed_location.getText().toString().trim().isEmpty()) {
                    if (original.equals("yes")) {
                        SPmanager.saveValue(LocationActivity.this, "address", ed_location.getText().toString());
                        SPmanager.saveValue(LocationActivity.this, "city", city);
                        SPmanager.saveValue(LocationActivity.this, "country", country);
                        SPmanager.saveValue(LocationActivity.this, "pincode", pincode);
                    } else {
                        SPmanager.saveValue(LocationActivity.this, "billaddress", ed_location.getText().toString());
                        SPmanager.saveValue(LocationActivity.this, "billcity", city);
                        SPmanager.saveValue(LocationActivity.this, "country", country);
                        SPmanager.saveValue(LocationActivity.this, "billpincode", pincode);
                    }
                   /* if (SPmanager.getBilling(LocationActivity.this)) {
                        SPmanager.saveValue(LocationActivity.this, "address", ed_location.getText().toString());
                        SPmanager.saveValue(LocationActivity.this, "city", city);
                        SPmanager.saveValue(LocationActivity.this, "country", country);
                        SPmanager.saveValue(LocationActivity.this, "pincode", pincode);
                    } else {
                        SPmanager.saveValue(LocationActivity.this, "billaddress", ed_location.getText().toString());
                        SPmanager.saveValue(LocationActivity.this, "billcity", city);
                        SPmanager.saveValue(LocationActivity.this, "country", country);
                        SPmanager.saveValue(LocationActivity.this, "billpincode", pincode);
                    }*/
                    SPmanager.saveValue(LocationActivity.this, "location", ed_location.getText().toString());
                    Intent intent = new Intent(LocationActivity.this, PaymentActivity.class);
                    intent.putExtra("ispress", ispress);
                    intent.putExtra("json", jsondata);
                    intent.putExtra("total", total);
                    intent.putExtra("original", original);
                    intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    ed_location.setText(getString(R.string.locationfirst));
                }
            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void gettingLocation() {
        GPSTracker gps = new GPSTracker(LocationActivity.this);
        if (gps.canGetLocation()) {
            try {
                latitudecur = gps.getLatitude();
                longitudecur = gps.getLongitude();
                SPmanager.saveValue(LocationActivity.this, "latitude", "" + latitudecur);
                SPmanager.saveValue(LocationActivity.this, "longitude", "" + longitudecur);
            } catch (NumberFormatException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        } else {
            gps.showSettingsAlert();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        try {
            googleMap.setInfoWindowAdapter(new CustomInfoWindowGoogleMap(this));
            this.googleMap = googleMap;
            googleMap.setOnMarkerDragListener(this);
            initializeUiSettings();
            initializeMapLocationSettings();
            initializeMapTraffic();
            initializeMapType();
            initializeMapViewSettings();
            googleMap.setMyLocationEnabled(true);
            new getLocationFromLatLong().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            LatLng position = new LatLng(latitudecur, longitudecur);
            googleMap.addMarker(marker.position(position).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_map))).showInfoWindow();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17.0f));
        } catch (NullPointerException | NumberFormatException e) {
            // TODO: handle exception
        }

    }

    private void initializeMapTraffic() {
        googleMap.setTrafficEnabled(true);
    }

    private void initializeMapType() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void initializeMapViewSettings() {
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(false);
    }

    private void initilizeMap() {
        SupportMapFragment supportMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment));
        supportMapFragment.getMapAsync(this);
        (findViewById(R.id.mapFragment)).getViewTreeObserver().addOnGlobalLayoutListener(new android.view.ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                (findViewById(R.id.mapFragment)).getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void initializeUiSettings() {
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        LatLng field = marker.getPosition();
        Log.e(TAG, "onMarkerDragStart: LatitudenLongitude" + field.latitude + " " + field.longitude);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        marker.hideInfoWindow();
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng field = marker.getPosition();
        Log.e(TAG, "onMarkerDragEnd: LatitudenLongitude" + field.latitude + " " + field.longitude);
        latitudecur = field.latitude;
        longitudecur = field.longitude;
        SPmanager.saveValue(LocationActivity.this, "latitude", "" + latitudecur);
        SPmanager.saveValue(LocationActivity.this, "longitude", "" + longitudecur);
        new getLocationFromLatLong().execute();
        marker.showInfoWindow();
    }

    private List<Address> getAddress() {
        if (latitudecur != 0 && longitudecur != 0) {
            try {
                Geocoder geocoder = new Geocoder(LocationActivity.this);
                List<Address> addresses = geocoder.getFromLocation(latitudecur, longitudecur, 1);
                String address = addresses.get(0).getAddressLine(0);
                city = addresses.get(0).getLocality();
                country = addresses.get(0).getCountryName();
                String country = addresses.get(0).getAddressLine(2);
                pincode = addresses.get(0).getPostalCode();
                Log.e("TAG", "address = " + address + ", city = " + city + ", country = " + country);
                return addresses;

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "getAddress:gotException " + e.getMessage());
            }
        } else {
            Log.e(TAG, "getAddress: Lat Long is 0");
        }
        return null;
    }

    private void initializeMapLocationSettings() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            gettingLocation();
            onMapReady(googleMap);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class getLocationFromLatLong extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LocationActivity.this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (getAddress() != null)
                address = getAddress().get(0).getAddressLine(0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LatLng position = new LatLng(latitudecur, longitudecur);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17.0f));
            ed_location.setText(address);
            progressDialog.dismiss();
        }
    }


}
