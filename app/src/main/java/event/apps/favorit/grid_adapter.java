package event.apps.favorit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import event.apps.ApiVolley;
import event.apps.R;
import event.apps.webserviceURL;

public class grid_adapter extends BaseAdapter{
    private Context mContext;
    private List<favorit> favoritList;

    public grid_adapter(Context c,List<favorit> favlist) {
        mContext = c;
        this.favoritList=favlist;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return favoritList.size();
    }

    @Override
    public Object getItem(int position) {
        favoritList.get(position);
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final favorit f = favoritList.get(position);
        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.kotak_favorit, null);
            ImageButton opsi=(ImageButton) grid.findViewById(R.id.tombol);
            TextView jud = (TextView) grid.findViewById(R.id.judul_kotak);
            ImageView imageView = (ImageView)grid.findViewById(R.id.gambar_kotak);
            TextView kuota =(TextView)grid.findViewById(R.id.sisa_kuota);
            jud.setText(f.getjudul());
            String image_url= webserviceURL.img+f.getimg();
            Picasso.with(mContext).load(image_url)
                    .placeholder(R.drawable.event_small) // optional
                    .error(R.drawable.event_small).into(imageView);
            //imageView.setImageResource(f.getimg());
            kuota.setText(String.valueOf(f.getkuota()));

            opsi.setTag(position);
            opsi.setOnClickListener(new View.OnClickListener() {
                String btnmsg = "getting delete for " + f.getjudul();
                @Override
                public void onClick(View v) {
                    /** Instantiating PopupMenu class */
                    PopupMenu popup = new PopupMenu(mContext.getApplicationContext(), v);

                    /** Adding menu items to the popumenu */
                    popup.getMenuInflater().inflate(R.menu.menu_fav, popup.getMenu());

                    /** Defining menu item click listener for the popup menu */
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            JSONObject body = new JSONObject();
                            try{
                                body.put("id_acara",f.getid());
                                body.put("id_user",f.getId_usr());
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                            ApiVolley req = new ApiVolley(mContext,body,"POST",webserviceURL.listfav,"","",0, new ApiVolley.VolleyCallback() {
                                JSONObject response1;
                                @Override
                                public void onSuccess(String result) {
                                    Log.d("cekk",result.toString());
                                    try {
                                        response1 = new JSONObject(result);
                                        String status = response1.getJSONObject("metadata").getString("status");
                                        int sts = Integer.parseInt(status);
                                        if(sts==400){Toast.makeText(mContext, "Anda Telah Terdaftar", Toast.LENGTH_SHORT).show();}
                                        else{
                                            Toast.makeText(mContext, "Anda Berhasil Mendaftar", Toast.LENGTH_SHORT).show();

                                        }
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
                    });

                    /** Showing the popup menu */
                    popup.show();
                    //Toast.makeText(getContext(), btnmsg, Toast.LENGTH_LONG).show();
                }
            });

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}