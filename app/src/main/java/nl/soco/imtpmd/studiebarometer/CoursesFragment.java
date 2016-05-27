package nl.soco.imtpmd.studiebarometer;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
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

import nl.soco.imtpmd.studiebarometer.Models.CourseModel;


/**
 * Created by Collin on 19-4-2016.
 */
public class CoursesFragment extends Fragment {
    View myView;

    public String naam;
    private String JSON_STRING;
    private String json_string;
    private JSONArray jsonArray;
    private CourseAdapter courseAdapter;
    ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.courses_layout, container, false);

        getJSON(myView);
        return myView;
    }

    public void getJSON(View view) {
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
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
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
            //TextView textView = (TextView) findViewById(R.id.textView5);
            //textView.setText(result);
            json_string = result;
            parseJSON(myView);
        }
    }

    public void parseJSON(View view) {

        listView = (ListView) myView.findViewById(R.id.listview);

        courseAdapter = new CourseAdapter(getActivity().getBaseContext(), R.layout.courses_layout);
        listView.setAdapter(courseAdapter);

        if (json_string == null) {
            Toast.makeText(getActivity().getBaseContext(), "First Get JSON", Toast.LENGTH_LONG).show();
        } else {

            try {
                Log.d("log", " Log test");
                jsonArray = new JSONArray(json_string);
                int count = 0;
                String courseName, courseEcts, courseGrade, coursePeriod;

                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    courseName = JO.getString("name");
                    courseEcts = JO.getString("ects");
                    courseGrade = JO.getString("grade");
                    coursePeriod = JO.getString("period");
                    CourseModel courseModel = new CourseModel(courseName, courseEcts, courseGrade, coursePeriod);
                    courseAdapter.add(courseModel);
                    count++;
                    Log.d("log"," Log de array row: "+ count +" en de naam: "+courseName);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } //catch (NullPointerException e) {
            //TODO nullpointer verhelpen...
            //}


        }
    }
}
