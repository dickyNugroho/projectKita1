package event.apps.profil;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import event.apps.ApiVolley;
import event.apps.MainActivity;
import event.apps.R;
import event.apps.SessionManagement;
import event.apps.favorit.fav_detail_acara;
import event.apps.webserviceURL;

/**
 * Created by rejak on 4/10/2017.
 */

public class detail_acara_profil extends AppCompatActivity {
    AccessToken token;
    SessionManagement session;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_detail_acara);

        TextView jud = (TextView)findViewById(R.id.det_judul);
        TextView subj = (TextView)findViewById(R.id.det_subjudul);
        TextView instansi = (TextView)findViewById(R.id.det_instansi);
        TextView harg = (TextView)findViewById(R.id.det_harga);
        ImageView gbr = (ImageView) findViewById(R.id.det_poster);
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
        String img=webserviceURL.baseurl+i.getString("img");
        String deskrip=i.getString("desk");

        jud.setText(judul);
        subj.setText(sub);
        instansi.setText(ins);
        harg.setText("Rp."+har+",-");
        desk.setText(deskrip);
        Log.d("cek url ",img);
        Picasso.with(this).load(img).placeholder(R.drawable.semnas) // optional
                .error(R.drawable.semnas).into(gbr);
        //gbr.setImageResource(img);

        //session
        //final HashMap<String, String> user = session.getUserDetails();
        //Toast.makeText(getApplicationContext(),user.get(SessionManagement.KEY_NAME),Toast.LENGTH_SHORT);

        getSupportActionBar().setTitle("Rincian Acara");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

            return true;

    }
}
