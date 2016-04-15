package nl.soco.imtpmd.studiebarometer;

import android.content.ContentValues;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import nl.soco.imtpmd.studiebarometer.Models.CourseModel;
import nl.soco.imtpmd.studiebarometer.database.DatabaseHelper;
import nl.soco.imtpmd.studiebarometer.database.DatabaseInfo;
import nl.soco.imtpmd.studiebarometer.Models.CourseModel;

public class InsertCourseDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_course_data);

        // Roep een instantie van je class DatabaseHelper aan.
        // Merk op dat de DatabaseHelper synchronised is en er is altijd maar 1 instantie.
        // Dit is de singleton truc
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);

        // Maak even een Object waar we wat data instoppen.
        // Dit zijn <key> <value> pairs en deze vullen we met onze eigen namen en data
        ContentValues values = new ContentValues();
        values.put(DatabaseInfo.CourseColumn.NAME, "Inf MT Programming Mobile Devices");
        values.put(DatabaseInfo.CourseColumn.ECTS, "3");
        values.put(DatabaseInfo.CourseColumn.CODE, "IMTPMD");
        values.put(DatabaseInfo.CourseColumn.GRADE, "5.5");

        // INSERT dit values object in DE (ZELFGEMAAKTE) RIJ COURSE,
        dbHelper.insert(DatabaseInfo.CourseTables.COURSE, null, values);


    }

}

