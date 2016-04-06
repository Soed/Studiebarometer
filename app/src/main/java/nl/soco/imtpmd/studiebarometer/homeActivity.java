package nl.soco.imtpmd.studiebarometer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;

public class HomeActivity extends AppCompatActivity {

    private PieChart pieChart = (PieChart) findViewById(R.id.pieChart);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


}
