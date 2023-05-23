package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class DatabaseConnector {
    private static final String PROP_FILE = "Config/database.settings";
    private final SQLServerDataSource ds;
    private static DatabaseConnector instance = null;

    /**
     * Constructor for the singleton class "DatabaseConnector".
     * @throws IOException
     */
   private DatabaseConnector() throws IOException {
       Properties databaseProperties = new Properties();
       databaseProperties.load(new FileInputStream(PROP_FILE));

       String server = databaseProperties.getProperty("Server");
       String database = databaseProperties.getProperty("Database");
       String user = databaseProperties.getProperty("User");
       String password = databaseProperties.getProperty("Password");

       ds = new SQLServerDataSource();
       ds.setServerName(server);
       ds.setDatabaseName(database);
       ds.setUser(user);
       ds.setPassword(password);
       ds.setTrustServerCertificate(true);
   }

   /**
    * Creating or getting the instance of the class.
    * @return
    * @throws IOException
    */
    public static DatabaseConnector getInstance() throws IOException {
        if (instance == null){
            instance = new DatabaseConnector();
        }
        return instance;
    }

    /**
     * Get the connection of the database.
     * @return
     * @throws SQLServerException
     */
    public Connection getConnection() throws SQLServerException {return ds.getConnection();}
}