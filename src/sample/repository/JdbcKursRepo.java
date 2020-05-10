package sample.repository;


import sample.model.JDBCutil;
import sample.model.Kurs;
import sample.model.Lehrer;
import sample.repository.ICrudRepository;
import sample.repository.JdbcLehrerRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcKursRepo implements ICrudRepository<Kurs> {
    private List<Kurs> kursList;
    private Connection conection;
    private JDBCutil jdbcUtil;
    public JdbcKursRepo() throws SQLException {
        List<Kurs> kursList = new ArrayList<>();
        jdbcUtil=new JDBCutil();
        conection=jdbcUtil.getConnection();
        //conection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/example", "postgres", "1234");
        kursList= (List<Kurs>) findAll();
        this.kursList = kursList;
        /*try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/example", "postgres", "1234")) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.\"Kurs\"");
            while (resultSet.next()) {
                String s1 = resultSet.getString(1);
                String s2 = resultSet.getString(2);
                String s3 = resultSet.getString(3);
                String s4 = resultSet.getString(4);
                String s5 = resultSet.getString(5);
                String s6 = resultSet.getString(6);
                String s7 = resultSet.getString(7);
                Lehrer l = new Lehrer(Long.parseLong(s3), s4, s5);
                Kurs k = new Kurs(Long.parseLong(s1), s2, l, Integer.parseInt(s6), Integer.parseInt(s7));
                kursList.add(k);

            }
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        this.kursList = kursList;
*/

    }

    /**
     * Die Methode gibt der Kurs mit der gegebene Id zuruck
     *
     * @param id -the id of the entity to be returned id must not be null
     * @return -der Kurs welcher der  gegebenen id hat
     * - null wennes keinen gibt
     */
    @Override
    public Kurs findOne(Long id) {
       /* for (Kurs k : kursList) {
            if (k.getKursId() == id)
                return k;
        }//
        List<Kurs> list=kursList.stream().filter(k->k.getKursId()==id).collect(Collectors.toList());
        for(Kurs k:list)
        {
            return k;
        }
        return null;*/
        Statement stmt;
        Statement stmt2;
        ResultSet rs;
        ResultSet rs2;
        Kurs k = null;
        try {
            stmt = conection.createStatement();
            stmt2 = conection.createStatement();
            rs = stmt.executeQuery(String.format("SELECT * FROM public.\"Kurs\" where \"Id\"=%d;", id));
            if (!rs.next()) {
                return null;

            } else {
                String s1 = rs.getString(1);
                String s2 = rs.getString(2);
                String s3 = rs.getString(3);
                String s4 = rs.getString(4);
                String s5 = rs.getString(5);
                String s6 = rs.getString(6);
                String s7 = rs.getString(7);
                Lehrer l = new Lehrer(Long.parseLong(s3), s4, s5);
                k = new Kurs(Long.parseLong(s1), s2, l, Integer.parseInt(s6), Integer.parseInt(s7));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return k;

    }

    /**
     * Die Methode gibt zuruck die Liste von Kurs
     *
     * @return Die Liste von Kurs
     */
    @Override
    public Iterable findAll() {
        //insertIntoTable();
        //return kursList;
        List<Kurs> all = new ArrayList<>();
        Statement stmt;
        ResultSet resultSet;
        ResultSet rs2;
        try {
            stmt = conection.createStatement();
            resultSet = stmt.executeQuery("Select * from public. \"Kurs\"");
            //System.out.println(rs);
            if (resultSet == null) {
                return null;
            } else {
                while (resultSet.next()) {
                    String s1 = resultSet.getString(1);
                    String s2 = resultSet.getString(2);
                    String s3 = resultSet.getString(3);
                    String s4 = resultSet.getString(4);
                    String s5 = resultSet.getString(5);
                    String s6 = resultSet.getString(6);
                    String s7 = resultSet.getString(7);
                    Lehrer l = new Lehrer(Long.parseLong(s3), s4, s5);
                    Kurs k = new Kurs(Long.parseLong(s1), s2, l, Integer.parseInt(s6), Integer.parseInt(s7));
                    all.add(k);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(all);
        return all;

    }

    /**
     * Die Methode fugt eienen Kurs hinzu
     *
     * @param entity entity must be not null
     * @return -null wenn der Kurs hinzugefugt wird
     * -der Kurs wenn einer mit dem selbe id schon gibt
     */
    @Override
    public Kurs save(Kurs entity) {
       /* Kurs kurs = findOne(entity.getKursId());
        if (kurs != null)
            return kurs;
        else {
            kursList.add(entity);
            return null;
        }
        */
        Statement stmt;
        ResultSet rs;
        ResultSet rs2;
        try{
            stmt = conection.createStatement();
            rs = stmt.executeQuery("select * from \"Kurs\"");
            if (rs==null) {
                stmt.execute(String.format("insert into \"Kurs\" values('%d','%s','%d','%s','%s','%d','%d');", entity.getKursId(), entity.getName(), entity.getLehrer().getId(),
                        entity.getLehrer().getNachName(),entity.getLehrer().getVorname(),entity.getMaxEnrollment(),entity.getCredits()));

            }else{
                while(rs.next()){
                    if (rs.getInt(1) == entity.getKursId()){
                        return null;
                    }

                }
                stmt.execute(String.format("insert into \"Kurs\" values('%d','%s','%d','%s','%s','%d','%d');", entity.getKursId(), entity.getName(), entity.getLehrer().getId(),
                        entity.getLehrer().getNachName(),entity.getLehrer().getVorname(),entity.getMaxEnrollment(),entity.getCredits()));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * Die Mthoder loscht ein Kurs aus der Liste
     *
     * @param id id must be not null
     * @return -null wenn der Kurs icht gibt
     * - der Kurs der geloscht wurde
     */
    @Override
    public Kurs delete(Long id) {
       /* Kurs kurs = findOne(id);
        if (kurs != null) {
            kursList.remove(kurs);
            return kurs;
        }
        return null;*/
        if(findOne(id) == null){
            return null;
        }
        Statement stmt;
        Kurs k = findOne(id);
        try {
            stmt = conection.createStatement();
            stmt.execute(String.format("delete from \"Kurs\" where \"Id\" = %d;", id));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return k;

    }

    /**
     * Die Methode macht update fur einen Kurs
     *
     * @param entity entity must not be null
     * @return -null der Kurs wurde updated
     * - der Kurs wenn es in der Liste keiner mit dem Id ist.
     */
    @Override
    public Kurs update(Kurs entity) {
        /*Kurs kurs = findOne(entity.getKursId());
        if (kurs != null) {
            kurs.setCredits(entity.getCredits());
            kurs.setLehrer(entity.getLehrer());
            kurs.setMaxEnrollment(entity.getMaxEnrollment());
            kurs.setName(entity.getName());
            kurs.setStudentsEnrolled(entity.getStudentsEnrolled());
            return null;
        } else {
            return entity;
        }*/
        if(findOne(entity.getKursId()) == null){
            return null;
        }
        else{
            delete(entity.getKursId());
            save(entity);
        }
        return entity;
    }

    public void insertIntoTable() {
        String url = "jdbc:postgresql://localhost:5432/example";
        String user = "postgres";
        String password = "1234";
        int ok = 0;
        for (Kurs k : kursList) {
            int id = (int) k.getKursId();
            String name = k.getName();
            int idL = (int) k.getLehrer().getId();
            String nameL = k.getLehrer().getNachName();
            String vornameL = k.getLehrer().getVorname();
            int platz = k.getMaxEnrollment();
            int kredits = k.getCredits();


            String query1 = "DELETE FROM \"public\".\"Kurs\"";
            String query = "INSERT INTO public.\"Kurs\"(\n\"Id\", \"Name\", \"IdL\", \"NameL\", \"VornameL\", \"Platz\",\"Kredit\")VALUES (?, ?, ?, ?, ?, ?, ?)";


            try (Connection con = DriverManager.getConnection(url, user, password);
                 PreparedStatement pst = con.prepareStatement(query); PreparedStatement pst2 = con.prepareStatement(query1)) {
                if (ok == 0) {
                    pst2.executeUpdate();
                    ok = 1;
                }

                pst.setInt(1, id);
                pst.setString(2, name);
                pst.setInt(3, idL);
                pst.setString(4, nameL);
                pst.setString(5, vornameL);
                pst.setInt(6, platz);
                pst.setInt(7, kredits);
                pst.executeUpdate();

            } catch (SQLException ex1) {

                Logger lgr = Logger.getLogger(JdbcLehrerRepo.class.getName());
                lgr.log(Level.SEVERE, ex1.getMessage(), ex1);
            }
        }
    }

}


