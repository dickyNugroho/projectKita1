package event.apps.Home;

/**
 * Created by rejak on 3/6/2017.
 */


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IntegerRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;

import event.apps.ApiVolley;
import event.apps.R;
import event.apps.webserviceURL;

import android.widget.PopupMenu.OnMenuItemClickListener;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private Activity context;
    private List<acara> acaralist;
    int kuota,daftar;
    public CustomListAdapter(Activity context, List<acara> acaralist) {
        this.context = context;
        this.acaralist = acaralist;
    }

    @Override
    public int getCount() {
        return acaralist.size();
    }

    @Override
    public Object getItem(int i) {
        return acaralist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.isi_home_daftar, null,true);
        final acara m = acaralist.get(position);
        Log.d("judul",m.getjudul());
        //image

        ImageButton opsi=(ImageButton) rowView.findViewById(R.id.tombolmenu);
        ImageView gbr=(ImageView) rowView.findViewById(R.id.gbrposter);
        TextView Judul1 = (TextView) rowView.findViewById(R.id.judul);
        TextView subjudul1 = (TextView) rowView.findViewById(R.id.subjudul);
        TextView Instansi1 = (TextView) rowView.findViewById(R.id.Instansi);
        final TextView daftar1 = (TextView) rowView.findViewById(R.id.daftar);
        TextView harga1 = (TextView) rowView.findViewById(R.id.harga);
        final TextView kuota1 = (TextView) rowView.findViewById(R.id.kuota);

        String image_url= webserviceURL.img+m.getimg();
        Picasso.with(context).load(image_url)
                .placeholder(R.drawable.event_small) // optional
                .error(R.drawable.event_small).into(gbr);

        Judul1.setText(m.getjudul());
        subjudul1.setText(m.getsubjudul());
        Instansi1.setText(m.getinstansi());
        daftar1.setText(m.getdaftar()+" /");
        harga1.setText("Rp."+m.getharga()+",-");
        kuota1.setText(m.getkuota()+" Org");
        kuota=m.getkuota();
        daftar=m.getdaftar();


        opsi.setTag(position);

        //menu opsi di daftar acara
        opsi.setOnClickListener(new View.OnClickListener() {
            String btnmsg = "getting delete for " + m.getjudul();
            @Override
            public void onClick(View v) {
                /** Instantiating PopupMenu class */
                PopupMenu popup = new PopupMenu(context, v);

                /** Adding menu items to the popumenu */
                popup.getMenuInflater().inflate(R.menu.menu_acara, popup.getMenu());

                /** Defining menu item click listener for the popup menu */
                popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int status_pesan;

                           if (item.getItemId() != 2131755478) {
                               if(kuota > daftar) {
                               JSONObject body = new JSONObject();
                               try {
                                   body.put("id_acara", m.getid());
                                   body.put("id_user", m.getId_usr());
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }

                               ApiVolley req = new ApiVolley(context, body, "POST", webserviceURL.pesan, "", "", 0, new ApiVolley.VolleyCallback() {
                                   JSONObject response1;

                                   @Override
                                   public void onSuccess(String result) {
                                       Log.d("cekk", result.toString());
                                       try {
                                           response1 = new JSONObject(result);
                                           String status = response1.getJSONObject("metadata").getString("status");
                                           int sts = Integer.parseInt(status);
                                           if (sts == 400) {
                                               Toast.makeText(context, "Anda Telah Terdaftar", Toast.LENGTH_SHORT).show();
                                           } else {
                                               Toast.makeText(context, "Anda Berhasil Mendaftar", Toast.LENGTH_SHORT).show();
                                           }
                                       } catch (JSONException e) {
                                           e.printStackTrace();
                                       }
                                   }

                                   @Override
                                   public void onError(String result) {

                                   }
                               });
                           }else{ Toast.makeText(context,"Maaf Kuota Penuh",Toast.LENGTH_SHORT); }
                           } else {
                               JSONObject body = new JSONObject();
                               try {
                                   body.put("id_acara", m.getid());
                                   body.put("id_user", m.getId_usr());
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }

                               ApiVolley req = new ApiVolley(context, body, "POST", webserviceURL.favorit, "", "", 0, new ApiVolley.VolleyCallback() {
                                   JSONObject response1;

                                   @Override
                                   public void onSuccess(String result) {
                                       Log.d("cekk", result.toString());
                                       try {
                                           response1 = new JSONObject(result);
                                           String status = response1.getJSONObject("metadata").getString("status");
                                           int sts = Integer.parseInt(status);
                                           if (sts == 400) {
                                               Toast.makeText(context, "Acara sudah ada di favorit", Toast.LENGTH_SHORT).show();
                                           } else {
                                               Toast.makeText(context, "Acara Berhasil dimasukkan di favorit", Toast.LENGTH_SHORT).show();
                                           }
                                       } catch (JSONException e) {
                                           e.printStackTrace();
                                       }
                                   }

                                   @Override
                                   public void onError(String result) {

                                   }
                               });
                           }

                        return true;
                    }
                });

                /** Showing the popup menu */
                popup.show();
                //Toast.makeText(getContext(), btnmsg, Toast.LENGTH_LONG).show();
            }
        });

        return rowView;

    };
}

