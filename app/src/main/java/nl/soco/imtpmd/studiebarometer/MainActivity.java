package nl.soco.imtpmd.studiebarometer;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.widget.TextView;

import nl.soco.imtpmd.studiebarometer.Models.UserModel;
import nl.soco.imtpmd.studiebarometer.Models.CourseModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public String naam;
    String JSON_STRING;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    CourseAdapter courseAdapter;
    ListView listView;
    public static UserModel user = new UserModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View v = navigationView.getHeaderView(0);
        TextView name = (TextView ) v.findViewById(R.id.nameMenuTxt);
        TextView email = (TextView ) v.findViewById(R.id.emailMenuTxt);

        name.setText(user.getName());
        email.setText(user.getEmail());

        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));

        listView = (ListView)findViewById(R.id.listview);

        courseAdapter = new CourseAdapter(this, R.layout.courses_layout);
        //TODO hier onder verder...
        //listView.setAdapter(courseAdapter);
        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("");
            int count = 0;
            String CourseName;
            int ects, grade, period;
            while (count<jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                CourseName = JO.getString("name");
                ects = JO.getInt("ects");
                grade = JO.getInt("grade");
                period = JO.getInt("period");
                CourseModel courseModel = new CourseModel(CourseName, ects, grade, period);
                courseAdapter.add(courseModel);
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            //TODO nullpointer verhelpen...
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        switch (id) {
            case R.id.nav_overview_layout:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new OverviewFragment()).commit();
                break;
            case R.id.nav_period_layout:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new PeriodFragment()).commit();
                break;
            case R.id.nav_courses_layout:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new CoursesFragment()).commit();
                break;
            case R.id.nav_logout:
                logout();
                break;
            default:
                Log.d("Log data:", "Navigation id error");
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout(){
        Log.d("Log data:", "Terug naar loginscherm etc.. naam:" + user.getName() + ".. email:" + user.getEmail());
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void getJSON (View view){
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = "http://fuujokan.nl/subject_lijst.json";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine())!=null ) {
                    stringBuilder.append(JSON_STRING+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = (TextView)findViewById(R.id.textView5);
            textView.setText(result);
            json_string = result;
        }
    }

    public void parseJSON (View view) {
        if (json_string==null){
            Toast.makeText(getApplicationContext(),"First Get JSON", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(this, CoursesFragment.class);
            intent.putExtra("json_data", json_string);
        }
    }

}
