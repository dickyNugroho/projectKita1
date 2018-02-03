package event.apps.profil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

import event.apps.ApiVolley;
import event.apps.R;
import event.apps.SessionManagement;
import event.apps.webserviceURL;

import static android.app.Activity.RESULT_OK;

/**
 * Created by rejak on 3/17/2017.
 */

public class fra_acara extends Fragment implements View.OnClickListener{
    LoginButton loginButton;
    private Context context  ;
    TextView textView;
    CallbackManager callbackManager;
    String id, name, email;
    URL profile_pic;
    String idUser;
    private TextInputLayout inputLayoutjudul, inputLayoutdeskrip, inputLayoutinstansi,
            inputLayouttagline, inputLayoutkuota, inputLayoutharga,inputLayoutkontak, inputLayoutalamat, inputLayoutrekening;

    EditText acara1, acara2, acara3, acara4, acara5, acara6,acara10,acara8, acara7 ;


    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;


    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "MainActivity";

    CoordinatorLayout coordinatorLayout;
    FloatingActionButton btnSelectImage;
    AppCompatImageView PosterImg;

    String judul_acara,desk_acara, gmb_poster ;
    String harga, kontak, no_rek, alamat, instansi, tagline;

    int id_acara,id_user, id_kategori,id_bid, id_prov=0, id_kota=0,kuota,status;

    String tgl_acara, tgl_exp;
    String kuota1 = "";


