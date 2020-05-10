package sample.model;

import java.util.LinkedList;
import java.util.List;

public class Lehrer extends Person {
    private long lehrerId;
    private List<Kurs> courses = null;


    public Lehrer(long lehrerId, String vorname, String nachName) {
        super(vorname, nachName);
        this.lehrerId = lehrerId;
        this.courses = new LinkedList<>();
        // this.courses = courses;
    }

    /**
     * Getter fur den Id
     *
     * @return der Id von dem Lehrer
     */
    public long getId() {
        return lehrerId;
    }

    /**
     * Getter fur die Kurs Liste
     *
     * @return die Liste von Kurse wo der Lehrer unterrichtet
     */
    public List<Kurs> getCourses() {
        return courses;
    }

    /**
     * Setter fur die Kurs Liste
     *
     * @param courses die Kurse welche er unterrichetet
     */
    public void setCourses(List<Kurs> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return getId()+" "+ getNachName()+" "+getVorname();
    }
}
