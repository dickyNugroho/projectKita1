package event.apps.tiket;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.google.zxing.WriterException;

import event.apps.R;

public class tiket_detailtiket extends AppCompatActivity {
    private static final String TAG = "tiket";
    private String mEncodeString;
    private TextView mTextDesc;
    private ImageView mImageQR;
    private ProgressBar mProgress;
    private Bitmap mBitmapQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_detailtiket);
        TextView jud = (TextView)findViewById(R.id.det_judul);
        TextView subj = (TextView)findViewById(R.id.det_subjudul);
        TextView instansi = (TextView)findViewById(R.id.det_instansi);
        TextView harg = (TextView)findViewById(R.id.det_harga);
        TextView tgl =(TextView) findViewById(R.id.tanggal);
        TextView name=(TextView) findViewById(R.id.nama);
        Button detail=(Button) findViewById(R.id.button_detail);
        mImageQR = (ImageView) findViewById(R.id.imageQR);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Bundle i=getIntent().getExtras();
        String judul =i.getString("judul");
        String sub =i.getString("sub");
        String ins =i.getString("ins");
        int har=i.getInt("har");
        String tanggal=i.getString("tgl");
        String qrcode=i.getString("qr");
        String nama=i.getString("nama");

        name.setText(nama);
        jud.setText(judul);
        subj.setText(sub);
        instansi.setText(ins);
        harg.setText("Rp."+har+",-");
        tgl.setText(tanggal);


        //qrcode
        mEncodeString =qrcode;
        new AsyncGenerateQRCode().execute(GenerateQR.MARGIN_AUTOMATIC);


        //gbr.setImageResource(img);

        getSupportActionBar().setTitle("Tiket Acara "+judul+"");

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    private class AsyncGenerateQRCode extends AsyncTask<Integer, Void, Integer> {


        @Override
        protected Integer doInBackground(Integer... params) {
            if (params.length != 1) {
                throw new IllegalArgumentException("Must pass QR Code margin value as argument");
            }

            try {
                final int colorQR = Color.BLACK;
                final int colorBackQR = Color.WHITE;
                final int marginSize = params[0];
                final int width = 310;
                final int height = 310;

                mBitmapQR = GenerateQR.generateBitmap(mEncodeString, width, height,
                        marginSize, colorQR, colorBackQR);
            }
            catch (IllegalArgumentException iae) {
                Log.e(TAG, "Invalid arguments for encoding QR");
                iae.printStackTrace();
                return 0;
            }
            catch (WriterException we) {
                Log.e(TAG, "QR Writer unable to generate code");
                we.printStackTrace();
                return 0;
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {

            if (result != 0) {
                mImageQR.setImageBitmap(mBitmapQR);
            }else {
                mTextDesc.setText(getString(R.string.encode_error));
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
