package nl.soco.imtpmd.studiebarometer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private static final String DEFAULT = "N/A";
    EditText etEmail;
    EditText etPassword;
    Button bLogin;
    Button bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bRegister = (Button) findViewById(R.id.bRegister);

        getSavedUserLogin();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);


            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String id = jsonResponse.getString("user_id");
                                String name = jsonResponse.getString("user_name");
                                String email = jsonResponse.getString("user_email");

                                setUser(id, name, email);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user_name", name);

                                LoginActivity.this.startActivity(intent);


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Inloggen mislukt!")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                saveUserLogin(email, password);

                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });


    }

    private void setUser(String id, String name, String email) {
        MainActivity.user.setId(Integer.parseInt(id));
        MainActivity.user.setName(name);
        MainActivity.user.setEmail(email);
        Log.d("Log data: "," id nu ook... " + id);
    }

    private void saveUserLogin(String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("gebruiker_email", email);
        editor.putString("gebruiker_password", password);
        editor.commit();

        Toast.makeText(this, "Login succesvol opgeslagen!", Toast.LENGTH_LONG).show();
    }

    private void getSavedUserLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("gebruiker_email", DEFAULT);
        String password = sharedPreferences.getString("gebruiker_password", DEFAULT);

        setSavedUserLogin(email, password);
    }

    private void setSavedUserLogin(String email, String password) {
        if (!email.equals(DEFAULT)) {
            etEmail.setText(email, TextView.BufferType.EDITABLE);
            etPassword.setText(password, TextView.BufferType.EDITABLE);
        } else {
            Log.d("Log data: ", "Geen gegevens!");
        }
    }
}
