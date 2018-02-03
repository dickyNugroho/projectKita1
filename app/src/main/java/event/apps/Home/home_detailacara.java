package event.apps.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import event.apps.ApiVolley;
import event.apps.MainActivity;
import event.apps.R;
import event.apps.SessionManagement;
import event.apps.tiket.fra_tiket;
import event.apps.webserviceURL;

public class home_detailacara extends AppCompatActivity  {
    AccessToken token;
    SessionManagement session;
    Context context;
    int id,jum_daf;
    String id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_detailacara);

        TextView jud = (TextView)findViewById(R.id.det_judul);
        TextView subj = (TextView)findViewById(R.id.det_subjudul);
        TextView instansi = (TextView)findViewById(R.id.det_instansi);
        TextView harg = (TextView)findViewById(R.id.det_harga);
        ImageView gbr = (ImageView) findViewById(R.id.det_poster);
        Button detail=(Button) findViewById(R.id.button_detail);
        Button terdaftar =(Button) findViewById(R.id.daftar);
        Button b_kuota =(Button) findViewById(R.id.kuota);
        TextView desk = (TextView) findViewById(R.id.deskripsi);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Bundle i=getIntent().getExtras();
        String judul =i.getString("judul");
        String sub =i.getString("sub");
        String ins =i.getString("ins");
        int har=i.getInt("har");
        String img=i.getString("img");
        String deskrip=i.getString("desk");
        int kuota=i.getInt("kuota");
        final int daftar=i.getInt("daftar");
        id=i.getInt("id");
        id_user=i.getString("id_user");



    /*    if(kuota==daftar){
            detail.setVisibility(View.GONE);
            b_kuota.setVisibility(View.VISIBLE);
        }*/



        jud.setText(judul);
        subj.setText(sub);
        instansi.setText(ins);
        harg.setText("Rp."+har+",-");
        desk.setText(deskrip);
        Log.d("cek url ",img);
        Picasso.with(this).load(img).placeholder(R.drawable.event_big) // optional
                .error(R.drawable.event_big).into(gbr);
        //gbr.setImageResource(img);

        //session
        //final HashMap<String, String> user = session.getUserDetails();
        //Toast.makeText(getApplicationContext(),user.get(SessionManagement.KEY_NAME),Toast.LENGTH_SHORT);

        getSupportActionBar().setTitle("Rincian Acara");
        final String juml;

        detail.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        JSONObject body1 = new JSONObject();
                        JSONObject body = new JSONObject();
                        try{
                          body.put("id_acara",id);
                          body.put("id_user",id_user);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        ApiVolley requ = new ApiVolley(getApplicationContext(),body1,"GET",webserviceURL.listevent,"","",0, new ApiVolley.VolleyCallback() {
                            JSONObject response1;
                            @Override
                            public void onSuccess(String result) {
                                Log.d("cekk",result.toString());
                                try {
                                    JSONObject responseAPI = new JSONObject(result);
                                    JSONArray arr = responseAPI.getJSONArray("response");
                                    JSONObject a=arr.getJSONObject(0);
                                    jum_daf=a.getInt("jum_daftar");


                                    String status = response1.getJSONObject("metadata").getString("status");
                                    int sts = Integer.parseInt(status);
                                    if(sts==400){Toast.makeText(getApplication(), "Anda Telah Terdaftar", Toast.LENGTH_SHORT).show();}
                                    else{Toast.makeText(getApplication(), "Anda Berhasil Mendaftar", Toast.LENGTH_SHORT).show();}

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(String result) {

                            }
                        });
                        jum_daf=jum_daf+1;
                        JSONObject jumlah =new JSONObject() ;
                        try{
                            jumlah.put("id_acara",id);
                            jumlah.put("jum_daf",jum_daf);
                        }
                        catch(Exception e){e.printStackTrace();}

                        ApiVolley jum = new ApiVolley(getApplicationContext(),jumlah,"PUT",webserviceURL.jumdaf,"","",0, new ApiVolley.VolleyCallback() {
                            JSONObject response1;
                            @Override
                            public void onSuccess(String result) {
                                Log.d("cekk",result.toString());
                                try {
                                    response1 = new JSONObject(result);
                                    String status = response1.getJSONObject("metadata").getString("status");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(String result) {

                            }
                        });




                        String url=webserviceURL.delfav+"/"+id_user+"/"+id;

                        ApiVolley del = new ApiVolley(getBaseContext(),body,"DELETE",url,"","",0, new ApiVolley.VolleyCallback() {
                            JSONObject response1;
                            @Override
                            public void onSuccess(String result) {
                                try {
                                    response1 = new JSONObject(result);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(String result) {

                            }
                        });


                        ApiVolley req = new ApiVolley(getApplicationContext(),body,"POST",webserviceURL.pesan,"","",0, new ApiVolley.VolleyCallback() {
                            JSONObject response1;
                            @Override
                            public void onSuccess(String result) {
                                Log.d("cekk",result.toString());
                                try {
                                    response1 = new JSONObject(result);
                                    String status = response1.getJSONObject("metadata").getString("status");
                                    int sts = Integer.parseInt(status);
                                    if(sts==400){Toast.makeText(getApplication(), "Anda Telah Terdaftar", Toast.LENGTH_SHORT).show();}
                                    else{Toast.makeText(getApplication(), "Anda Berhasil Mendaftar", Toast.LENGTH_SHORT).show();}

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(String result) {

                            }
                        });
                        Intent in = new Intent(home_detailacara.this, MainActivity.class);
                        startActivity(in);

                    }
                }
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isi= i.getString("judul");
                Snackbar.make(view, isi, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bar_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.fav) {
            final Bundle i=getIntent().getExtras();
            int id_acr=i.getInt("id");
            String id_user=i.getString("id_user");
            JSONObject body = new JSONObject();
            try{
                body.put("id_acara",id_acr);
                body.put("id_user",id_user);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            ApiVolley req = new ApiVolley(getApplicationContext(),body,"POST",webserviceURL.favorit,"","",0, new ApiVolley.VolleyCallback() {
                JSONObject response1;
                @Override
                public void onSuccess(String result) {
                    Log.d("cekk",result.toString());
                    try {
                        response1 = new JSONObject(result);
                        String status = response1.getJSONObject("metadata").getString("status");
                        int sts = Integer.parseInt(status);
                        if(sts==400){Toast.makeText(getApplication(), "Acara sudah ada di favorit", Toast.LENGTH_SHORT).show();}
                        else{Toast.makeText(getApplication(), "Acara Berhasil dimasukkan di favorit", Toast.LENGTH_SHORT).show();}
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String result) {

                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
