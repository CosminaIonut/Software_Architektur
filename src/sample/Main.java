package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.controller.Controller;
import sample.repository.JdbcKursRepo;
import sample.repository.JdbcLehrerRepo;
import sample.repository.JdbcStudentRepo;
import sample.view.Ui;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

    }


    public static void main(String[] args) throws Exception {
        //launch(args);
        JdbcLehrerRepo r1=new JdbcLehrerRepo();
        JdbcKursRepo r2=new JdbcKursRepo();
        JdbcStudentRepo r3=new JdbcStudentRepo();
        //Controller sample.controller = new Controller(kl,ks,kr);
        Controller controller2 = new Controller(r1,r3,r2);
        Ui ui =new Ui(controller2);
        Ui u= new Ui();
        u.controller.alleKurse();
        //ui.menu();
        u.main(args);
    }
}
