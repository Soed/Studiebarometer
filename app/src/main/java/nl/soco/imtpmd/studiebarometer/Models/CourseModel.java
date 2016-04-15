package nl.soco.imtpmd.studiebarometer.Models;
import java.io.Serializable;

public class CourseModel implements Serializable {           // WAAROM serializable ????

    public String name;
    public String ects;
    public String grade;
    public String period;
    public String code;


    public CourseModel(String courseName, String ects, String grade, String period){
        this.name = courseName;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
        this.code = code;
    }

    // ADD GETTERS

    // ADD SETTERS
}
