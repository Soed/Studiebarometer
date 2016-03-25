package nl.soco.imtpmd.studiebarometer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button getButton, insertButton;

    RequestQueue requestQueue;
    String getUrl = "http://collinwoerde.nl/schoolApp/showUsers.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
        getButton = (Button) findViewById(R.id.getButton);
        insertButton = (Button) findViewById(R.id.insertButton);

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

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InsertActivity.class));
            }
        });


        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getUrl, (String) null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray users = response.getJSONArray("users");
                            tv.setText("");

                            for (int i = 0; i < users.length(); i++) {
                                JSONObject user = users.getJSONObject(i);

                                String voornaam = user.getString("user_voornaam");
                                String achternaam = user.getString("user_achternaam");
                                String email = user.getString("user_email");
                                String create_date = user.getString("user_create_date");

                                tv.append(create_date + "\n" + voornaam + "\n" + achternaam + "\n" + email + "\n" + "\n");

                            }

                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });
    }

    public static class InsertActivity extends AppCompatActivity {

        Button insertButton;
        EditText voornaamField;
        EditText achternaamField;
        EditText emailField;
        RequestQueue requestQueue;
        String insertUrl = "http://collinwoerde.nl/schoolApp/insert.php";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_insert);

            insertButton = (Button) findViewById(R.id.sendButton);
            voornaamField = (EditText) findViewById(R.id.voornaamField);
            achternaamField = (EditText) findViewById(R.id.achternaamField);
            emailField = (EditText) findViewById(R.id.emailField);
            requestQueue = Volley.newRequestQueue(getApplicationContext());

            insertButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     StringRequest stringRequest = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        }
                    }, new Response.ErrorListener() {
                         @Override
                         public void onErrorResponse(VolleyError error) {

                         }
                     }){
                         @Override
                     protected Map<String, String> getParams() {
                             Map<String, String> params = new HashMap<String, String>();
                             params.put("user_voornaam", voornaamField.getText().toString());
                             params.put("user_achternaam", achternaamField.getText().toString());
                             params.put("user_email", emailField.getText().toString());
                             params.put("user_id","");
                             return params;
                         }
                    };requestQueue.add(stringRequest);
                finish();
                }
            });

        }
    }
}
