package sample.model;


import java.util.ArrayList;
import java.util.List;

public class Kurs {
    private long kursId;
    private String name;
    private Lehrer lehrer;
    private int maxEnrollment;
    private List<Student> studentsEnrolled = null;
    private int credits;


    public Kurs(long kursId, String name, Lehrer lehrer, int maxEnrollment, int credits) {
        this.kursId = kursId;
        this.name = name;
        this.lehrer = lehrer;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = new ArrayList<>();
        //this.studentsEnrolled = studentsEnrolled;
        this.credits = credits;
    }

    /**
     * Getter fut den Anzahl von Kredits
     *
     * @return Anzahl von Kredits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Getter fur den Id von Kurs
     *
     * @return der Id des Kurses
     */
    public long getKursId() {
        return kursId;
    }

    /**
     * Getter fur den MaxAnzahl die sich einschreiben konnen
     *
     * @return MaxAnzahl die sich einschreiben konnen
     */
    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    /**
     * Getter fur der Lehrer der unterrichtet
     *
     * @return der Lehrer
     */
    public Lehrer getLehrer() {
        return lehrer;
    }

    /**
     * Getter fur der Name der Kurs
     *
     * @return Der Name der Kurs
     */
    public String getName() {
        return name;
    }

    /**
     * Getter fur die Stundenten die eingeschrieben sind
     *
     * @return die Stundenten die eingeschrieben sind
     */
    public List<Student> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    /**
     * Setter fur den Anzahl von Kredits
     *
     * @param credits Anzahl von Kredits
     */

    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Setter fur den Lehrer der Kurs
     *
     * @param lehrer der Lehrer der unterrichtet
     */
    public void setLehrer(Lehrer lehrer) {
        this.lehrer = lehrer;
    }

    /**
     * Setter fur vie viele sich einschreiben konnen
     *
     * @param maxEnrollment wie viele sich einschreiben konnen
     */
    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    /**
     * Setter fur den Name der Kurs
     *
     * @param name Der Name der Kurs
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter fur die Stundenten die eingeschrieben sind
     *
     * @param studentsEnrolled die Stundenten die eingeschrieben sind
     */
    public void setStudentsEnrolled(List<Student> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    public String toString() {
        return getKursId()+" "+ getName()+" "+getLehrer()+" "+getMaxEnrollment()+" "+getCredits();
    }
}

