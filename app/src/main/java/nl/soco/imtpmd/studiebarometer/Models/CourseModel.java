package nl.soco.imtpmd.studiebarometer.Models;
import java.io.Serializable;

public class CourseModel implements Serializable {           // WAAROM serializable ????

    private String name;
    private int ects;
    private int grade;
    private int period;


    public CourseModel(String courseName, int ects, int grade, int period){
        this.name = courseName;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

}
