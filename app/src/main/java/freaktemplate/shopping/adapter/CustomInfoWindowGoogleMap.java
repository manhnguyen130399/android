package freaktemplate.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import freaktemplate.shopping.R;


public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {
    Context context;
    private LayoutInflater inflater;

    public CustomInfoWindowGoogleMap(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.google_marker_snippest, null);
    }
}