    Spinner bid,prov,kota,kategori;
    ArrayAdapter<CharSequence> adapterBID,adapterPROV,adapterKOTA,adapterKategori;
    SessionManagement session;
    Button makeEvent, chooseImg;
    ImageView imgview;
    public View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile_acara, container, false);

        chooseImg = (Button) v.findViewById(R.id.Choose_Image);
        chooseImg.setOnClickListener(this);

        imgview = (ImageView) v.findViewById(R.id.imageView2);

        //input view
        inputLayoutjudul = (TextInputLayout)v.findViewById(R.id.input_layout_namaAcara);
        inputLayoutdeskrip = (TextInputLayout)v.findViewById(R.id.input_layout_desc);
        inputLayoutinstansi = (TextInputLayout)v.findViewById(R.id.input_layout_instansi);
        inputLayouttagline = (TextInputLayout)v.findViewById(R.id.input_layout_tagline);
        inputLayoutkuota = (TextInputLayout)v.findViewById(R.id.input_layout_kuota);
        inputLayoutharga = (TextInputLayout)v.findViewById(R.id.input_layout_harga);
        inputLayoutkontak = (TextInputLayout)v.findViewById(R.id.input_layout_kontak);
        inputLayoutalamat = (TextInputLayout)v.findViewById(R.id.input_layout_alamat);
        inputLayoutrekening = (TextInputLayout)v.findViewById(R.id.input_layout_rekening);

        acara1 = (EditText) v.findViewById(R.id.namaAcara);

        acara2 = (EditText) v.findViewById(R.id.descAcara);

        acara6 = (EditText) v.findViewById(R.id.Instansi);

        acara10 = (EditText) v.findViewById(R.id.Tagline);

        acara7 = (EditText) v.findViewById(R.id.kuota);

        acara3 = (EditText) v.findViewById(R.id.harga);

        acara4 = (EditText) v.findViewById(R.id.contact);

        acara5 = (EditText) v.findViewById(R.id.alamat);


        acara8 = (EditText) v.findViewById(R.id.no_rek);

        acara1.addTextChangedListener(new MyTextWatcher(acara1));
        acara2.addTextChangedListener(new MyTextWatcher(acara2));
        acara3.addTextChangedListener(new MyTextWatcher(acara3));
        acara4.addTextChangedListener(new MyTextWatcher(acara4));
        acara5.addTextChangedListener(new MyTextWatcher(acara5));
        acara6.addTextChangedListener(new MyTextWatcher(acara6));
        acara10.addTextChangedListener(new MyTextWatcher(acara10));
        acara8.addTextChangedListener(new MyTextWatcher(acara8));
        acara7.addTextChangedListener(new MyTextWatcher(acara7));


        //buat spinner
        bid = (Spinner) v.findViewById(R.id.spinnerBID);
        //prov = (Spinner) v.findViewById(R.id.spinnerProv);
        //kota = (Spinner) v.findViewById(R.id.spinnerKota);
        kategori = (Spinner) v.findViewById(R.id.spinnerKategori);
        //selesai dengan spinner
        adapterBID = ArrayAdapter.createFromResource(getActivity(), R.array.bid_name,android.R.layout.simple_spinner_item);
        adapterBID.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bid.setAdapter(adapterBID);
        /*adapterKOTA = ArrayAdapter.createFromResource(getActivity(), R.array.kota_name,android.R.layout.simple_spinner_item);
        adapterKOTA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kota.setAdapter(adapterKOTA);
        adapterPROV = ArrayAdapter.createFromResource(getActivity(), R.array.prov_name,android.R.layout.simple_spinner_item);
        adapterPROV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prov.setAdapter(adapterPROV);*/
        adapterKategori = ArrayAdapter.createFromResource(getActivity(), R.array.kategori_name,android.R.layout.simple_spinner_item);
        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategori.setAdapter(adapterKategori);
        session =new SessionManagement(getContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        idUser= user.get(SessionManagement.KEY_NAME);
        //.makeText(getContext(), "id user" + idUser, Toast.LENGTH_SHORT).show();
        //buat arrayadapter
        //cara kerja
        bid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_bid=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*kota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_kota=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        prov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_prov=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_kategori=position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //selesai cara kerja

        //selesai array adapter
        makeEvent = (Button) v.findViewById(R.id.MakeEvent);
        makeEvent.setOnClickListener(this);

        return v;
    }

    private void makeEvent(){

        if (!validatejudul()) {
            return;
        }

        if (!validatedesk()) {
            return;
        }

        if (!validateinstansi()) {
            return;
        }
        if (!validatetagline()) {
            return;
        }

        if (!validatekuota()) {
            return;
        }

        if (!validateharga()) {
            return;
        }
        if (!validatekontak()) {
            return;
        }
        if (!validatealamat()) {
            return;
        }

        if (!validaterekening()) {
            return;
        }

        status = 1;

        DatePicker dateAcara= (DatePicker) v.findViewById(R.id.tgl_acara);
        tgl_acara = dateAcara.getDayOfMonth()+"/"+dateAcara.getMonth()+"/"+dateAcara.getYear();
        DatePicker dateExp= (DatePicker) v.findViewById(R.id.tgl_exp);
        tgl_exp = dateExp.getDayOfMonth()+"/"+dateExp.getMonth()+"/"+dateExp.getYear();
        //Toast.makeText(getActivity(), tgl_acara, Toast.LENGTH_SHORT).show();

        judul_acara = acara1.getText().toString();

        desk_acara = acara2.getText().toString();

        harga = acara3.getText().toString();

        kontak = acara4.getText().toString();

        alamat = acara5.getText().toString();

        instansi = acara6.getText().toString();

        tagline = acara10.getText().toString();

        no_rek = acara8.getText().toString();

        kuota1 = acara7.getText().toString();
        kuota =  Integer.parseInt(kuota1);
        gmb_poster = getStringImage(bitmap);
        Log.d("encode poster : ",gmb_poster);
        JSONObject jbody= new JSONObject();
        try{
            jbody.put("id_user",idUser);
            jbody.put("id_kategori",id_kategori);
            jbody.put("judul_acara",judul_acara);
            jbody.put("desk_acara",desk_acara);
            jbody.put("instansi",instansi);
            jbody.put("tag_line",tagline);
            jbody.put("kuota",kuota);
            jbody.put("gmb_poster",gmb_poster);
            jbody.put("id_bid",id_bid);
            jbody.put("tgl_acara",tgl_acara);
            jbody.put("tgl_exp",tgl_exp);
            jbody.put("harga",harga);
            jbody.put("kontak",kontak);
            jbody.put("no_rek",no_rek);
            jbody.put("alamat",alamat);
            jbody.put("status",status);
            Log.d("jason",jbody.toString());
            //Toast.makeText(getActivity(), "succes make json", Toast.LENGTH_SHORT).show();
        } catch (JSONException e){
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(getActivity(), jbody, "POST", webserviceURL.makeEvent, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                JSONObject response;

                //Toast.makeText(getActivity(), "Success Create Event", Toast.LENGTH_SHORT).show();
                try {
                    response = new JSONObject(result);
                    String status = response.getJSONObject("metadata").getString("status");
                    Intent intent = getActivity().getIntent();
                    getActivity().finish();
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                //Toast.makeText(getActivity(), "ERorrr : "+result, Toast.LENGTH_LONG).show();

            }
        });

    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imgview.setImageBitmap(bitmap);
                String coba1 = getStringImage(bitmap);
//                Toast.makeText(this, "bitmap : "+coba1, Toast.LENGTH_SHORT).show();
                Log.e("bitmap poster acara : ",coba1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validatejudul() {
        if (acara1.getText().toString().trim().isEmpty()) {
            inputLayoutjudul.setError("Masukan Nama Acara");
            requestFocus(acara1);
            return false;
        } else {
            inputLayoutjudul.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatedesk() {
        if (acara2.getText().toString().trim().isEmpty()) {
            inputLayoutdeskrip.setError("Masukan Deskripsi Acara");
            requestFocus(acara2);
            return false;
        } else {
            inputLayoutdeskrip.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateinstansi() {
        if (acara6.getText().toString().trim().isEmpty()) {
            inputLayoutinstansi.setError("Masukan Instansi");
            requestFocus(acara6);
            return false;
        } else {
            inputLayoutinstansi.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatetagline() {
        if (acara10.getText().toString().trim().isEmpty()) {
            inputLayouttagline.setError("Masukan Tagline Acara");
            requestFocus(acara10);
            return false;
        } else {
            inputLayouttagline.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatekuota() {
        if (acara7.getText().toString().trim().isEmpty()) {
            inputLayoutkuota.setError("Masukan Kuota Acara");
            requestFocus(acara7);
            return false;
        } else {
            inputLayoutkuota.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateharga() {
        if (acara3.getText().toString().trim().isEmpty()) {
            inputLayoutharga.setError("Masukan Harga");
            requestFocus(acara3);
            return false;
        } else {
            inputLayoutharga.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatekontak() {
        if (acara4.getText().toString().trim().isEmpty()) {
            inputLayoutkontak.setError("Masukan Contact Person");
            requestFocus(acara4);
            return false;
        } else {
            inputLayoutkontak.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatealamat() {
        if (acara5.getText().toString().trim().isEmpty()) {
            inputLayoutalamat.setError("Masukan Alamat Acara");
            requestFocus(acara5);
            return false;
        } else {
            inputLayoutalamat.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validaterekening() {
        if (acara8.getText().toString().trim().isEmpty()) {
            inputLayoutrekening.setError("Masukan Rekening");
            requestFocus(acara8);
            return false;
        } else {
            inputLayoutrekening.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.namaAcara:
                    validatejudul();
                    break;
                case R.id.descAcara:
                    validatedesk();
                    break;
                case R.id.Instansi:
                    validateinstansi();
                    break;
                case R.id.Tagline:
                    validatetagline();
                    break;
                case R.id.kuota:
                    validatekuota();
                    break;
                case R.id.harga:
                    validateharga();
                    break;
                case R.id.contact:
                    validatekontak();
                    break;
                case R.id.alamat:
                    validatealamat();
                    break;
                case R.id.no_rek:
                    validaterekening();
                    break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        if(v.equals(makeEvent)){
            makeEvent();

        }else if(v.equals(chooseImg)){
            showFileChooser();
        }
    }
}
