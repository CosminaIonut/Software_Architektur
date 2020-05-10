package sample.controller;




import sample.controller.MoreCreditsException;
import sample.controller.NoPlaceException;
import sample.model.Kurs;
import sample.model.Lehrer;
import sample.model.Student;
import sample.repository.ICrudRepository;
import sample.repository.JdbcKursRepo;
import sample.repository.JdbcLehrerRepo;
import sample.repository.JdbcStudentRepo;


import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    public ICrudRepository<Lehrer> lehrers;
    public ICrudRepository<Student> students;
    public ICrudRepository<Kurs> kurse;

    public Controller() throws SQLException, IOException {
        this.kurse= new JdbcKursRepo();
        this.students=new JdbcStudentRepo();
        this.lehrers=new JdbcLehrerRepo();
    }
    public Controller(ICrudRepository<Lehrer> lehrers, ICrudRepository<Student> students, ICrudRepository<Kurs> kurse) throws IOException {
        this.lehrers = lehrers;
        this.students = students;
        this.kurse = kurse;
    }

    /**
     * Der Student meldet sich fur einen neuen Kurs
     *
     * @param kurs der Kurs fur welcher Kurs sich der Student sich einschreibt
     * @param stud der Student der sich fur ein Kurs einschreibt
     * @return false wenn der Kurs nicht eingefugt fur den Student ist
     * @throws NoPlaceException wenn es keinen Platz mehr gibt
     */
    public boolean anmelden(Kurs kurs, Student stud) throws NoPlaceException {
        if (kurs.getStudentsEnrolled().size() == kurs.getMaxEnrollment()) {
            throw new NoPlaceException();
        } else {
            List<Kurs>list=stud.getEnrolledCourses().stream().filter(k->k.getKursId() == kurs.getKursId()).collect(Collectors.toList());
            if(!list.isEmpty()){
                return false;
            }
        }
        if (stud.getTotalCredits() + kurs.getCredits() > 30) {
            return false;           //with this course the maximum credits will be exceeded
        }

        //add course to list
        List<Kurs> enrolledCs = stud.getEnrolledCourses();
        enrolledCs.add(kurs);
        stud.setEnrolledCourses(enrolledCs);

        //increase credit number
        int creds = stud.getTotalCredits() + kurs.getCredits();
        stud.setTotalCredits(creds);
        students.update(stud);
        return true;

    }

    /**
     * Die Verfugbare Filme Anzeigt
     *
     * @return die Liste von Kutse die verfugbar sind
     * @throws IOException -
     */
    public List<Kurs> verfugbarAnzeigen() throws IOException {
        List<Kurs> alleKurse = new ArrayList<>();
        List<Kurs> verfugbar = new ArrayList<>();
        alleKurse = (List<Kurs>) kurse.findAll();

        for (Kurs k : alleKurse) {
            List<Student> enrolledStud = anzeigeBestimmtenKurs(k);
            if (enrolledStud.size() < k.getMaxEnrollment()) {
                verfugbar.add(k);
            }
        }

        return verfugbar;
    }

    /**
     * Zeigt die Studente die sich bei einen Kurs eingeschrieben haben
     *
     * @param kurs Der Kurs fur den wir sehen wollen die Studenten die eingeschreiben sind
     * @return die Liste von Studenten die Eingeschrieben sind
     * @throws IOException -
     */
    public List<Student> anzeigeBestimmtenKurs(Kurs kurs) throws IOException {
        Iterable<Student> alleStudenten = students.findAll();
        List<Student> studentsEnrolled = new ArrayList<>();
        alleStudenten.forEach(s -> {
            List<Kurs>list=s.getEnrolledCourses().stream().filter(k->k.getKursId() == kurs.getKursId()).collect(Collectors.toList());
            if(!list.isEmpty()){
                studentsEnrolled.add(s);
            }

        });
        return studentsEnrolled;
    }

    /**
     * Zeigt alle Kurse
     *
     * @return Alle Kurse
     * @throws IOException -
     */
    public Iterable<Kurs> alleKurse() throws IOException {
        return kurse.findAll();
    }

    /**
     * Der Student meldet sich fur einen neuen Kurs
     *
     * @param stud der Student der sich fur ein Kurs einschreibt
     * @param kurs der Kurs fur welcher Kurs sich der Student sich einschreibt
     * @return false wenn der Student  nicht zu diesen Kurs sich einschreiben kann
     * @throws MoreCreditsException -
     */
    public boolean kursEinfugen(Student stud, Kurs kurs) throws MoreCreditsException {
        List<Kurs>list=stud.getEnrolledCourses().stream().filter(k->k.getKursId() == kurs.getKursId()).collect(Collectors.toList());
        if(!list.isEmpty()){
            return false;
        }

        if (stud.getTotalCredits() + kurs.getCredits() > 30)
            throw new MoreCreditsException();

        else {
            List<Student> studentsEnrolled = kurs.getStudentsEnrolled();
            studentsEnrolled.add(stud);
            kurs.setStudentsEnrolled(studentsEnrolled);

            List<Kurs> kursen = stud.getEnrolledCourses();
            kursen.add(kurs);
            stud.setEnrolledCourses(kursen);

            int kredits = stud.getTotalCredits() + kurs.getCredits();
            stud.setTotalCredits(kredits);
            students.update(stud);
            kurse.update(kurs);
            return true;
        }
    }

    /**
     * Wenn die Kredite einer Kurs geandert werden werden auch in der Liste sich die Kredite anderen
     *
     * @param kurs Der Kurs fur welcher wir die Kredite andern
     * @throws IOException
     */
    public void neuAnzahlCredits(Kurs kurs) throws IOException {
        Iterable<Student> student;
        student = students.findAll();
        Kurs gesuchteKurs = kurse.findOne(kurs.getKursId());
        int credite = gesuchteKurs.getCredits();

        student.forEach(s->{
            s.getEnrolledCourses().forEach(k->{
                if (gesuchteKurs.getKursId() == k.getKursId()) {
                    if (s.getTotalCredits() - credite + kurs.getCredits() > 30) {

                        List<Kurs> enrolledKurs;
                        enrolledKurs = s.getEnrolledCourses();

                        List<Kurs> enrolled = new ArrayList<>();
                        List <Kurs> listToAdd=enrolledKurs.stream().filter(k2-> k2.getKursId() != kurs.getKursId()).collect(Collectors.toList());
                        listToAdd.forEach(k4->{
                            enrolled.add(k4);
                        });

                        s.setEnrolledCourses(enrolled);
                        int credit = s.getTotalCredits() - credite;
                        s.setTotalCredits(credit);
                        students.update(s);
                        kurse.update(kurs);


                    } else {
                        List<Kurs> enrolledKurs;
                        enrolledKurs = s.getEnrolledCourses();
                        List<Kurs> enroll = new ArrayList<>();
                        List <Kurs> listToAdd=enrolledKurs.stream().filter(k3-> k3.getKursId() != kurs.getKursId()).collect(Collectors.toList());
                        listToAdd.forEach(k4->{
                            enroll.add(k4);
                        });

                        enroll.add(kurs);
                        s.setEnrolledCourses(enroll);


                        int credits = s.getTotalCredits() - credite + kurs.getCredits();
                        s.setTotalCredits(credits);
                        students.update(s);
                        kurse.update(kurs);
                    }
                }
            });

        });

    }

    /**
     * Die Methode loscht einen Kurs
     *
     * @param lehrer der Lerer fur welcher wir einen Kurs lochen wollen
     * @param kurs   der kurs der gelocht wird
     * @throws IOException -
     */
    public void removeKursfurLehrer(Lehrer lehrer, Kurs kurs) throws IOException {
        Kurs gesuchterkurs = kurse.findOne(kurs.getKursId());
        Iterable<Student> alleStudenten = students.findAll();

        alleStudenten.forEach(stud -> {
            List<Kurs> enrollstud = stud.getEnrolledCourses();
            List <Kurs>listToAdd2=enrollstud.stream().filter(k2->kurs.getKursId() == k2.getKursId()).collect(Collectors.toList());
            listToAdd2.forEach(k7->{

                int cred = stud.getTotalCredits() - gesuchterkurs.getCredits();
                stud.setTotalCredits(cred);
                students.update(stud);
                List<Kurs> enroll = new ArrayList<>();
                List <Kurs>listToAdd=enrollstud.stream().filter(k3->k3.getKursId() != kurs.getKursId()).collect(Collectors.toList());
                listToAdd.forEach(k4->{
                    enroll.add(k4);
                });
                stud.setEnrolledCourses(enroll);
                students.update(stud);
            });
            kurse.delete(gesuchterkurs.getKursId());

        });

    }

    /**
     * Die Methode filtriert die Studenten die weniger als 10 Kredite hat
     *
     * @return die filtrierte Liste von Studenten
     * @throws IOException -
     */
    public List<Student> filtriereStudents() throws IOException {
        List<Student> allstudents = (List<Student>) students.findAll();

        List<Integer> notenough = allstudents.stream().map(s -> s.getTotalCredits()).filter((number -> number <= 10)).collect(Collectors.toList());
        List<Student> stud = new ArrayList<>();
        allstudents.forEach(s -> {
            notenough.forEach(integer -> {
                if (s.getTotalCredits() == integer)
                    stud.add(s);
            });


        });

        Set<Student> set = new HashSet<>(stud);
        stud.clear();
        stud.addAll(set);
        return stud;
    }

    /**
     * Die Methode liefert die Liste von Kurse geordnet nach der Anzahl der Kredite
     *
     * @return Ordnet die Kurse nach der Anzahl der Kredite
     * @throws IOException -
     */
    public List<Kurs> sortKurse() throws IOException {
        List<Kurs> kurseList = (List<Kurs>) kurse.findAll();
        Collections.sort(kurseList, Comparator.comparing(k -> k.getCredits()));
        return kurseList;
    }


}



