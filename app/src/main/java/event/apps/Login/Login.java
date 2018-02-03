package event.apps.Login;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import event.apps.AlertDialogManager;
import event.apps.ApiVolley;
import event.apps.MainActivity;
import event.apps.R;
import event.apps.SessionManagement;
import event.apps.webserviceURL;
//import com.facebook.login.widget;

public class Login extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    LoginButton loginButton;
    TextView textView ,cek;
    CallbackManager callbackManager;
    String id, name, email,iduser ,status;
    URL profile_pic;
    AlertDialog alertDialog;
    AccessToken token;
    private GoogleApiClient googgleApiClient;
    private static final int REQ_CODE = 9001;
    AlertDialogManager alert = new AlertDialogManager();
    // Session Manager Class
    SessionManagement session;

    private SignInButton SignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        session = new SessionManagement(getApplicationContext());
        setContentView(R.layout.activity_login);

        final LoginButton button = (LoginButton) findViewById(R.id.login_button);
        button.setBackgroundResource(R.drawable.facebook);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        loginFacebook();

        alertDialog = new AlertDialog.Builder(getBaseContext()).create();

    }

    private  void loginGmail(){
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googgleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }

    private void loginFacebook() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        List<String> permissionNeeds = Arrays.asList("email", "public_profile");
        loginButton.setReadPermissions(permissionNeeds);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                final String accessToken = loginResult.getAccessToken().getUserId();
                Log.d("accessToken", accessToken);


                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        try {
                            id = object.getString("id");
                            name = object.getString("name");
                            try {
                                profile_pic = new URL(
                                        "http://graph.facebook.com/" + id + "/picture?type=large");
                                Log.i("profile_pic",
                                        profile_pic + "");

                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            email = object.getString("email");

                            JSONObject body= new JSONObject();
                            try
                            {
                                body.put("id_fb",id);
                                body.put("email",email);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            session.createLoginSession(id, email);

                            ApiVolley req = new ApiVolley(getBaseContext(), body, "POST", webserviceURL.get_login, "", "", 0, new ApiVolley.VolleyCallback() {

                                public void onSuccess(String result) {

                                    Log.d("LoginActivity", result.toString());
                                    Log.d("pict",profile_pic.toString());
                                    try {
                                        JSONObject response1 = new JSONObject(result);
                                        status = response1.getJSONObject("metadata").getString("status");
                                        Log.d("cek data",id);
                                        int sts = Integer.parseInt(status);
                                        if(sts==400){
                                            Intent intent = new Intent(getBaseContext(), registerForm.class);
                                            intent.putExtra("idUser", id);
                                            intent.putExtra("nameUser", name);
                                            intent.putExtra("emailUser", email);
                                            intent.putExtra("pictUser", profile_pic.toString());
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            getBaseContext().startActivity(intent);}
                                        else{
                                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.putExtra("idUser", id);
                                            getBaseContext().startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(String result) {

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
                Bundle parameters = new Bundle();
                parameters.putString("fields",
                        "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                textView.setText("login CAnceled");
            }

            @Override
            public void onError(FacebookException error) {

            }

        });
    }

    private void handleGmailID(String idGmail, String username, String email, String urlpic){
        String id = idGmail;
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        String username1 = username;
        String email1 = email;
        String urlpic1 = urlpic;
        String type = "login";
        backgroundWorkerRegForm backgroundWorkerRegForm = new backgroundWorkerRegForm(this);
        backgroundWorkerRegForm.execute(type, id, username1, email1,urlpic1);
    }

    private void handleFacebookID(String idFacebook, String username, String email, URL urlpic) {
        String id = idFacebook;
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        String username1 = username;
        String email1 = email;
        String urlpic1 = urlpic.toString();
        String type = "login";
        backgroundWorkerRegForm backgroundWorkerRegForm = new backgroundWorkerRegForm(this);
        backgroundWorkerRegForm.execute(type, id, username1, email1,urlpic1);

    }

    private void goToForm(String id){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE){
            GoogleSignInResult result =  Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    private void handleResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String id = account.getId();
            String name = account.getDisplayName();
            String email = account.getId();
            String img_url = account.getPhotoUrl().toString();
            Toast.makeText(this, "Success Login", Toast.LENGTH_SHORT).show();
            handleGmailID(id,name, email, img_url);

        }else{
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googgleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                signIn();
                break;

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
