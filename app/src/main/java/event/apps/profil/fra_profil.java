package event.apps.profil;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.wearable.view.CircledImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import event.apps.ApiVolley;
import event.apps.Home.TabPagerAdapter;
import event.apps.Login.Login;
import event.apps.R;
import event.apps.SessionManagement;
import event.apps.tiket.tiket;
import event.apps.webserviceURL;
import event.apps.profil.CircleTransform;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by rejak on 2/27/2017.
 */

public class fra_profil extends Fragment {
    Button edit;
    Button logout;
    ImageView gbr;
    TextView nama,email,telp;
    SessionManagement session;
    String idUser;
    int ac=1,daf=1;
    LinearLayout ly;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View v= inflater.inflate(R.layout.fragment_profil,container, false);

        final TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tab_layout_profil);
        edit = (Button) v.findViewById(R.id.edit);
        nama=(TextView) v.findViewById(R.id.nama_prof);
        email= (TextView) v.findViewById(R.id.email_prof);
        telp=(TextView) v.findViewById(R.id.nomer_prof);
        gbr =(ImageView) v.findViewById(R.id.profile_image);
        TextView acara=(TextView) v.findViewById(R.id.acara);
        TextView daftar=(TextView) v.findViewById(R.id.foleve);
        ly =(LinearLayout) v.findViewById(R.id.proflayout);
        ly.setVisibility(View.VISIBLE);
        session=new SessionManagement(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        idUser= user.get(SessionManagement.KEY_NAME);

        //hit acara
        JSONObject json = new JSONObject();
        ApiVolley acr = new ApiVolley(getContext(), json, "GET", webserviceURL.userevent+idUser, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("cekk",result.toString());
                // Important Note : need to use try catch when parsing JSONObject, no need when parsing string

                try {
                    JSONObject responseAPI = new JSONObject(result);
                    JSONArray arr = responseAPI.getJSONArray("response");
                    responseAPI = null;
                    for(int i=0;i<arr.length();i++){
                        ac=ac+1;
                    }
                    //Log.d("cek isi",ar.getString("judul_acara"));

                } catch (Exception e) {

                    e.printStackTrace();
//                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getActivity(), "Terjadi kesalahan saat memuat data", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onError(String result) {

            }
        });

        JSONObject body = new JSONObject();
        try{json.put("id_user",idUser);}
        catch (Exception e){e.printStackTrace();}
        ApiVolley req = new ApiVolley(getContext(), body, "POST", webserviceURL.listtiket, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("cekk",result.toString());
                // Important Note : need to use try catch when parsing JSONObject, no need when parsing string

                try {
                    JSONObject responseAPI = new JSONObject(result);
                    JSONArray arr = responseAPI.getJSONArray("response");
                    String status = responseAPI.getJSONObject("metadata").getString("status");
                    responseAPI = null;
                    for(int i=0;i<arr.length();i++){
                        daf=daf+1;
                    }
                    //Log.d("cek isi",ar.getString("judul_acara"));

                } catch (Exception e) {

                    e.printStackTrace();
//                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();

                    //Toast.makeText(getActivity(), "Terjadi kesalahan saat memuat data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(String result) {

            }
        });



        String aca=Integer.toString(ac);
        String daft=Integer.toString(daf);

        acara.setText(aca);
        daftar.setText(daft);

        JSONObject body1 =new JSONObject();
        ApiVolley req1 = new ApiVolley(getContext(), body1, "GET", webserviceURL.showProfile+idUser, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                JSONObject response;
                try{
                    response= new JSONObject(result);
                    JSONArray arr=response.getJSONArray("response");
                    JSONObject a=arr.getJSONObject(0);
                    nama.setText(a.getString("nm_user"));
                    email.setText(a.getString("email_user"));
                    telp.setText(a.getString("no_hp"));
                    String img=a.getString("gmb_user");
                    Picasso.with(getContext()).load(webserviceURL.img+img)
                            .placeholder(R.drawable.back_profile) // optional
                            .error(R.drawable.back_profile).into(gbr);

                }
                catch (Exception e){e.printStackTrace();}
            }

            @Override
            public void onError(String result) {

            }
        });

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.getTabAt(0).setIcon(R.drawable.profile_list);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.getTabAt(1).setIcon(R.drawable.profile_acara);

        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.pagerprofile);
        final Pager_tab_profile adapter = new Pager_tab_profile(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        edit.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity().getApplicationContext(),edit_profil.class);
                        startActivity(i);
                    }
                }
        );

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==1){
                    ly.setVisibility(View.GONE);
                }
                else{ly.setVisibility(View.VISIBLE);}
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }


}
