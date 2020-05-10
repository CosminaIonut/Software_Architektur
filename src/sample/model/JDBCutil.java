package sample.model;//package lab3.sample.model;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class JDBCutil {



    private String className = "org.postgresql.Driver";
    private String url;
    private String user;
    private String password;
    private String databasename;
    private Connection connection;

    public JDBCutil() {}

    public Connection getConnection() {
        //Load the driver class
        try {
            Class.forName(className);
        } catch (ClassNotFoundException ex) {
            System.out.println("Unable to load the class. Terminating the program");
            System.exit(-1);
        }
        //get the connection
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/geografie", "postgres", "1234");
        } catch (SQLException ex) {
            System.out.println("Error getting connection: " + ex.getMessage());
            System.exit(-1);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            System.exit(-1);
        }
        return null;
    }

    private void getPropertyValues() {
        InputStream inputStream;
        try {
            Properties prop = new Properties();
            String propFileName = "application.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            // get the property value and print it out
            className = prop.getProperty("classname");
            user = prop.getProperty("username");
            password = prop.getProperty("password");
            databasename = prop.getProperty("databasename");
            url = prop.getProperty("url");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
