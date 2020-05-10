package sample.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.controller.Controller;
import sample.controller.MoreCreditsException;
import sample.controller.NoPlaceException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.model.Kurs;
import sample.model.Lehrer;
import sample.model.Student;

import java.io.IOException;


import javafx.scene.Scene;

import java.sql.SQLException;
import java.util.*;

public class Ui extends Application {
    public Controller controller;
    private Stage studentStage;
    private Stage lehrerStage;
    private Scene studentScene;
    private Scene lehrerScene;
    private Student loggedStudent;
    private List<Kurs> kurse;
    private Scene sc;
    private Kurs kursToEnroll;
    private Lehrer loggedLehrer;
    private Kurs kursToDelete;
    private Kurs kursToUpdate;
    private List<Kurs> lsitkurseforL;
    ListView<Student> enrolledStud = new ListView<>();
    TextField kredit = new TextField();
    Label labelKredits = new Label();
    ListView<Kurs> myListView = new ListView<>();
    ListView<Student> myStudentView = new ListView<>();

    public Ui() throws IOException, SQLException {
        controller = new Controller();
    }

    public Ui(Controller controller) {
        this.controller = controller;
    }

    public void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Hello World");
        Group root = new Group();
        sc = new Scene(root, 900, 900);
        primaryStage.setScene(sc);

        primaryStage.setTitle("Student");
        primaryStage.setWidth(750);
        primaryStage.setHeight(700);

        Group root2 = new Group();
        lehrerStage = new Stage();
        lehrerScene = new Scene(root2, 900, 900);
        lehrerStage.setTitle("Lehrer");
        lehrerStage.setWidth(750);
        lehrerStage.setHeight(700);
        lehrerStage.setScene(lehrerScene);
        lehrerStage.show();
        studentScene(primaryStage);
        lehrerScane1();





