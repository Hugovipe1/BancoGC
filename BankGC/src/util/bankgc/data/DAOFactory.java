package bankgc.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {

  private Connection connection;

  public DAOFactory(Connection connection) {
    this.connection = connection;
  }

  public DAOFactory(String url, String user, String password) throws DAOFactoryException {
    try {
      connection = DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
      throw new DAOFactoryException("No se puede conectar a la base de datos: " + e.getMessage());
    }
  }

  public CustomerDAO getCustomerDAO() throws DAOFactoryException {
    throwExceptionIfConnectionIsNull();
    return new CustomerDAOMySql(connection);
  }

  public AccountDAO getAccountDAO() throws DAOFactoryException {
    throwExceptionIfConnectionIsNull();
    return new AccountDAOMySql(connection);
  }
  
  private void throwExceptionIfConnectionIsNull() throws DAOFactoryException {
    if (connection == null) {
      throw new DAOFactoryException("No hay conexión establecida con una fuente de datos");
    }
  }

}
