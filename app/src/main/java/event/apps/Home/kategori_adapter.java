package event.apps.Home;

/**
 * Created by rejak on 4/10/2017.
 */

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

public class kategori_adapter extends BaseAdapter{
    private Context mContext;
    private List<kategori> kategorilist;

    public kategori_adapter(Context c,List<kategori> kategorilist) {
        mContext = c;
        this.kategorilist=kategorilist;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return kategorilist.size();
    }

    @Override
    public Object getItem(int position) {
        kategorilist.get(position);
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
        final kategori f = kategorilist.get(position);
        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.kotak_kategori, null);
            ImageView gambar_kotak=(ImageView) grid.findViewById(R.id.gambar_kotak);
            TextView nama =(TextView) grid.findViewById(R.id.judul);
            nama.setText(f.getnama());
            String image_url= webserviceURL.P_image+f.geticon();
            Picasso.with(mContext).load(image_url)
                    .placeholder(R.drawable.event_small) // optional
                    .error(R.drawable.event_small).into(gambar_kotak);

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}