package nl.soco.imtpmd.studiebarometer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Collin on 19-4-2016.
 */
public class GradeFragment extends Fragment {
    View myView;
    Spinner courseSpinner;
    EditText etCijfer;
    Button btnCijferOpslaan;

    String course;
    int grade;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.grade_layout, container, false);
        fillSpinner();

        btnCijferOpslaan = (Button) myView.findViewById(R.id.btnCijferOpslaan);
        btnCijferOpslaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGrade();
            }
        });

        return myView;
    }

    private void fillSpinner() {
        courseSpinner = (Spinner) myView.findViewById(R.id.courseSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.course_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        courseSpinner.setAdapter(adapter);
    }

    public void saveGrade() {
        try {
            courseSpinner = (Spinner) myView.findViewById(R.id.courseSpinner);
            course = courseSpinner.getSelectedItem().toString();

            etCijfer = (EditText) myView.findViewById(R.id.etCijfer);
            grade = Integer.parseInt(etCijfer.getText().toString());

            saveGradeDB();
        } catch (NullPointerException e) {
            Toast.makeText(getActivity().getBaseContext(), "Vul een cijfer in om op te slaan!", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity().getBaseContext(), "Vul een cijfer in om op te slaan!", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveGradeDB() {
        Toast.makeText(getActivity().getBaseContext(), "saveGradeDB gestart!!!!", Toast.LENGTH_SHORT).show();
    }

}
