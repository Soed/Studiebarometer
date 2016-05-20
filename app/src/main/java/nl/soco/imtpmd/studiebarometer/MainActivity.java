package nl.soco.imtpmd.studiebarometer;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import nl.soco.imtpmd.studiebarometer.Models.CourseModel;

public class MainActivity extends AppCompatActivity {
    //test
    TextView tv;
    Button getButton, insertButton;

    RequestQueue requestQueue;
    //String getUrl = "http://127.0.0.1/AndroidApi/showUsers.php";
    String getUrl = "http://collinwoerde.nl/schoolApp/showUsers.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("per1");
        tabSpec.setContent(R.id.tabPer1);
        tabSpec.setIndicator("Per. 1");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("per2");
        tabSpec.setContent(R.id.tabPer2);
        tabSpec.setIndicator("Per. 2");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("per3");
        tabSpec.setContent(R.id.tabPer3);
        tabSpec.setIndicator("Per. 3");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("per4");
        tabSpec.setContent(R.id.tabPer4);
        tabSpec.setIndicator("Per. 4");
        tabHost.addTab(tabSpec);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

    }

    public void openHomescreen(View view) {
        Log.d("log data: ", "Deze method wordt goed aangeroepen.");
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}