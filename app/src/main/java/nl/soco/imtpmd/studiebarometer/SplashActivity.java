package nl.soco.imtpmd.studiebarometer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();


        Thread myThread = new Thread() {
            public void run() {

                try {

                    sleep(2500);
                    Intent startLoginScreen = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(startLoginScreen);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
