package nl.soco.imtpmd.studiebarometer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InsertActivity extends AppCompatActivity {

    Button insertButton;
    EditText voornaamField;
    EditText achternaamField;
    EditText emailField;
    RequestQueue requestQueue;

    //String insertUrl = "http://127.0.0.1/AndroidApi/insert.php";
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
                         params.put("voornaam", voornaamField.getText().toString());
                         params.put("achternaam", achternaamField.getText().toString());
                         params.put("email", emailField.getText().toString());
                         params.put("id","");
                         return params;
                     }
                };requestQueue.add(stringRequest);
            finish();
            }
        });

    }
}
