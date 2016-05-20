package nl.soco.imtpmd.studiebarometer;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private PieChart mChart;
    public String naam;
    public static final int MAX_ECTS = 60;
    public int currentEcts = 0;
    public int disabledEcts = 0;
    public int posibleEcts;
    public int staticEcts;
    public int unknownEcts = 60;
    TextView welkom;
    TextView advies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        naam = intent.getStringExtra("user_name");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // De teksten vinden
        welkom = (TextView) findViewById(R.id.txtWelkom);
        advies = (TextView) findViewById(R.id.txtAdvies);
        // De teksten wijzigen
        setWelkomTxt();
        setAdviesTxt();

        //De piechart instellen
        mChart = (PieChart) findViewById(R.id.chart);
        mChart.setDescription("");
        mChart.setTouchEnabled(true);
        mChart.setDrawSliceText(false);
        mChart.getLegend().setEnabled(true);
        mChart.setCenterText("Voortgang");
        mChart.setCenterTextSize(10);
        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        setData(currentEcts, disabledEcts, unknownEcts);

        //Button fab = (Button) findViewById(R.id.plusTweeTest);
        //fab.setVisibility(View.GONE);

        Button fab = (Button) findViewById(R.id.plusTweeTest);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((currentEcts + disabledEcts) < MAX_ECTS) {
                    currentEcts += 2;
                    //disabledEcts += 2;
                    unknownEcts = MAX_ECTS - (currentEcts + disabledEcts);
                } else {
                    currentEcts = 0;
                    disabledEcts = 0;
                    unknownEcts = 60;
                }
                setData(currentEcts, disabledEcts, unknownEcts);

                setWelkomTxt();
                setAdviesTxt();
            }
        });

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

        if (id == R.id.nav_home_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new HomeFragment())
                    .commit();
        } else if (id == R.id.nav_second_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new SecondFragment())
                    .commit();
        } else if (id == R.id.nav_third_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new ThirdFragment())
                    .commit();
        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setData(int current, int disabled, int unknown) {
        currentEcts = current;
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        yValues.add(new Entry(current, 0));
        xValues.add("Behaalde ECTS");

        yValues.add(new Entry(disabled, 1));
        xValues.add("Niet behaalde ECTS");

        yValues.add(new Entry(unknown, 2));
        xValues.add("Nog te behalen ECTS");

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(144, 191, 73)); //groen
        colors.add(Color.rgb(194, 58, 58)); //rood
        colors.add(Color.rgb(219, 201, 47)); //oranje

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setColors(colors);

        PieData data = new PieData(xValues, dataSet);
        mChart.setData(data); // bind dataset aan chart.
        mChart.invalidate();  // Aanroepen van een redraw
        //Log.d("aantal =", "" + currentEcts);
    }

    public void setWelkomTxt() {
        DateTime dt = new DateTime();
        int hour = dt.getHourOfDay();
        String welkomTxt;

        //Log.d("Log Data: ", " het uur: "+hour);

        if (hour >= 12 && hour < 18) {
            welkomTxt = "Goedemiddag " + naam + ", welkom bij de Studiebarometer app van HSL.";
        } else if (hour >= 18 && hour < 24) {
            welkomTxt = "Goedenavond " + naam + ", welkom bij de Studiebarometer app van HSL.";
        } else {
            welkomTxt = "Goedemorgen " + naam + ", welkom bij de Studiebarometer app van HSL.";
        }

        welkom.setText(welkomTxt);
    }

    public void setAdviesTxt() {
        String adviesTxt;
        posibleEcts = (currentEcts + unknownEcts);
        staticEcts = (currentEcts + disabledEcts);

        if (posibleEcts < 40) {
            adviesTxt = "BSA niet gehaald, helaas";
        } else if (posibleEcts > 0 && currentEcts <= 39) {
            adviesTxt = "BSA nog niet gehaald, pas op!";
        } else if (posibleEcts >= 40 && currentEcts <= 49) {
            adviesTxt = "BSA gehaald, nog niet verder met de hoofdfase!";
        } else if (posibleEcts >= 50 && currentEcts <= 59) {
            adviesTxt = "BSA gehaald en door met de hoodfase!";
        } else if (currentEcts == 60) {
            adviesTxt = "Propedeuse behaald, gefeliciteerd!";
        } else {
            adviesTxt = "?";
        }

        advies.setText(adviesTxt);

    }

    public void openPeriodScreen(View view) {
        Log.d("log data: ", "Deze method wordt goed aangeroepen.");
        //Intent intent = new Intent(HomeOLDActivity.this, MainActivity.class);
        //startActivity(intent);
    }

}
