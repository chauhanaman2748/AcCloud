package in.ac.accloud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity{

    EditText Username, Password;
    ProgressBar pb3;
    Button btnLogin, btnSignup,forgot,contact;

    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth=FirebaseAuth.getInstance();
        Username=(EditText)findViewById(R.id.Username);
        Password=(EditText)findViewById(R.id.Password);
        forgot=(Button) findViewById(R.id.forgot);
        contact=(Button) findViewById(R.id.contact);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnSignup=(Button)findViewById(R.id.btnSignup);
        pb3=(ProgressBar)findViewById(R.id.pb3);
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth!=null){
                    Toast.makeText(login.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent j=new Intent(login.this,home.class);
                    startActivity(j);
                }
                else {
                    Toast.makeText(login.this,"Please login",Toast.LENGTH_SHORT).show();
                }
            }
        };

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"8700230416"));
                startActivity(i);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(login.this,signin.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Username.getText().toString().trim();
                String Pass=Password.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Username.setError("Enter Email");
                    Username.requestFocus();
                    return;
                }
                if (Pass.isEmpty()) {
                    Password.setError("Enter Password");
                    Password.requestFocus();
                    return;
                }
                else {
                    pb3.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(username,Pass).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"You are logged in",Toast.LENGTH_SHORT).show();
                                pb3.setVisibility(View.GONE);
                                startActivity(new Intent(login.this,home.class));
                            } else{
                                Toast.makeText(getApplicationContext(),"Login Error",Toast.LENGTH_SHORT).show();
                                pb3.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }
}






