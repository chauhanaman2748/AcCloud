package in.ac.accloud;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import static in.ac.accloud.R.layout;

public class MainActivity extends AppCompatActivity{
    ProgressBar pb2;
    TextView text1;
    int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        pb2 = (ProgressBar) findViewById(R.id.pb2);
        text1 = (TextView) findViewById(R.id.text1);
        progressAnimation();

    }

    public void progressAnimation(){
        final Timer ti=new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                pb2.setProgress(2*counter);



                if (counter == 50) {
                    startActivity(new Intent(MainActivity.this, login.class));
                    ti.cancel();
                }

            }
        };
        ti.schedule(tt,0,100);
    }


}