package sample.repository;

import sample.model.JDBCutil;
import sample.model.Kurs;
import sample.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcStudentRepo implements ICrudRepository<Student> {
    private List<Student> studentList;
    private Connection conection;
    private JDBCutil jdbcUtil;
    JdbcKursRepo r2 = new JdbcKursRepo();

    public JdbcStudentRepo() throws SQLException {
        jdbcUtil=new JDBCutil();
        conection=jdbcUtil.getConnection();
        // conection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/example", "postgres", "1234");
        List<Student> studentList = new ArrayList<>();
        studentList= (List<Student>) findAll();
        this.studentList = studentList;

        /*JdbcKursRepo r = new JdbcKursRepo();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/example", "postgres", "1234")) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.\"Student\"");
           /* while (resultSet.next()) {
                String s1 = resultSet.getString(1);
                String s2 = resultSet.getString(2);
                String s3 = resultSet.getString(3);
                String s4 = resultSet.getString(4);
                String s5 = resultSet.getString(5);

                List<Kurs> enrolledKurs= new ArrayList();
                if(s5!=null) {
                    if(s5.length()>6)
                    {
                        s5=s5.substring(1,s5.length()-1);
                    }
                    String s6=s5.substring(1,s5.length()-1);
                    String[] split = s6.split(",");


                    for (int i=0;i<split.length;i++){
                        Kurs k1 = r.findOne(Long.parseLong(split[i]));
                        if(k1!=null){
                            enrolledKurs.add(k1);
                        }
                    }
                }

                    Student s = new Student (Long.parseLong(s1),s2,s3,Integer.parseInt(s4),enrolledKurs);
                    studentList.add(s);
                    for(Kurs k : enrolledKurs){
                        List<Student> enrolledStudents = k.getStudentsEnrolled();

                        enrolledStudents.add(s);
                        k.setStudentsEnrolled(enrolledStudents);
                    }
                }
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }

        }*/

    }


    /**
     * Die Methode gibt der Student mit der gegebene Id zuruck
     *
     * @param id -the id of the entity to be returned id must not be null
     * @return -der Student welcher der  gegebenen id hat
     * - null wennes keinen gibt
     */
    @Override
    public Student findOne(Long id) {
      /*  for (Student s : studentList) {
            if (s.getStudentId() == id)
                return s;
        }//
        List<Student> list=studentList.stream().filter(s->s.getStudentId()==id).collect(Collectors.toList());
        for(Student s:list)
        {
            return s;
        }
        return null;*/
        Statement stmt;
        Statement stmt2;
        ResultSet rs;
        ResultSet rs2;
        Student s = null;
        try {
            stmt = conection.createStatement();
            stmt2 = conection.createStatement();
            rs = stmt.executeQuery(String.format("select * from \"Student\" where \"Id\" = %d ;", id));
            if (!rs.next()) {
                return null;
            } else {
                rs2 = stmt2.executeQuery(String.format("select idkurs from studentkurs where idstudent = %d ;", id));
                List<Integer> kurs = new ArrayList<>();

                while (rs2.next()) {
                    kurs.add(rs2.getInt(1));
                }
                List<Kurs> enrolledKurs = new ArrayList<>();
                for (Integer i : kurs) {
                    Kurs k = r2.findOne(Long.valueOf(i));
                    enrolledKurs.add(k);
                }
                String s1 = rs.getString(1);
                String s2 = rs.getString(2);
                String s3 = rs.getString(3);
                String s4 = rs.getString(4);
                s = new Student(Long.parseLong(s1), s2, s3, Integer.parseInt(s4), enrolledKurs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * Die Methode gibt zuruck die Liste von Studenten
     *
     * @return Die Liste von Studenten
     */
    @Override
    public Iterable findAll() {

        //return studentList;
        List<Student> all = new ArrayList<>();
        Statement stmt;
        Statement stmt2;
        ResultSet rs;
        ResultSet rs2;

        try {
            stmt = conection.createStatement();
            stmt2 = conection.createStatement();
            rs = stmt.executeQuery("select * from \"Student\"");
            if (!rs.next()) {
                return null;
            } else {
                do {
                    Student s;
                    rs2 = stmt2.executeQuery(String.format("select idkurs from studentkurs where idstudent = %d ;", rs.getInt(1)));
                    List<Integer> kurs = new ArrayList<>();

                    while (rs2.next()) {
                        kurs.add(rs2.getInt(1));
                    }

                    List<Kurs> enrolledKurs = new ArrayList<>();
                    for (Integer i : kurs) {
                        Kurs k = r2.findOne(Long.valueOf(i));
                        enrolledKurs.add(k);
                    }
                    String s1 = rs.getString(1);
                    String s2 = rs.getString(2);
                    String s3 = rs.getString(3);
                    String s4 = rs.getString(4);
                    s = new Student(Long.parseLong(s1), s2, s3, Integer.parseInt(s4), enrolledKurs);
                    all.add(s);
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }

    /**
     * Die Methode fugt eienen Student hinzu
     *
     * @param entity entity must be not null
     * @return -null wenn der Student hinzugefugt wird
     * -der Student wenn einer mit dem selbe id schon gibt
     */
    @Override
    public Student save(Student entity) {
        /*insertIntoTable();
        Student student = findOne(entity.getStudentId());
        if (student != null)
            return student;
        else {
            studentList.add(entity);
            return null;
        }*/
        Statement stmt;
        ResultSet rs;
        ResultSet rs2;
        try {
            stmt = conection.createStatement();
            rs = stmt.executeQuery("select * from \"Student\"");
            if (!rs.next()) {
                stmt.execute(String.format("insert into \"Student\" values('%d','%s','%s','%d');", entity.getStudentId(), entity.getNachName(), entity.getVorname(), entity.getTotalCredits()));
                for (Kurs k : entity.getEnrolledCourses()) {
                    stmt.execute(String.format("insert into studentkurs values(%d,%d);", entity.getStudentId(), k.getKursId()));
                }
            } else {
                while (rs.next()) {
                    if (rs.getInt(1) == entity.getStudentId()) {
                        return null;
                    }
                }
                stmt.execute(String.format("insert into \"Student\" values('%d','%s','%s','%d');", entity.getStudentId(), entity.getNachName(), entity.getVorname(), entity.getTotalCredits()));
                for (Kurs k : entity.getEnrolledCourses()) {
                    stmt.execute(String.format("insert into studentkurs values(%d,%d);", entity.getStudentId(), k.getKursId()));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }


    /**
     * Die Mthoder loscht ein Student aus der Liste
     *
     * @param id id must be not null
     * @return -null wenn der Student icht gibt
     * - der Student der geloscht wurde
     */
    @Override
    public Student delete (Long id){
            /*Student student = findOne(id);
            if (student != null) {
                studentList.remove(student);
                return student;
            }
            return null;*/
        if(findOne(id) == null){
            return null;
        }
        Statement stmt;
        Student s = findOne(id);
        try {
            stmt = conection.createStatement();
            stmt.execute(String.format("delete from \"Student\" where \"Id\" = %d;", id));
            stmt.execute(String.format("delete from studentkurs where idstudent = %d;", id));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }
    /** public Student delete(int id) {
     if(findOne(id) == null){
     return null;
     }
     Statement stmt;
     Student s = findOne(id);
     try {
     stmt = con.createStatement();
     stmt.execute(String.format("delete from student where studentid = %d;", id));
     stmt.execute(String.format("delete from asocierestudentcurs where idstudent = %d;", id));


     } catch (SQLException e) {
     e.printStackTrace();
     }
     return s;

     }***/
    /**
     * Die Methode macht update fur einen Student
     *
     * @param entity entity must not be null
     * @return -null der Student wurde updated
     * - der Student  wenn es in der Liste keiner mit dem Id ist.
     */
    @Override
    public Student update (Student entity){
        /*Student student = findOne(entity.getStudentId());
        if (student != null) {
            student.setVorname(entity.getVorname());
            student.setNachName(entity.getNachName());
            student.setTotalCredits(entity.getTotalCredits());
            student.setEnrolledCourses(entity.getEnrolledCourses());
            return null;
        } else {
            return entity;
        }*/
        if (findOne(entity.getStudentId()) == null) {
            return null;
        } else {
            delete(entity.getStudentId());
            save(entity);
        }
        return entity;
    }

    public void insertIntoTable () {
        String url = "jdbc:postgresql://localhost:5432/example";
        String user = "postgres";
        String password = "1234";
        int ok = 0;
        for (Student s : studentList) {
            int id = (int) s.getStudentId();
            String name = s.getNachName();
            String vorname = s.getVorname();
            int kredit = s.getTotalCredits();
            List<Kurs> enrolledKurs = new ArrayList();
            enrolledKurs = s.getEnrolledCourses();
            List<String> l = new ArrayList();
            String v = "";
            int ok2 = 0;
            for (Kurs k : enrolledKurs) {
                if (ok2 == 0) {
                    v = v + Long.toString(k.getKursId());
                    ok2 = 1;
                } else
                    v = v + "," + Long.toString(k.getKursId());


            }


            String query1 = "DELETE FROM \"public\".\"Student\"";
            String query = "INSERT INTO public.\"Student\"(\n\"Id\", \"Name\", \"Vorname\",\"Kredit\",\"EnrolledKurs\")VALUES (?, ?, ?,?,?)";


            try (Connection con = DriverManager.getConnection(url, user, password);
                 PreparedStatement pst = con.prepareStatement(query); PreparedStatement pst2 = con.prepareStatement(query1)) {
                if (ok == 0) {
                    pst2.executeUpdate();
                    ok = 1;
                }
                //System.out.println(enrolledKurs);
                if (!enrolledKurs.isEmpty()) {
                    Array a = con.createArrayOf("varchar", new Object[]{v});
                    pst.setArray(5, a);
                } else
                    pst.setArray(5, null);
                pst.setInt(1, id);
                pst.setString(2, name);
                pst.setString(3, vorname);
                pst.setInt(4, kredit);

                //pst.setString(3, );
                //pst.setArray(3,id);
                pst.executeUpdate();

            } catch (SQLException ex1) {

                Logger lgr = Logger.getLogger(JdbcLehrerRepo.class.getName());
                lgr.log(Level.SEVERE, ex1.getMessage(), ex1);
            }
        }
    }


}
