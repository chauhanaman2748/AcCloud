package in.ac.accloud;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signin extends AppCompatActivity{
    EditText name, Username, Password, Password2, mob;
    Button btnSignin;
    ProgressBar pb3;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name = (EditText) findViewById(R.id.name);
        Username = (EditText) findViewById(R.id.Username);
        Password = (EditText) findViewById(R.id.Password);
        Password2 = (EditText) findViewById(R.id.Password2);
        pb3 = (ProgressBar) findViewById(R.id.pb3);

        mob = (EditText) findViewById(R.id.mob);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        mAuth=FirebaseAuth.getInstance();


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n =name.getText().toString().trim();
                String username = Username.getText().toString().trim();
                String Pass=Password.getText().toString().trim();
                String Pass2=Password2.getText().toString().trim();

                if (n.isEmpty()) {
                    name.setError("Enter Name");
                    name.requestFocus();
                    return;
                }
                if (Pass.isEmpty()) {
                    Password.setError("Enter Password");
                    Password.requestFocus();
                    return;
                }
                if (Pass2.isEmpty()) {
                    Password2.setError("Confirm Password");
                    Password2.requestFocus();
                    return;
                }
                if (Password.length()<6){
                    Password.setError("Minimum length of Password is 6");
                    Password.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mob.getText().toString())) {
                    mob.setError("Enter Phone Number");
                    mob.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    Username.setError("Enter Email");
                    Username.requestFocus();
                    return;
                }

                else{
                    pb3.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(username, Pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),"User Resgistered Successfully",Toast.LENGTH_SHORT).show();
                                        pb3.setVisibility(View.GONE);
                                        startActivity(new Intent(signin.this,login.class));
                                    } else{
                                        Toast.makeText(getApplicationContext(),"Not Resgistered Successfully",Toast.LENGTH_SHORT).show();
                                        pb3.setVisibility(View.GONE);
                                    }

                                }
                            });
                }

            }
        });
    }
}

