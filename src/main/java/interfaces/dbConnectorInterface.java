package interfaces;

import java.sql.Connection;

/**
 *
 * @author Camila & Erik
 */
public interface dbConnectorInterface {
    public Connection getConnection();
    public void closeConnection();
}
