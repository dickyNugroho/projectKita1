package event.apps.Home;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import event.apps.webserviceURL;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import event.apps.ApiVolley;
import event.apps.R;

/**
 * Created by rejak on 3/6/2017.
 */

public class fra_home_map extends Fragment  {
    MapView mMapView;
    private GoogleMap googleMap;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home_map, container, false);
        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                JSONObject body =new JSONObject();
                ApiVolley req =new ApiVolley(getActivity(),body,"GET",webserviceURL.listevent,"","",0, new ApiVolley.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try{
                            JSONObject responseAPI = new JSONObject(result);
                            JSONArray arr = responseAPI.getJSONArray("response");
                            for(int i=0;i<arr.length();i++){
                                JSONObject ar = arr.getJSONObject(i);
                                double lat= Double.parseDouble(ar.getString("latitude"));
                                double longl =Double.parseDouble(ar.getString("longitude"));
                                int kat=Integer.parseInt(ar.getString("id_kategori"));
                                if((lat!=0)&&(longl!=0)){
                                    if(kat==1){
                                        LatLng semarang = new LatLng(lat, longl);
                                        googleMap.addMarker(new MarkerOptions().position(semarang).title(ar.getString("judul_acara")).snippet(ar.getString("instansi")).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                                    }
                                    if(kat==2){
                                        LatLng semarang = new LatLng(lat, longl);
                                        googleMap.addMarker(new MarkerOptions().position(semarang).title(ar.getString("judul_acara")).snippet(ar.getString("instansi")).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_2)));
                                    }
                                    if(kat==3){
                                        LatLng semarang = new LatLng(lat, longl);
                                        googleMap.addMarker(new MarkerOptions().position(semarang).title(ar.getString("judul_acara")).snippet(ar.getString("instansi")).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                                    }
                                    if(kat==4){
                                        LatLng semarang = new LatLng(lat, longl);
                                        googleMap.addMarker(new MarkerOptions().position(semarang).title(ar.getString("judul_acara")).snippet(ar.getString("instansi")).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                                    }
                                    if(kat==5){
                                        LatLng semarang = new LatLng(lat, longl);
                                        googleMap.addMarker(new MarkerOptions().position(semarang).title(ar.getString("judul_acara")).snippet(ar.getString("instansi")).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                                    }
                                }
                            }

                        }
                        catch (Exception e){e.printStackTrace();}

                    }

                    @Override
                    public void onError(String result) {

                    }
                });
                // For dropping a marker at a point on the Map
                LatLng semarang = new LatLng( -6.966667, 110.416664);
                googleMap.addMarker(new MarkerOptions().position(semarang).title("Marker Title").snippet("Marker Description").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(semarang).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
        return v;
    }

}
