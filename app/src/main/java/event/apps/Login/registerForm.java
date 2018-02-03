package event.apps.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import event.apps.Login.backgroundWorkerRegForm;
import event.apps.R;

import static android.R.attr.password;

public class registerForm extends AppCompatActivity {

    Button reg;
    String idUser, nameUser, emailUser, pictUser,tanggal;

    EditText username, email, name, handphone,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(),"Lengkapi form berikut",Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_register_form);
        Calendar c = Calendar.getInstance();
        idUser= getIntent().getStringExtra("idUser");
        nameUser= getIntent().getStringExtra("nameUser");
        emailUser= getIntent().getStringExtra("emailUser");
        pictUser= getIntent().getStringExtra("pictUser");

        Toast.makeText(getBaseContext(),nameUser,Toast.LENGTH_SHORT).show();
        username = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.emailuser);
        name = (EditText)findViewById(R.id.name);
        handphone = (EditText)findViewById(R.id.numberPhone);
        reg = (Button) findViewById(R.id.register);


        reg.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        String username1 =name.getText().toString() ;
                        String email1 = email.getText().toString();
                        String name1 = username.getText().toString() ;
                        String handphone1 = handphone.getText().toString();
                        String type = "register";
                        backgroundWorkerRegForm backgroundWorkerRegForm = new backgroundWorkerRegForm(getBaseContext());
                        backgroundWorkerRegForm.execute(type, idUser, email1, name1, username1, pictUser, handphone1);
                    }
                }
        );

        name.setText(nameUser);
        email.setText(emailUser);

    }

}
