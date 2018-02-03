package event.apps.profil;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import event.apps.ApiVolley;
import event.apps.Home.CustomListAdapter;
import event.apps.tiket.adapter_tiket;
import event.apps.Home.home_detailacara;
import event.apps.R;
import event.apps.SessionManagement;
import event.apps.favorit.favorit;
import event.apps.tiket.tiket_detailtiket;
import event.apps.webserviceURL;

/**
 * Created by rejak on 2/27/2017.
 */

public class fra_list extends Fragment {

    ListView list;
    private ProgressDialog pDialog;
    private List<profil> listprofil = new ArrayList<profil>();
    SessionManagement session;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       final View v= inflater.inflate(R.layout.fragment_profile_list,container, false);

        getActivity().setTitle("Tiket");

        final ImageView prof =(ImageView) v.findViewById(R.id.prof_kosong);
        prof.setImageResource(R.drawable.not_found);
        prof.setVisibility(v.GONE);
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
        //List
        final adapter_profil adapter=new adapter_profil(getActivity(),listprofil);
        list=(ListView)v.findViewById(R.id.list_profile);
        list.setAdapter(adapter);

        JSONObject json = new JSONObject();
        ApiVolley req = new ApiVolley(getContext(), json, "GET", webserviceURL.userevent+Usrid, "", "", 0, new ApiVolley.VolleyCallback() {
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
                        profil a= new profil();
                        a.setId_usr(Usrid);
                        a.setid(ar.getInt("id_acara"));
                        a.setjudul(ar.getString("judul_acara"));
                        a.setsubjudul(ar.getString("tag_line"));
                        a.setinstansi(ar.getString("instansi"));
                        a.setkuota(ar.getInt("kuota"));
                        a.setdaftar(ar.getInt("kuota"));
                        a.setharga(ar.getInt("harga"));
                        a.setimg(ar.getString("gmb_poster"));
                        a.setdesk(ar.getString("desk_acara"));
                        listprofil.add(a);
                    }
                    //Log.d("cek isi",ar.getString("judul_acara"));

                } catch (Exception e) {

                    e.printStackTrace();
                    prof.setVisibility(v.VISIBLE);
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