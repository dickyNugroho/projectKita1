package event.apps.Login;



import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.io.UnsupportedEncodingException;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.ProtocolException;
        import java.net.URL;
        import java.net.URLEncoder;

import event.apps.ApiVolley;
import event.apps.MainActivity;
import event.apps.webserviceURL;
/**
 * Created by dickyCN on 3/12/2017.
 */

public class backgroundWorkerRegForm extends AsyncTask<String, Void, String>{

    private Context context  ;
    AlertDialog alertDialog;
    String idUser, nameuser, emailuser, pictuser, type;

    public backgroundWorkerRegForm (Context contex  ){
        this.context=contex;
    }

    @Override
    protected String doInBackground(String... params) {

        type = params[0];
        idUser=params[1];
        nameuser =params[2];
        emailuser=params[3];
        pictuser=params[4];

        if (type.equals("register")) {
            String id_usr=params[1];
            String email = params[2];
            String name = params[3];
            String username = params[4];
            String pictUrlUser = params[5];
            String handphoneUser = params[6];

            //getbody
            JSONObject jbody= new JSONObject();
            try{
                jbody.put("uid",id_usr);
                jbody.put("username",username);
                jbody.put("email_user",email);
                jbody.put("nm_user",name);
                jbody.put("gmb_user",pictUrlUser);
                jbody.put("no_hp",handphoneUser);

            } catch (JSONException e){
                e.printStackTrace();
            }

            ApiVolley request = new ApiVolley(context, jbody, "POST", webserviceURL.register, "", "", 0, new ApiVolley.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d("cekk",result);
                    JSONObject response;
                    try {
                        response = new JSONObject(result);
                        String status = response.getJSONObject("metadata").getString("status");
                        int sts = Integer.parseInt(status);
                        if(sts==200){
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("idUser", idUser);
                            context.startActivity(intent);
                        }
                        else{
                            Toast.makeText(context,"Input eror",Toast.LENGTH_SHORT);
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

        return null;
    }



    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
       /*if (type.equals("login")){
                alertDialog.setMessage("result2 : " + result);
                alertDialog.show();
                Intent intent = new Intent(context, registerForm.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idUser", idUser);
                intent.putExtra("nameUser", nameuser);
                intent.putExtra("emailUser", emailuser);
                intent.putExtra("pictUser", pictuser);
                context.startActivity(intent);
                ((Activity)context).finish();

            }else if (type.equals("register")){
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idUser", idUser);
                context.startActivity(intent);
                ((Activity)context).finish();
            }*/

       /* alertDialog.setMessage("result13223 : "+idUser);
        alertDialog.show();*/
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
