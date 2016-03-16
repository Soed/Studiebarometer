package nl.soco.imtpmd.studiebarometer.model;

import java.io.Serializable;

public class Course implements Serializable {

    public String name;
    public String ects;
    public String grade;
    public String period;

    public Course(String name, String ects, String grade, String period) {
        this.name = name;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public String getEcts() {
        return ects;
    }

    public String getGrade() {
        return grade;
    }

    public String getPeriod() {
        return period;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEcts(String ects) {
        this.ects = ects;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
