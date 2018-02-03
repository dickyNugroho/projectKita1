package event.apps;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.view.Window;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.cast.framework.SessionManager;

import java.net.URL;
import java.util.HashMap;

import event.apps.Home.fra_home;
import event.apps.Login.Login;
import event.apps.Login.registerForm;
import event.apps.favorit.fra_favorit;
import event.apps.jadwal.fra_jadwal;
import event.apps.profil.fra_profil;
import event.apps.tiket.fra_tiket;


public class MainActivity extends FragmentActivity {

    private TextView tvLabel;
    LoginButton loginButton;
    TextView textView;
    CallbackManager callbackManager;
    String id, name, email;
    URL profile_pic;
    String idUser;
    private Toolbar toolbar;
    AccessToken token;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Alert Dialog Manager
        AlertDialogManager alert = new AlertDialogManager();

        FacebookSdk.sdkInitialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();

        // Session class instance
        session = new SessionManagement(getBaseContext());

        callbackManager = CallbackManager.Factory.create();

        //login
        token = AccessToken.getCurrentAccessToken();
        idUser= getIntent().getStringExtra("idUser");

        if(token==null) {
            gologin();
        }


        setContentView(R.layout.activity_main);

        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManagement.KEY_NAME);
        //Toast.makeText(getBaseContext(),name,Toast.LENGTH_SHORT).show();

        Fragment fragment;
        fragment  = new fra_home();
        FragmentManager manager= getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        //transaction.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
        transaction.replace(R.id.fragment,fragment);
        transaction.commit();


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        helperbottom.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(

                new BottomNavigationView.OnNavigationItemSelectedListener() {


                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment;

                         if (item.getItemId() == R.id.nav_home) {

                             //Set status bar
                             Window window = getWindow();
                             window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                             fragment  = new fra_home();

                             FragmentManager manager= getSupportFragmentManager();
                            FragmentTransaction transaction=manager.beginTransaction();
                            //transaction.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
                            transaction.replace(R.id.fragment,fragment);
                            transaction.commit();


                        } else if (item.getItemId() == R.id.nav_fav) {

                             Window window = getWindow();
                             window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                             fragment  = new fra_favorit();
                             FragmentManager manager= getSupportFragmentManager();
                            FragmentTransaction transaction=manager.beginTransaction();
                            //transaction.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
                            transaction.replace(R.id.fragment,fragment);
                            transaction.commit();


                        } else if (item.getItemId() == R.id.nav_tiket) {
                             Window window = getWindow();
                             window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                            fragment  = new fra_tiket();
                             FragmentManager manager= getSupportFragmentManager();
                            FragmentTransaction transaction=manager.beginTransaction();
                            //transaction.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
                            transaction.replace(R.id.fragment,fragment);
                            transaction.commit();

                        } /*else if (item.getItemId() == R.id.nav_jadwal) {
                            fragment  = new fra_jadwal();
                             FragmentManager manager= getSupportFragmentManager();
                            FragmentTransaction transaction=manager.beginTransaction();
                            //transaction.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
                            transaction.replace(R.id.fragment,fragment);
                            transaction.commit();

                        }*/ else if (item.getItemId() == R.id.nav_profil) {
                            fragment  = new fra_profil();
                             FragmentManager manager= getSupportFragmentManager();
                            FragmentTransaction transaction=manager.beginTransaction();
                            //transaction.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
                            transaction.replace(R.id.fragment,fragment);
                            transaction.commit();


                        }
                        return true;
                    }
                });

    }

    private void gologin() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            //Toast.makeText(this, "Press Back again to Exit.",Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