        /******************************************************************************/

    }

    public void  studentScene(Stage primaryStage){
        Label l1=new Label();
        Label l2=new Label();
        Label l3=new Label();
        l1.setText("Id:");
        l2.setText("Name:");
        l3.setText("Vorname:");
        TextField insertId = new TextField();
        insertId.setPromptText("Id");
        insertId.setPrefSize(150,20);
        TextField insertNume = new TextField();
        insertNume.setPromptText("Name");
        insertNume.setPrefSize(150,20);
        TextField insertPrenume = new TextField();
        insertPrenume.setPromptText("Vorname");
        insertPrenume.setPrefSize(150,20);
        Button login = new Button();
        login.setText("Login Student");
        login.setStyle("-fx-font-size:17; -fx-background-color: #c3c4c4, linear-gradient(#d6d6d6 50%, white 100%)," +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);-fx-background-radius: 30;-fx-background-insets: 0,1,1;-fx-text-fill: black;");
        login.setOnAction(e -> {
            long idS = Long.parseLong(insertId.getText());
            String Nume = insertNume.getText();
            String Prenume = insertPrenume.getText();
            loggedStudent = controller.students.findOne(idS);

            try {
                sceneTwo(primaryStage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });



        VBox myVbox = new VBox();
        myVbox.setSpacing(10);
        myVbox.getChildren().addAll(insertId, insertNume, insertPrenume, login);
        VBox labels=new VBox();
        labels.setSpacing(15);
        labels.getChildren().addAll(l1,l2,l3);
        ((Group) sc.getRoot()).getChildren().addAll(myVbox);
        myVbox.setLayoutX(300);
        myVbox.setLayoutY(250);
        labels.setLayoutX(230);
        labels.setLayoutY(255);
        labels.setAlignment(Pos.CENTER);
        myVbox.setAlignment(Pos.CENTER);
        primaryStage.setScene(sc);
        primaryStage.show();

    }
    public void lehrerScane1(){
        TextField insertIdL = new TextField();
        insertIdL.setPromptText("Id");
        insertIdL.setPrefSize(150,20);
        TextField insertNumeL = new TextField();
        insertNumeL.setPromptText("Name");
        insertNumeL.setPrefSize(150,20);
        TextField insertPrenumeL = new TextField();
        insertPrenumeL.setPrefSize(150,20);
        insertPrenumeL.setPromptText("Vorname");
        Button loginL = new Button();
        loginL.setText("Login Lehrer");
        loginL.setStyle("-fx-font-size:17; -fx-background-color: #c3c4c4, linear-gradient(#d6d6d6 50%, white 100%)," +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);-fx-background-radius: 30;-fx-background-insets: 0,1,1;-fx-text-fill: black;");
        loginL.setOnAction(e -> {
            long idL = Long.parseLong(insertIdL.getText());
            String NumeL = insertNumeL.getText();
            String PrenumeL = insertPrenumeL.getText();
            loggedLehrer = controller.lehrers.findOne(idL);
            try {
                sceneThree(lehrerStage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        VBox myVboxL = new VBox();
        myVboxL.setSpacing(10);
        myVboxL.setLayoutX(300);
        myVboxL.setLayoutY(250);
        myVboxL.setAlignment(Pos.CENTER);
        myVboxL.getChildren().addAll(insertIdL, insertNumeL, insertPrenumeL, loginL);
        ((Group) lehrerScene.getRoot()).getChildren().addAll(myVboxL);

        lehrerStage.setScene(lehrerScene);
        lehrerStage.show();

    }

    public Scene sceneTwo(Stage primaryStage) throws IOException {
        Button b = new Button();
        TextField idKurs = new TextField();
        ((Group) sc.getRoot()).getChildren().clear();
        kurse = (List<Kurs>) controller.alleKurse();

        Button b1 = new Button();
        Button b2 = new Button();
        Button b3 = new Button();
        Button b4 = new Button();
        Button b5 = new Button();
        b1.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;");
        b2.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;");
        b3.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;");
        b4.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;");
        b5.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;");
        b1.setText("Platz");
        b2.setText("Alle Kurse");
        b3.setText("Anmelden");
        b4.setText("Anzahl Kredit sehen");
        b5.setText("Sort Kurse");
        b2.setOnAction(e -> {
            ((Group) sc.getRoot()).getChildren().remove(b);
            ((Group) sc.getRoot()).getChildren().remove(idKurs);
            labelKredits.setText("");
            myListView.getItems().clear();
            try {
               observKurs();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        b1.setOnAction(e -> {
            ((Group) sc.getRoot()).getChildren().remove(b);
            ((Group) sc.getRoot()).getChildren().remove(idKurs);
            labelKredits.setText("");
            myListView.getItems().clear();
            List<Kurs> verfug = null;
            try {
                verfug = controller.verfugbarAnzeigen();
                ObservableList<Kurs> allePlatz = FXCollections.observableArrayList(verfug);
                myListView.setItems(allePlatz);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        b4.setOnAction(e -> {
            ((Group) sc.getRoot()).getChildren().remove(b);
            ((Group) sc.getRoot()).getChildren().remove(idKurs);
            loggedStudent=controller.students.findOne(loggedStudent.getStudentId());
            labelKredits.setText("Kredits: "+String.valueOf(loggedStudent.getTotalCredits()));


        });
        b3.setOnAction(e -> {
            labelKredits.setText("");
            idKurs.setPromptText("Id Kurs");
            idKurs.setLayoutY(70);
            idKurs.setLayoutX(40);
            b.setText("Submit");
            b.setLayoutY(70);
            b.setLayoutX(250);
            b.setStyle("-fx-font-size:12; -fx-background-color: #c3c4c4, linear-gradient(#d6d6d6 50%, white 100%)," +
                    "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);-fx-background-radius: 30;-fx-background-insets: 0,1,1;-fx-text-fill: black;");
            ((Group) sc.getRoot()).getChildren().addAll(b, idKurs);
            b.setOnAction(f -> {
                long idK = Long.parseLong(idKurs.getText());
                kursToEnroll = controller.kurse.findOne(idK);
                try {
                    controller.anmelden(kursToEnroll, loggedStudent);
                    if (loggedLehrer != null) {
                        observ();
                    }
                    observStud();
                } catch (NoPlaceException | IOException ex) {
                    ex.printStackTrace();
                }
                myListView.getItems().clear();


            });


        });
        b5.setOnAction(e->{

            try {
                List<Kurs>kurs = controller.sortKurse();
                ObservableList<Kurs> sortKurs = FXCollections.observableArrayList(kurs);
                myListView.setItems(sortKurs);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });


        VBox layout2 = new VBox();
        layout2.setSpacing(60);
        HBox layoutButton=new HBox();
        layoutButton.setSpacing(20);
        layoutButton.getChildren().addAll(b1,b2,b3,b4,b5);
        labelKredits.setLayoutY(70);
        labelKredits.setLayoutX(150);
        ((Group) sc.getRoot()).getChildren().addAll(labelKredits);
        myListView.setPrefSize(270,300);
        myListView.setLayoutX(40);
        myListView.setLayoutY(120);
        ((Group) sc.getRoot()).getChildren().addAll(myListView);
        layout2.getChildren().addAll(layoutButton);
        layout2.setAlignment(Pos.CENTER);
        layout2.setLayoutX(30);
        layout2.setLayoutY(30);

        ((Group) sc.getRoot()).getChildren().addAll(layout2);
        primaryStage.setScene(sc);
        return sc;

    }

    /*************************/
    public Scene sceneThree(Stage lehrerStage) throws IOException {
        Button b = new Button();
        TextField idKurs = new TextField();
        ListView<Kurs> kursefurLehrer = new ListView<>();


        ((Group) lehrerScene.getRoot()).getChildren().clear();
        Button b1 = new Button();
        Button b2 = new Button();
        Button b3 = new Button();
        b1.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;");
        b2.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;");
        b3.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;");
        b1.setText("Kurs loschen");
        b2.setText("Kredite andern");
        b3.setText("Filter Students");

        b1.setOnAction(e -> {
            ((Group) lehrerScene.getRoot()).getChildren().remove(b);
            ((Group) lehrerScene.getRoot()).getChildren().remove(idKurs);
            ((Group) lehrerScene.getRoot()).getChildren().remove(kredit);
            idKurs.setPromptText("Id Kurs");
            idKurs.setLayoutY(70);
            idKurs.setLayoutX(40);
            b.setText("Submit");
            b.setLayoutY(70);
            b.setLayoutX(250);
            b.setStyle("-fx-font-size:12; -fx-background-color: #c3c4c4, linear-gradient(#d6d6d6 50%, white 100%)," +
                    "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);-fx-background-radius: 30;-fx-background-insets: 0,1,1;-fx-text-fill: black;");
            try {
                lsitkurseforL = kursVonLehrer(loggedLehrer);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ObservableList<Kurs> kursForL = FXCollections.observableArrayList(lsitkurseforL);
            ((Group) lehrerScene.getRoot()).getChildren().addAll(b, idKurs);
            kursefurLehrer.setItems(kursForL);
            b.setOnAction(f -> {
                ((Group) lehrerScene.getRoot()).getChildren().remove(b);
                ((Group) lehrerScene.getRoot()).getChildren().remove(idKurs);
                try {
                    long idK = Long.parseLong(idKurs.getText());
                    kursToDelete = controller.kurse.findOne(idK);
                    controller.removeKursfurLehrer(loggedLehrer, kursToDelete);
                    lsitkurseforL = kursVonLehrer(loggedLehrer);
                    ObservableList<Kurs> kursForL2 = FXCollections.observableArrayList(lsitkurseforL);
                    kursefurLehrer.setItems(kursForL2);
                    observ();
                    observKurs();
                    if (loggedStudent != null) {
                        observStud();
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        });
        b2.setOnAction(e -> {
            ((Group) lehrerScene.getRoot()).getChildren().remove(b);
            ((Group) lehrerScene.getRoot()).getChildren().remove(idKurs);
            ((Group) lehrerScene.getRoot()).getChildren().remove(kredit);
            idKurs.setPromptText("Id Kurs");
            kredit.setPromptText("Kredits");
            idKurs.setLayoutY(70);
            idKurs.setLayoutX(40);
            b.setText("Submit");
            b.setLayoutY(70);
            b.setLayoutX(450);
            kredit.setLayoutX(250);
            kredit.setLayoutY(70);
            b.setStyle("-fx-font-size:12; -fx-background-color: #c3c4c4, linear-gradient(#d6d6d6 50%, white 100%)," +
                    "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);-fx-background-radius: 30;-fx-background-insets: 0,1,1;-fx-text-fill: black;");
            try {
                lsitkurseforL = kursVonLehrer(loggedLehrer);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ObservableList<Kurs> kursForL = FXCollections.observableArrayList(lsitkurseforL);
            ((Group) lehrerScene.getRoot()).getChildren().addAll(b, idKurs, kredit);
            kursefurLehrer.setItems(kursForL);
            b.setOnAction(f -> {
                ((Group) lehrerScene.getRoot()).getChildren().remove(b);
                ((Group) lehrerScene.getRoot()).getChildren().remove(idKurs);
                ((Group) lehrerScene.getRoot()).getChildren().remove(kredit);
                try {
                    long idK = Long.parseLong(idKurs.getText());
                    int credit = Integer.parseInt(kredit.getText());
                    Kurs k;
                    k = controller.kurse.findOne(idK);
                    k.setCredits(credit);
                    System.out.println(k);
                    kursToUpdate = k;
                    controller.neuAnzahlCredits(kursToUpdate);

                    lsitkurseforL = kursVonLehrer(loggedLehrer);
                    System.out.println(lsitkurseforL);
                    ObservableList<Kurs> kursForL2 = FXCollections.observableArrayList(lsitkurseforL);
                    System.out.println(kursForL2);
                    kursefurLehrer.setItems(kursForL2);
                    observ();
                    observKurs();
                    if (loggedStudent != null) {
                        observStud();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });


        });
        b3.setOnAction(e->{
            try {
                 List<Student> studentfilter=controller.filtriereStudents();
                ObservableList<Student> filter = FXCollections.observableArrayList(studentfilter);
                 enrolledStud.setItems(filter);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        observ();


        VBox layout3 = new VBox();
        layout3.setSpacing(10);
        HBox layoutButtons=new HBox();
        layoutButtons.setSpacing(20);
        layoutButtons.getChildren().addAll(b1,b2,b3);
        layout3.getChildren().addAll(layoutButtons);
        layout3.setAlignment(Pos.CENTER);
        layout3.setLayoutX(30);
        layout3.setLayoutY(30);
        kursefurLehrer.setPrefSize(270,300);
        kursefurLehrer.setLayoutY(120);
        kursefurLehrer.setLayoutX(30);
        ((Group) lehrerScene.getRoot()).getChildren().addAll(layout3);
        ((Group) lehrerScene.getRoot()).getChildren().addAll(kursefurLehrer);
        lehrerStage.setScene(lehrerScene);

        return lehrerScene;
    }

    public List<Kurs> kursVonLehrer(Lehrer l) throws IOException {
        List<Kurs> k = new ArrayList<>();
        k = (List<Kurs>) controller.kurse.findAll();
        System.out.println(k);
        List<Kurs> list = new ArrayList<>();
        for (Kurs k1 : k) {
            if (k1.getLehrer().getId() == l.getId())
                list.add(k1);

        }
        return list;
    }

    public List<Student> studeEnrolledLehrerKurs(Lehrer l) throws IOException {
        List<Kurs> kursL = kursVonLehrer(l);
        List<Student> allS = (List<Student>) controller.students.findAll();
        List<Student> listStudentes = new ArrayList<>();
        for (Student s : allS) {
            for (Kurs k : kursL) {

                for (Kurs k2 : s.getEnrolledCourses()) {
                    if (k2.getKursId() == k.getKursId()) {
                        listStudentes.add(s);

                    }
                }


            }
        }

        Set<Student> set = new HashSet<>(listStudentes);
        listStudentes.clear();
        listStudentes.addAll(set);
        return listStudentes;

    }

    public void observ() throws IOException {
        ((Group) lehrerScene.getRoot()).getChildren().remove(enrolledStud);
        List<Student> listS = studeEnrolledLehrerKurs(loggedLehrer);
        ObservableList<Student> sutdList = FXCollections.observableArrayList(listS);
        enrolledStud.setItems(sutdList);
        enrolledStud.setLayoutX(320);
        enrolledStud.setPrefWidth(400);
        enrolledStud.setPrefSize(410,300);
        enrolledStud.setLayoutY(120);
        ((Group) lehrerScene.getRoot()).getChildren().addAll(enrolledStud);
    }

    public void observStud() {
        ((Group) sc.getRoot()).getChildren().remove(myStudentView);
        Student s = controller.students.findOne(loggedStudent.getStudentId());
        List<Student> st = new ArrayList<>();
        st.add(s);
        ObservableList<Student> alleSt = FXCollections.observableArrayList(st);
        myStudentView.setItems(alleSt);
        myStudentView.setLayoutX(350);
        myStudentView.setPrefWidth(400);
        myStudentView.setPrefSize(380,300);
        myStudentView.setLayoutY(120);
        ((Group) sc.getRoot()).getChildren().addAll(myStudentView);

    }
    public void observKurs() throws IOException {
        kurse = (List<Kurs>) controller.alleKurse();
        ObservableList<Kurs> allkurs = FXCollections.observableArrayList(kurse);
        myListView.setItems(allkurs);

    }




    /**************************************************************************************************************/
    public Student addStudent() {
        Scanner in = new Scanner(System.in);
        System.out.print("ID: ");
        String id = in.next();
        System.out.print("VorName: ");
        String VorName = in.next();
        System.out.print("NachName: ");
        String NachName = in.next();
        Student student = new Student(Long.parseLong(id), VorName, NachName, 0, null);
        return student;
    }

    public Lehrer getLehrer() throws IOException {
        List<Lehrer> lehrern = (List<Lehrer>) controller.lehrers.findAll();
        for (Lehrer l : lehrern) {
            System.out.println(l);
        }
        Scanner in = new Scanner(System.in);
        System.out.print("ID von Lehrer: ");
        Long id = in.nextLong();
        Lehrer lehrer = controller.lehrers.findOne(id);
        return lehrer;
    }

    public Student getStudent() throws IOException {
        List<Student> stud = (List<Student>) controller.students.findAll();
        for (Student s : stud) {
            System.out.println(s);
        }
        Scanner in = new Scanner(System.in);
        System.out.print("ID von Student: ");
        Long id = in.nextLong();
        Student student = controller.students.findOne(id);
        return student;
    }

    public Kurs getKurs() throws IOException {
        List<Kurs> courses = (List<Kurs>) controller.kurse.findAll();
        for (Kurs course : courses) {
            System.out.println(course);
        }
        System.out.print("Kurs ID: ");
        Scanner in = new Scanner(System.in);
        Long id = in.nextLong();
        Kurs course = controller.kurse.findOne(id);
        return course;
    }

    public Kurs readKurs() throws IOException {
        Scanner in = new Scanner(System.in);
        Kurs k = getKurs();
        System.out.print("Credits: ");
        int newCredits = in.nextInt();
        Kurs k2 = new Kurs(k.getKursId(), k.getName(), k.getLehrer(), k.getMaxEnrollment(), newCredits);
        return k2;
    }


    public void menu() throws Exception {
        while (true) {
            System.out.println(" Option: 0  Exit");
            System.out.println(" Option: 1  Studenten, sich für einen bestimmten Kurs anzumelden");
            System.out.println(" Option: 2  Kurse fur denen Platze verfugbar sind ");
            System.out.println(" Option: 3  Zeigt Studenten die sich fur einen bestimmten Kurs angemeldet haben");
            System.out.println(" Option: 4  Alle Kurse Anzeingen");
            System.out.println(" Option: 5  Student wahlt ein Kurs");
            System.out.println(" Option: 6  Kredite fur ein Kurs konnen geandert werden");
            System.out.println(" Option: 7  Lehrer loscht einer seiner Kurse");
            System.out.println(" Option: 8  Sortiert die Kurse nach Anzahl der Kredite");
            System.out.println(" Option: 9  Filtriert die Studenten die weniger als 10 Kredite hat");
            Scanner scanner = new Scanner(System.in);
            int command = Integer.MIN_VALUE;
            do {
                System.out.print("Gibt eine Option ein: ");
                while (!scanner.hasNextInt()) {
                    String input = scanner.next();
                    System.out.printf("\"%s\" is not a valid number.\n", input);
                }
                command = scanner.nextInt();
            } while (command == Integer.MIN_VALUE);
            if (command < 0 || command > 9)
                continue;
            if (command == 1) {

                Student s1 = getStudent();
                Kurs k1 = getKurs();
                if (k1 != null && s1 != null) {
                    try {
                        controller.anmelden(k1, s1);
                    } catch (NoPlaceException ex) {
                        ex.mymessage();
                    }
                } else
                    System.out.println("Die Id's gibt es nicht");

                List<Student> stud = (List<Student>) controller.students.findAll();
                for (Student s : stud) {
                    System.out.println(s);
                }

            }
            if (command == 2) {
                List<Kurs> kurs = controller.verfugbarAnzeigen();
                for (Kurs course : kurs) {
                    System.out.println(course);
                }
                System.out.println("\n Anzahl Kurse: " + controller.verfugbarAnzeigen().size());
            }
            if (command == 3) {
                Kurs k1 = getKurs();

                if (k1 != null) {
                    List<Student> student = controller.anzeigeBestimmtenKurs(k1);
                    if (student == null) {
                        System.out.println("Keine Studenten sind eingeschrieben");
                    }
                    for (Student s : student) {
                        System.out.println(s);
                    }
                } else
                    System.out.println("Der Id gibt es nicht");
            }
            if (command == 4) {
                List<Kurs> kurs = (List<Kurs>) controller.alleKurse();
                for (Kurs course : kurs) {
                    System.out.println(course);
                }
            }
            if (command == 5) {

                Student s1 = getStudent();
                Kurs k1 = getKurs();
                if (k1 != null && s1 != null) {
                    try {
                        controller.kursEinfugen(s1, k1);
                    } catch (MoreCreditsException ex) {
                        ex.mymessage();
                    }
                } else
                    System.out.println("Die Id's gibt es nicht");


                List<Student> stud = (List<Student>) controller.students.findAll();
                for (Student s : stud) {
                    System.out.println(s);
                }

            }
            if (command == 6) {

                Kurs k1 = readKurs();
                if (k1 != null)
                    controller.neuAnzahlCredits(k1);
                else
                    System.out.println("Der Id gibt es nicht");

                List<Kurs> courses = (List<Kurs>) controller.kurse.findAll();
                for (Kurs course : courses) {
                    System.out.println(course);
                }

                List<Student> stud = (List<Student>) controller.students.findAll();
                for (Student s : stud) {
                    System.out.println(s);
                }

            }
            if (command == 7) {

                Lehrer l1 = getLehrer();
                Kurs k1 = getKurs();
                if (k1 != null && l1 != null) {
                    controller.removeKursfurLehrer(l1, k1);
                    List<Kurs> courses = (List<Kurs>) controller.kurse.findAll();
                    for (Kurs course : courses) {
                        System.out.println(course);
                    }

                    List<Student> stud = (List<Student>) controller.students.findAll();
                    for (Student s : stud) {
                        System.out.println(s);
                    }
                } else
                    System.out.println("Die Id's gibt es nicht");
            }
            if (command == 8) {
                List<Kurs> courses = controller.sortKurse();
                for (Kurs course : courses) {
                    System.out.println(course);
                }

            }
            if (command == 9) {
                List<Student> stud = controller.filtriereStudents();
                for (Student s : stud) {
                    System.out.println(s);
                }
            }

            if (command == 0) {

                controller.kurse.findAll();
                controller.students.findAll();
                controller.lehrers.findAll();
                System.exit(0);
            }

        }
    }
}
