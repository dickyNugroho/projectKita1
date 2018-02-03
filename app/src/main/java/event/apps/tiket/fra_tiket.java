package event.apps.tiket;

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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import event.apps.ApiVolley;
import event.apps.Home.CustomListAdapter;
import event.apps.Home.acara;
import event.apps.Home.home_detailacara;
import event.apps.R;
import event.apps.SessionManagement;
import event.apps.webserviceURL;

/**
 * Created by rejak on 2/27/2017.
 */

public class fra_tiket extends Fragment {

    ListView list;
    private List<tiket> listtiket = new ArrayList<tiket>();
    private ProgressDialog pDialog;
    SessionManagement session;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View v= inflater.inflate(R.layout.fragment_tiket,container, false);

        getActivity().setTitle("Tiket");
        final ImageView tik=(ImageView) v.findViewById(R.id.tiket_kosong);
        tik.setImageResource(R.drawable.not_buy_event);
        tik.setVisibility(v.GONE);
        //session
        session = new SessionManagement(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        final String id_user=user.get(SessionManagement.KEY_NAME);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        //List
        final adapter_tiket adapter=new adapter_tiket(getActivity(),listtiket);
        list=(ListView)v.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                tiket a = listtiket.get(position);
                int verif = a.getverif();
                //Toast.makeText(getActivity(),verif,Toast.LENGTH_SHORT);
                if(verif==1){
                String jud =a.getjudul();
                String sub =a.getsubjudul();
                String ins =a.getinstansi();
                String desk=a.getdesk();
                int har =a.getharga();
                int id =a.getid();
                String qr=a.getqrcode();
                String im = webserviceURL.P_image+a.getimg();
                String tang=a.gettgl();
                    String nama=a.getnama();
                Intent in = new Intent(getActivity().getApplicationContext(), tiket_detailtiket.class);
                in.putExtra("id_user",id_user);
                in.putExtra("id",id);
                in.putExtra("judul", jud);
                in.putExtra("sub", sub);
                in.putExtra("ins", ins);
                in.putExtra("har", har);
                in.putExtra("img", im);
                in.putExtra("desk",desk);
                in.putExtra("tgl",tang);
                in.putExtra("qr",qr);
                    in.putExtra("nama",nama);
                startActivity(in);
                }else{
                    Toast.makeText(getActivity(),"Anda Belum Diverikasi",Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(getActivity().getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });

        JSONObject json = new JSONObject();
        try{json.put("id_user",id_user);}
        catch (Exception e){e.printStackTrace();}
        ApiVolley req = new ApiVolley(getContext(), json, "POST", webserviceURL.listtiket, "", "", 0, new ApiVolley.VolleyCallback() {
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
                        tiket a= new tiket();
                        a.setId_usr(id_user);
                        a.setid(ar.getInt("id_acara"));
                        a.setjudul(ar.getString("judul_acara"));
                        a.setsubjudul(ar.getString("tag_line"));
                        a.setinstansi(ar.getString("instansi"));
                        a.setkuota(ar.getInt("kuota"));
                        a.setdaftar(ar.getInt("kuota"));
                        a.setharga(ar.getInt("harga"));
                        a.setimg(ar.getString("gmb_poster"));
                        a.setdesk(ar.getString("desk_acara"));
                        a.setnama(ar.getString("nm_user"));
                        int ver=Integer.parseInt(ar.getString("status_bayar"));
                        a.setverif(ver);
                        a.settgl(ar.getString("tgl_acara"));
                        a.setqrcode(ar.getString("qr_code"));
                        listtiket.add(a);
                    }
                    //Log.d("cek isi",ar.getString("judul_acara"));

                } catch (Exception e) {

                    e.printStackTrace();
//                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    tik.setVisibility(v.VISIBLE);
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
