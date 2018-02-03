package event.apps.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import event.apps.ApiVolley;
import event.apps.Login.Login;
import event.apps.MainActivity;
import event.apps.R;
import event.apps.SessionManagement;
import event.apps.webserviceURL;

import static event.apps.webserviceURL.*;

/**
 * Created by rejak on 3/6/2017.
 */

public class fra_home_event extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    SearchView searchView;
    private ProgressDialog pDialog;
    ListView list;
    SessionManagement session;
    String uid;
    ImageView not_found;
    private List<acara> acaralist = new ArrayList<acara>();
    CustomListAdapter adapter;
    int i;
    //isi list acara

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final View v= inflater.inflate(R.layout.fragment_home_event,container, false);
        session = new SessionManagement(getActivity());
        //Toast.makeText(getActivity(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
         uid= user.get(SessionManagement.KEY_NAME);
        //Toast.makeText(getActivity(), "ID " + uid, Toast.LENGTH_LONG).show();
        i=0;
        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        not_found =(ImageView) v.findViewById(R.id.not_found);
        not_found.setImageResource(R.drawable.not_found);
        not_found.setVisibility(v.GONE);
        adapter=new CustomListAdapter(getActivity(), acaralist);
        list=(ListView)v.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                acara a = acaralist.get(position);
                String jud =a.getjudul();
                String sub =a.getsubjudul();
                String ins =a.getinstansi();
                String desk=a.getdesk();
                int har =a.getharga();
                int id =a.getid();
                int kuota=a.getkuota();
                int daftar=a.getdaftar();

                String im =webserviceURL.img+a.getimg();
                Intent in = new Intent(getActivity().getApplicationContext(), home_detailacara.class);
                in.putExtra("id_user",uid);
                in.putExtra("id",id);
                in.putExtra("judul", jud);
                in.putExtra("sub", sub);
                in.putExtra("ins", ins);
                in.putExtra("har", har);
                in.putExtra("img", im);
                in.putExtra("desk",desk);
                in.putExtra("kuota",kuota);
                in.putExtra("daftar",daftar);
                startActivity(in);
                //Toast.makeText(getActivity().getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });


       //Search
        searchView=(SearchView) v.findViewById(R.id.searchView);
        searchView.setQueryHint("Cari Acara");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                acaralist.clear();
                if(query!=""){
                JSONObject json = new JSONObject();
                try{
                    json.put("cari",query);
                }
                catch (Exception e){e.printStackTrace();}
                ApiVolley req = new ApiVolley(getContext(), json, "POST", webserviceURL.caridata, "", "", 0, new ApiVolley.VolleyCallback() {
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
                                acara a= new acara();
                                a.setId_usr(uid);
                                a.setid(ar.getInt("id_acara"));
                                a.setjudul(ar.getString("judul_acara"));
                                a.setsubjudul(ar.getString("tag_line"));
                                a.setinstansi(ar.getString("instansi"));
                                a.setkuota(ar.getInt("kuota"));
                                a.setdaftar(ar.getInt("kuota"));
                                a.setharga(ar.getInt("harga"));
                                a.setimg(ar.getString("gmb_poster"));
                                a.setdesk(ar.getString("desk_acara"));
                                acaralist.add(a);
                            }
                            //Log.d("cek isi",ar.getString("judul_acara"));

                        } catch (Exception e) {

                            e.printStackTrace();
//                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                            if(query==""){
                                list();
                            }else{
                            not_found.setVisibility(v.VISIBLE);
                            }
                                //Toast.makeText(getActivity(), "Terjadi kesalahan saat memuat data", Toast.LENGTH_LONG).show();}
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String result) {

                    }
                });}else{
                    list();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                acaralist.clear();
                if(newText!=""){
                    JSONObject json = new JSONObject();
                    try{
                        json.put("cari",newText);
                    }
                    catch (Exception e){e.printStackTrace();}
                    ApiVolley req = new ApiVolley(getContext(), json, "POST", webserviceURL.caridata, "", "", 0, new ApiVolley.VolleyCallback() {
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
                                    acara a= new acara();
                                    a.setId_usr(uid);
                                    a.setid(ar.getInt("id_acara"));
                                    a.setjudul(ar.getString("judul_acara"));
                                    a.setsubjudul(ar.getString("tag_line"));
                                    a.setinstansi(ar.getString("instansi"));
                                    a.setkuota(ar.getInt("kuota"));
                                    a.setdaftar(ar.getInt("kuota"));
                                    a.setharga(ar.getInt("harga"));
                                    a.setimg(ar.getString("gmb_poster"));
                                    a.setdesk(ar.getString("desk_acara"));
                                    acaralist.add(a);
                                }
                                //Log.d("cek isi",ar.getString("judul_acara"));

                            } catch (Exception e) {
                                e.printStackTrace();
//                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                                    list();
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(String result) {

                        }
                    });}
                else{
                    list();
                }

                //Toast.makeText(getActivity().getBaseContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });

        list();

        return v;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void list(){
        acaralist.clear();
        JSONObject json = new JSONObject();
        ApiVolley req = new ApiVolley(getContext(), json, "GET", webserviceURL.listevent, "", "", 0, new ApiVolley.VolleyCallback() {
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
                        acara a= new acara();
                        a.setId_usr(uid);
                        a.setid(ar.getInt("id_acara"));
                        a.setjudul(ar.getString("judul_acara"));
                        a.setsubjudul(ar.getString("tag_line"));
                        a.setinstansi(ar.getString("instansi"));
                        a.setkuota(ar.getInt("kuota"));
                        a.setdaftar(ar.getInt("kuota"));
                        a.setharga(ar.getInt("harga"));
                        a.setimg(ar.getString("gmb_poster"));
                        a.setdesk(ar.getString("desk_acara"));
                        acaralist.add(a);
                    }
                    //Log.d("cek isi",ar.getString("judul_acara"));

                } catch (Exception e) {

                    e.printStackTrace();
//                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getActivity(), "Terjadi kesalahan saat memuat data", Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String result) {

            }
        });

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


}

