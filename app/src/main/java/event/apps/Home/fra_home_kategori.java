package event.apps.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import event.apps.ApiVolley;
import event.apps.R;
import event.apps.SessionManagement;
import event.apps.favorit.fav_detail_acara;
import event.apps.favorit.favorit;
import event.apps.favorit.grid_adapter;
import event.apps.webserviceURL;

/**
 * Created by rejak on 3/6/2017.
 */

public class fra_home_kategori extends Fragment {
    GridView grid;
    TextView title;
    private ProgressDialog pDialog;
    private List<kategori> listkat = new ArrayList<kategori>();
    SessionManagement session;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_home_kategori,container, false);

        //title.setText("Daftar Event Tersimpan");
        //session
        session = new SessionManagement(getActivity());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        final String Usrid=user.get(SessionManagement.KEY_NAME);
        //Toast.makeText(getActivity(),Usrid,Toast.LENGTH_SHORT).show();
        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        final kategori_adapter adapter = new kategori_adapter(v.getContext(), listkat);
        grid=(GridView)v.findViewById(R.id.grid);
        grid.setAdapter(adapter);
        /*grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                kategori a = listkat.get(position);
                //String jud =a.getnama();
                //String icon =a.geticon();
                //String color =a.getcolor();
                //int jum=a.getjumlah();
                int id_k=a.getid();
                Intent in = new Intent(getActivity().getApplicationContext(), kategori_list.class);
                //in.putExtra("id_user",Usrid);
                in.putExtra("id",id_k);
                //in.putExtra("nama",jud);
                //in.putExtra("icon",icon);
                //in.putExtra("color",color);
               // in.putExtra("jumlah",jum);
                startActivity(in);

            }
        });*/

        JSONObject json = new JSONObject();
        ApiVolley req = new ApiVolley(getContext(), json, "GET", webserviceURL.kategori, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("cekk",result.toString());
                hidePDialog();
                // Important Note : need to use try catch when parsing JSONObject, no need when parsing string

                try {
                    JSONObject responseAPI = new JSONObject(result);
                    JSONArray arr = responseAPI.getJSONArray("response");
                    String status = responseAPI.getJSONObject("metadata").getString("status");
                    responseAPI = null;
                    for(int i=0;i<arr.length();i++){
                        JSONObject ar = arr.getJSONObject(i);
                        kategori a= new kategori();
                        a.setid(ar.getInt("id_kat"));
                        a.setnama(ar.getString("nm_kat"));
                        a.seticon(ar.getString("icon"));
                        a.setcolor(ar.getString("color"));
                        a.setjumlah(ar.getInt("jumlah"));
                        listkat.add(a);
                    }
                    //Log.d("cek isi",ar.getString("judul_acara"));

                } catch (Exception e) {

                    e.printStackTrace();
//                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String result) {

            }
        });

        return v;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }



    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


}
