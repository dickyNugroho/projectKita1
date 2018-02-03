package event.apps.favorit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import event.apps.ApiVolley;
import event.apps.Home.acara;
import event.apps.Home.home_detailacara;
import event.apps.R;
import event.apps.SessionManagement;
import event.apps.webserviceURL;

import static event.apps.R.id.toolbar;

/**
 * Created by rejak on 2/27/2017.
 */

public class fra_favorit extends Fragment {
    GridView grid;
    TextView title;
    private ProgressDialog pDialog;
    private List<favorit> favlist = new ArrayList<favorit>();
    SessionManagement session;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final View v= inflater.inflate(R.layout.fragment_favorit,container, false);
        title = (TextView) v.findViewById(R.id.toolbar_title);
        final ImageView fav=(ImageView) v.findViewById(R.id.fav_kosong);
        fav.setImageResource(R.drawable.not_favorite);
        fav.setVisibility(v.GONE);
        title.setText("Daftar Event Tersimpan");
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

        final grid_adapter adapter = new grid_adapter(v.getContext(), favlist);
        grid=(GridView)v.findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                favorit a = favlist.get(position);
                String jud =a.getjudul();
                String sub =a.getsubjudul();
                String ins =a.getinstansi();
                String desk=a.getdesk();
                int har =a.getharga();
                int id_acr =a.getid();
                String im = webserviceURL.P_image+a.getimg();
                Intent in = new Intent(getActivity().getApplicationContext(), fav_detail_acara.class);
                in.putExtra("id_user",Usrid);
                in.putExtra("id",id_acr);
                in.putExtra("judul", jud);
                in.putExtra("sub", sub);
                in.putExtra("ins", ins);
                in.putExtra("har", har);
                in.putExtra("img", im);
                in.putExtra("desk",desk);
                startActivity(in);

            }
        });



        JSONObject json = new JSONObject();
        try{json.put("id_user",Usrid);}
        catch (Exception e){e.printStackTrace();}
        ApiVolley req = new ApiVolley(getContext(), json, "POST", webserviceURL.listfav, "", "", 0, new ApiVolley.VolleyCallback() {
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
                        favorit a= new favorit();
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
                        favlist.add(a);
                    }
                    //Log.d("cek isi",ar.getString("judul_acara"));

                } catch (Exception e) {

                    e.printStackTrace();
                    fav.setVisibility(v.VISIBLE);
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
