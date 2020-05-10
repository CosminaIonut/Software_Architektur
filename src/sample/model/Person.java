package sample.model;


public class Person {
    private String vorname;
    private String nachName;

    public Person(String vorname, String nachName) {
        this.vorname = vorname;
        this.nachName = nachName;
    }

    public void setNachName(String nachName) {
        this.nachName = nachName;
    }
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachName() {
        return nachName;
    }

    public String getVorname() {
        return vorname;
    }
}

