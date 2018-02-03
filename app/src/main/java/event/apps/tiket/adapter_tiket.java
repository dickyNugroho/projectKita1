package event.apps.tiket;

/**
 * Created by rejak on 3/6/2017.
 */


import android.app.Activity;
import android.support.annotation.IntegerRes;
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
import android.view.MenuItem;
import event.apps.R;
import event.apps.tiket.tiket;
import event.apps.webserviceURL;

import android.widget.PopupMenu.OnMenuItemClickListener;

import com.squareup.picasso.Picasso;

import java.util.List;

public class adapter_tiket extends BaseAdapter {

    private final Activity context;
    private  List<tiket> listiket;

    public adapter_tiket(Activity context, List<tiket> listtiket)
    {
        this.context=context;
        this.listiket=listtiket;

    }
    @Override
    public int getCount() {
        return listiket.size();
    }

    @Override
    public Object getItem(int i) {
        return listiket.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.isi_tiket, null,true);
        final tiket m = listiket.get(position);
        //ImageButton opsi=(ImageButton) rowView.findViewById(R.id.tombolmenu);
        ImageView gbr=(ImageView) rowView.findViewById(R.id.gbrposter);
        TextView Judul1 = (TextView) rowView.findViewById(R.id.judul);
        TextView subjudul1 = (TextView) rowView.findViewById(R.id.subjudul);
        TextView Instansi1 = (TextView) rowView.findViewById(R.id.Instansi);
        TextView daftar1 = (TextView) rowView.findViewById(R.id.daftar);
        TextView harga1 = (TextView) rowView.findViewById(R.id.harga);
        TextView kuota1 = (TextView) rowView.findViewById(R.id.kuota);

        Judul1.setText(m.getjudul());
        subjudul1.setText(m.getsubjudul());
        Instansi1.setText(m.getinstansi());
        daftar1.setText(String.valueOf(m.getkuota())+" /");
        harga1.setText("Rp."+String.valueOf(m.getharga())+",-");
        kuota1.setText(String.valueOf(m.getkuota())+" Org");
        String image_url= webserviceURL.img+m.getimg();
        Picasso.with(context).load(image_url)
                .placeholder(R.drawable.event_small) // optional
                .error(R.drawable.event_small).into(gbr);
        //opsi.setTag(position);

        //menu opsi di daftar acara
        /*opsi.setOnClickListener(new View.OnClickListener() {
            String btnmsg = "getting delete for " + judul[position];
            @Override
            public void onClick(View v) {
                *//** Instantiating PopupMenu class *//*
                PopupMenu popup = new PopupMenu(getContext(), v);

                *//** Adding menu items to the popumenu *//*
                popup.getMenuInflater().inflate(R.menu.menu_acara, popup.getMenu());

                *//** Defining menu item click listener for the popup menu *//*
                popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getContext(), "You selected the action : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                *//** Showing the popup menu *//*
                popup.show();
                //Toast.makeText(getContext(), btnmsg, Toast.LENGTH_LONG).show();
            }
        });*/

        return rowView;

    };
}