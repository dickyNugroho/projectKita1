package event.apps.profil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import event.apps.ApiVolley;
import event.apps.R;
import event.apps.SessionManagement;
import event.apps.webserviceURL;
public class edit_profil extends AppCompatActivity implements View.OnClickListener{

    EditText name, email, noHp,username, pass;
    String idUser;

    URL urlPicture;
    Button editProfile, editPicture;

    SessionManagement session;

    ImageView profile_image;

    String gmb_profile = null;
    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;


    private static final int SELECT_PICTURE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        name = (EditText)findViewById(R.id.p_nama);
        email = (EditText)findViewById(R.id.p_email);
        noHp = (EditText)findViewById(R.id.p_nomor);
        username = (EditText)findViewById(R.id.p_username);
        pass = (EditText)findViewById(R.id.p_password);

        profile_image = (ImageView)findViewById(R.id.profile_image);

        editProfile = (Button)findViewById(R.id.p_edit);
        editPicture =(Button)findViewById(R.id.edit_gbr);

        editProfile.setOnClickListener(this);
        editPicture.setOnClickListener(this);

        session=new SessionManagement(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        idUser= user.get(SessionManagement.KEY_NAME);
        Toast.makeText(this, "id user" + idUser, Toast.LENGTH_SHORT).show();

        bacaDataProfile();
    }

    private void bacaDataProfile(){
        JSONObject jbody= new JSONObject();

        ApiVolley request = new ApiVolley(edit_profil.this, jbody, "GET", webserviceURL.showProfile+idUser, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                JSONObject response;

                Toast.makeText(edit_profil.this, "Success baca data", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject responseAPI = new JSONObject(result);
                    JSONArray arr = responseAPI.getJSONArray("response");
                    String status = responseAPI.getJSONObject("metadata").getString("status");
                    responseAPI = null;
                    String oldName, oldEmail, oldNohp, oldUsername, oldPass;
                    String urlpict;

                    for(int i=0;i<arr.length();i++){
                        JSONObject ar = arr.getJSONObject(i);
                        oldName = ar.getString("nm_user");
                        oldEmail = ar.getString("email_user");
                        oldNohp = ar.getString("no_hp");
                        oldUsername = ar.getString("username");
                        oldPass = ar.getString("password");
                        urlpict = ar.getString("gmb_user");
                        Log.d("EMAIL = ",oldEmail);
                        Toast.makeText(edit_profil.this, "Email = "+oldEmail, Toast.LENGTH_SHORT).show();
                        name.setText(oldName);
                        email.setText(oldEmail);
                        noHp.setText(oldNohp);
                        username.setText(oldUsername);
                        pass.setText(oldPass);
                        String url1 = webserviceURL.img+urlpict;
//                        URL myURL = new URL(urlpict);
                        Picasso.with(edit_profil.this).load(url1)
                                .placeholder(R.drawable.back_profile) // optional
                                .error(R.drawable.back_profile).into(profile_image);
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

    private void editProfile(){

        String name1 = name.getText().toString();
        String email1 = email.getText().toString();
        String noHp1 = noHp.getText().toString();
        String usernema1 = username.getText().toString();
        String pass1 = pass.getText().toString();

//        Log.d("bitmap : ",bitmap.toString());

        if(bitmap != null){
            gmb_profile = getStringImage(bitmap);
            Log.d("bitmap123 : ",gmb_profile);
            JSONObject jbody= new JSONObject();
            try{
                jbody.put("id_user",idUser);
                jbody.put("nm_user",name1);
                jbody.put("email_user",email1);
                jbody.put("no_hp",noHp1);
                jbody.put("username",usernema1);
                jbody.put("pass",pass1);
                jbody.put("gmb_profile",gmb_profile);

                Log.d("jason",jbody.toString());
                //Toast.makeText(this, "succes make jsonedit profile", Toast.LENGTH_SHORT).show();
            } catch (JSONException e){
                e.printStackTrace();
            }
            ApiVolley request = new ApiVolley(edit_profil.this, jbody, "PUT", webserviceURL.editProfile, "", "", 0, new ApiVolley.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    JSONObject response;

                    //Toast.makeText(edit_profil.this, "Success edit with pict", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    try {
                        response = new JSONObject(result);
                        JSONArray jsonresult = response.getJSONArray("response");

                        for(int i=0; i<jsonresult.length();i++){
                            JSONObject arr= jsonresult.getJSONObject(i);
//                        Toast.makeText(edit_profil.this, "email : "+arr.getString("email_user"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String result) {

                }
            });


        }else {
            //Toast.makeText(this, "I am hereeee", Toast.LENGTH_SHORT).show();

//            try {
//                URL url = new URL("http://....");
//
//                Bitmap image = BitmapFactory.decodeStream(urlPicture.openConnection().getInputStream());
//                gmb_profile = getStringImage(image);
//            } catch(IOException e) {
//                System.out.println(e);
//            }

            JSONObject jbody= new JSONObject();
            try{
                jbody.put("id_user",idUser);
                jbody.put("nm_user",name1);
                jbody.put("email_user",email1);
                jbody.put("no_hp",noHp1);
                jbody.put("username",usernema1);
                jbody.put("pass",pass1);
                jbody.put("gmb_profile",null);

                Log.d("jason",jbody.toString());
                //Toast.makeText(this, "succes make jsonedit profile", Toast.LENGTH_SHORT).show();
            } catch (JSONException e){
                e.printStackTrace();
            }
            ApiVolley request = new ApiVolley(edit_profil.this, jbody, "PUT", webserviceURL.editProfile, "", "", 0, new ApiVolley.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    JSONObject response;

                    //Toast.makeText(edit_profil.this, "Success edit without pict", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    try {
                        response = new JSONObject(result);
                        JSONArray jsonresult = response.getJSONArray("response");

                        for(int i=0; i<jsonresult.length();i++){
                            JSONObject arr= jsonresult.getJSONObject(i);
//                        Toast.makeText(edit_profil.this, "email : "+arr.getString("email_user"), Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                profile_image.setImageBitmap(bitmap);
                String coba1 = getStringImage(bitmap);
//                Toast.makeText(this, "bitmap : "+coba1, Toast.LENGTH_SHORT).show();
                Log.e("bitmap : ",coba1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == editProfile){
            editProfile();
        }else if(view == editPicture){
            showFileChooser();
        }

    }
}
