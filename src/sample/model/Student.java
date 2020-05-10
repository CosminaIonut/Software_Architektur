package sample.model;


import java.util.List;

public class Student extends Person {
    private long StudentId;
    private int totalCredits;
    private List<Kurs> enrolledCourses = null;

    public Student(long studentId, String vorname, String nachName, int totalCredits, List<Kurs> enrolledCourses) {
        super(vorname, nachName);
        StudentId = studentId;
        this.totalCredits = totalCredits;
        this.enrolledCourses = enrolledCourses;
    }

    /**
     * Getter fur die Anzahl der Kurse
     *
     * @return die totale Anzahl des Kredits
     */
    public int getTotalCredits() {
        return totalCredits;
    }

    /**
     * Getter fur die StudentId
     *
     * @return der StudentId
     */
    public long getStudentId() {
        return StudentId;
    }

    /***
     * Getter fur die Liste der Kurse
     * @return alle Kurse wo der Student eingeschrieben ist
     */
    public List<Kurs> getEnrolledCourses() {
        return enrolledCourses;
    }

    /**
     * Setter fur die Liste der Kurse
     *
     * @param enrolledCourses die Kurde bei denen einen Student teilnimmt
     */
    public void setEnrolledCourses(List<Kurs> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    /**
     * Setter fur die StudentId
     *
     * @param studentId Id von Student
     */
    public void setStudentId(long studentId) {
        StudentId = studentId;
    }

    /**
     * Setter fur die Kredits
     *
     * @param totalCredits Anzahl der Kredits
     */
    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }
    public String toString() {
        return getStudentId()+" "+ getNachName()+" "+getVorname()+" "+getTotalCredits()+" "+getEnrolledCourses();
    }


}
