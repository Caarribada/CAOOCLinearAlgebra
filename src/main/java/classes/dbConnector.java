package classes;

import interfaces.dbConnectorInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Camila & Erik
 */
public class dbConnector implements dbConnectorInterface{
    
     private Connection con = null;

    private String hostName = null;
    private String userName = null;
    private String password = null;
    private String url = null;
    private String jdbcDriver = null;
    private String dataBaseName = null;
    private String dataBasePrefix = null;
    private String dataBasePort = null;
    
     public dbConnector(){
      super();
      
      hostName = "localhost"; //local server
      userName = "root";//database user
      password = ""; //database password
      dataBaseName = "linearsystem"; //local database
      jdbcDriver = "com.mysql.cj.jdbc.Driver";
      dataBasePrefix = "jdbc:mysql://";
      dataBasePort = "3306";
      
      url = dataBasePrefix + hostName + ":" + dataBasePort + "/" + dataBaseName;
  }

    @Override
    public Connection getConnection() {
    try {
        if (con == null) {
          Class.forName(jdbcDriver);
          con = DriverManager.getConnection(url, userName, password);
        } else if (con.isClosed()) {
          con = null;
          return getConnection();
        }
      } catch (ClassNotFoundException | SQLException e) {
          System.out.println("Unable to connect");
          e.printStackTrace();
      }

      return con;
    }

    @Override
    public void closeConnection() {
       if (con != null) {
        try {
          con.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    
}
