package sample.repository;


import sample.model.JDBCutil;
import sample.model.Lehrer;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcLehrerRepo implements ICrudRepository<Lehrer> {
    private List<Lehrer> lehrerList;
    private Connection conection;
    private JDBCutil jdbcUtil;
    public JdbcLehrerRepo() throws SQLException, IOException {
        List<Lehrer>lehrerList=new ArrayList<>();
        jdbcUtil=new JDBCutil();
        conection=jdbcUtil.getConnection();
        // conection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/example", "postgres", "1234");
        lehrerList= (List<Lehrer>) findAll();
        this.lehrerList = lehrerList;
        /*conection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/example", "postgres", "1234");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/example", "postgres", "1234")) {


            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.\"Lehrer\"");
            while (resultSet.next()) {
                String s1 = resultSet.getString(1);
                String s2 = resultSet.getString(3);
                String s3 = resultSet.getString(2);
                Lehrer l = new Lehrer(Long.parseLong(s1), s2, s3);
                lehrerList.add(l);

            }
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        this.lehrerList = lehrerList;*/

    }

    public List<Lehrer> getLehrerList() {
        return lehrerList;
    }

    /**
     * Die Methode gibt der Leher mit der gegebene Id zuruck
     *
     * @param id -the id of the entity to be returned id must not be null
     * @return -der Lehrer welcher der  gegebenen id hat
     * - null wennes keinen gibt
     */
    @Override
    public Lehrer findOne(Long id) {
        /** if (id != null) {
         List<Lehrer> list=lehrerList.stream().filter(l->l.getId()==id).collect(Collectors.toList());
         /*for (Lehrer l : lehrerList) {
         if (l.getId() == id)
         return l;
         }//
         for(Lehrer leh:list) {
         return leh;
         }
         }
         return null;**/
        Statement stmt;
        ResultSet rs;
        //ResultSet rs2;
        Lehrer l=null;
        try{
            stmt=conection.createStatement();
            rs=stmt.executeQuery(String.format("SELECT * FROM public.\"Lehrer\" where \"Id\"=%d;",id));
            if(!rs.next()){
                return null;
            }else{
                String s1 = rs.getString(1);
                String s2 = rs.getString(3);
                String s3 = rs.getString(2);
                l = new Lehrer(Long.parseLong(s1), s2, s3);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return l;
    }



    /**
     * Die Methode gibt zuruck die Liste von Lehrer
     *
     * @return Die Liste von Lehrer
     */
    @Override
    public Iterable findAll() throws IOException {
        //insertIntoTable();
        //return lehrerList;
        List<Lehrer>all=new ArrayList<>();
        Statement stmt;
        ResultSet rs;
        ResultSet rs2;
        try{
            stmt=conection.createStatement();
            rs=stmt.executeQuery("Select * from public. \"Lehrer\"");
            //System.out.println(rs);
            if(rs==null){
                return null;
            }else{
                while(rs.next()){
                    String s1 = rs.getString(1);
                    String s2 = rs.getString(3);
                    String s3 = rs.getString(2);
                    Lehrer  l = new Lehrer(Long.parseLong(s1), s2, s3);
                    //System.out.println(l);
                    all.add(l);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return all;
    }


    /**
     * Die Methode fugt eienen Lehrer hinzu
     *
     * @param entity entity must be not null
     * @return -null wenn der Lehrer hinzugefugt wird
     * -der Lehrer wenn einer mit dem selbe id schon gibt
     */
    @Override
    public Lehrer save(Lehrer entity) {

        /*if (entity != null) {
            Lehrer lehrer = findOne(entity.getId());
            if (lehrer != null)
                return lehrer;
            else {
                lehrerList.add(entity);
            }
        }
        return null;*/
        Statement stmt;
        ResultSet rs;
        ResultSet rs2;
        try{
            stmt = conection.createStatement();
            rs = stmt.executeQuery("select * from \"Lehrer\"");
            if (!rs.next()) {
                stmt.execute(String.format("insert into \"Lehrer\" values('%d','%s','%s');", entity.getId(), entity.getNachName(), entity.getVorname()));

            }else{
                while(rs.next()){
                    if (rs.getInt(1) == entity.getId()){
                        return null;
                    }

                }
                stmt.execute(String.format("insert into \"Lehrer\" values('%d','%s','%s');", entity.getId(), entity.getNachName(), entity.getVorname()));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return entity;

    }

    /**
     * Die Mthoder loscht ein Lehrer aus der Liste
     *
     * @param id id must be not null
     * @return -null wenn der Lehrer icht gibt
     * - der Lehrer der geloscht wurde
     */
    @Override
    public Lehrer delete(Long id) {
        /*if (id != null) {
            Lehrer lehrer = findOne(id);
            if (lehrer != null) {
                lehrerList.remove(lehrer);
                return lehrer;
            }
        }
        return null;
         */
        if(findOne(id) == null){
            return null;
        }
        Statement stmt;
        Lehrer l = findOne(id);
        try {
            stmt = conection.createStatement();
            stmt.execute(String.format("delete from \"Lehrer\" where \"Id\" = %d;", id));
            // stmt.execute(String.format("delete from asociereteachercurs where idteacher = %d;", id));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return l;

    }


    @Override
    public Lehrer update(Lehrer entity) {
        /*if (entity != null) {
            Lehrer lehrer = findOne(entity.getId());
            if (lehrer != null) {
                lehrer.setNachName(entity.getNachName());
                lehrer.setVorname(entity.getVorname());
                lehrer.setCourses(entity.getCourses());
                return null;
            } else {
                return entity;
            }
        }
        return null;*/
        if(findOne(entity.getId()) == null){
            return null;
        }
        else{
            delete(entity.getId());
            save(entity);
        }
        return entity;

    }

    public void insertIntoTable(){
        String url = "jdbc:postgresql://localhost:5432/example";
        String user = "postgres";
        String password = "1234";
        int ok=0;
        for( Lehrer l : lehrerList) {
            long id1 = l.getId();
            int id = (int) id1;
            String nume = l.getNachName();
            String prenume = l.getVorname();


            String query1 = "DELETE FROM \"public\".\"Lehrer\"";
            String query = "INSERT INTO public.\"Lehrer\"(\n\"Id\", \"Name\", \"Vorname\")VALUES (?, ?, ?)";


            try (Connection con = DriverManager.getConnection(url, user, password);
                 PreparedStatement pst = con.prepareStatement(query); PreparedStatement pst2 = con.prepareStatement(query1)) {
                if(ok==0){
                    pst2.executeUpdate();
                    ok=1;
                }

                //Array a = con.createArrayOf("varchar", new Object[]{id});
                pst.setInt(1, id);
                pst.setString(2, nume);
                pst.setString(3, prenume);
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




