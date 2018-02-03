package event.apps.Home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import event.apps.ApiVolley;
import event.apps.R;
import event.apps.SessionManagement;
import event.apps.webserviceURL;

/**
 * Created by rejak on 4/10/2017.
 */

public class kategori_list extends AppCompatActivity {
    SearchView searchView;
    private ProgressDialog pDialog;
    ListView list;
    SessionManagement session;
    String uid;
    String id_kat;
    private List<acara> acaralist = new ArrayList<acara>();
    CustomListAdapter adapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_event);
        //session
        session = new SessionManagement(getBaseContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        uid = user.get(SessionManagement.KEY_NAME);

        pDialog = new ProgressDialog(getBaseContext());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        final Bundle i=getIntent().getExtras();
        id_kat=i.getString("id");

        adapter = new CustomListAdapter(this, acaralist);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                acara a = acaralist.get(position);
                String jud = a.getjudul();
                String sub = a.getsubjudul();
                String ins = a.getinstansi();
                String desk = a.getdesk();
                int har = a.getharga();
                int id = a.getid();
                String im = webserviceURL.P_image + a.getimg();
                Intent in = new Intent(getBaseContext().getApplicationContext(), home_detailacara.class);
                in.putExtra("id_user", uid);
                in.putExtra("id", id);
                in.putExtra("judul", jud);
                in.putExtra("sub", sub);
                in.putExtra("ins", ins);
                in.putExtra("har", har);
                in.putExtra("img", im);
                in.putExtra("desk", desk);
                startActivity(in);
                //Toast.makeText(getActivity().getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });


        //Search
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Cari Acara");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                acaralist.clear();
                if (query != "") {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("cari", query);
                        json.put("id_kat",id_kat);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ApiVolley req = new ApiVolley(getBaseContext(), json, "POST", webserviceURL.carikat, "", "", 0, new ApiVolley.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.d("cekk", result.toString());
                            hidePDialog();
                            // Important Note : need to use try catch when parsing JSONObject, no need when parsing string

                            try {
                                JSONObject responseAPI = new JSONObject(result);
                                JSONArray arr = responseAPI.getJSONArray("response");
                                String status = responseAPI.getJSONObject("metadata").getString("status");
                                responseAPI = null;
                                for (int i = 0; i < arr.length(); i++) {
                                    JSONObject ar = arr.getJSONObject(i);
                                    acara a = new acara();
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
                                if (query == "") {
                                    list();
                                } else {
                                    Toast.makeText(getBaseContext(), "Terjadi kesalahan saat memuat data", Toast.LENGTH_LONG).show();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(String result) {

                        }
                    });
                } else {
                    list();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                acaralist.clear();
                if (newText != "") {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("cari", newText);
                        json.put("id_kat",id_kat);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ApiVolley req = new ApiVolley(getBaseContext(), json, "POST", webserviceURL.carikat, "", "", 0, new ApiVolley.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.d("cekk", result.toString());
                            hidePDialog();
                            // Important Note : need to use try catch when parsing JSONObject, no need when parsing string

                            try {
                                JSONObject responseAPI = new JSONObject(result);
                                JSONArray arr = responseAPI.getJSONArray("response");
                                String status = responseAPI.getJSONObject("metadata").getString("status");
                                responseAPI = null;
                                for (int i = 0; i < arr.length(); i++) {
                                    JSONObject ar = arr.getJSONObject(i);
                                    acara a = new acara();
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
                                if (newText == "") {
                                    list();
                                } else {
                                    Toast.makeText(getBaseContext(), "Terjadi kesalahan saat memuat data", Toast.LENGTH_LONG).show();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(String result) {

                        }
                    });
                } else {
                    list();
                }

                //Toast.makeText(getActivity().getBaseContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });

        list();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void list() {
        acaralist.clear();
        JSONObject json = new JSONObject();
        ApiVolley req = new ApiVolley(getBaseContext(), json, "GET", webserviceURL.listeventkat+"/"+id_kat, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("cekk", result.toString());
                hidePDialog();
                // Important Note : need to use try catch when parsing JSONObject, no need when parsing string

                try {
                    JSONObject responseAPI = new JSONObject(result);
                    JSONArray arr = responseAPI.getJSONArray("response");
                    String status = responseAPI.getJSONObject("metadata").getString("status");
                    responseAPI = null;
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject ar = arr.getJSONObject(i);
                        acara a = new acara();
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("kategori_list Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
