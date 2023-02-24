package bankgc.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bankgc.business.Customer;

public class CustomerDAOMySql implements CustomerDAO {

  private Connection connection;

  public CustomerDAOMySql(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void add(Customer customer) throws CustomerDAOException {
    String sql = "INSERT INTO CUSTOMERS (dni, name, address, phone) VALUES ('" + customer.getDni()
        + "','" + customer.getName() + "','" + customer.getAddress() + "','" + customer.getPhone()
        + "')";
    executeUpdate(sql);
  }

  @Override
  public void set(Customer customer) throws CustomerDAOException {
    String sql =
        "UPDATE CUSTOMERS SET name='" + customer.getName() + "',address='" + customer.getAddress()
            + "',phone='" + customer.getPhone() + "' WHERE dni='" + customer.getDni() + "'";
    int rowsUpdated = executeUpdate(sql);
    if (rowsUpdated == 0) {
      throw new CustomerDAOException("No existe ningún cliente con dni " + customer.getDni());
    }
  }

  @Override
  public void remove(String dni) throws CustomerDAOException {
    String sql = "DELETE FROM CUSTOMERS WHERE dni='" + dni + "'";
    int rowsDeleted = executeUpdate(sql);
    if (rowsDeleted == 0) {
      throw new CustomerDAOException("No existe ningún cliente con dni " + dni);
    }
  }

  private int executeUpdate(String sql) throws CustomerDAOException {
    try (Statement statement = connection.createStatement()) {
      return statement.executeUpdate(sql);
    } catch (SQLException e) {
      throw new CustomerDAOException(e);
    }
  }

  @Override
  public Customer get(String dni) throws CustomerDAOException {
    String sql = "SELECT * FROM CUSTOMERS WHERE dni='" + dni + "'";
    try (Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery(sql);) {
      if (!resultset.next()) {
        throw new CustomerDAOException("El DNI " + dni + " no existe en la base de datos");
      }
      return new Customer(dni, resultset.getString("name"), resultset.getString("address"),
          resultset.getString("phone"));
    } catch (SQLException e) {
      throw new CustomerDAOException(e);
    }
  }

  @Override
  public List<Customer> getList() throws CustomerDAOException {
    return getCustomers("SELECT * FROM CUSTOMERS ORDER BY name");
  }

  @Override
  public List<Customer> getCustomers(String sql) throws CustomerDAOException {
    try (Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery(sql);) {
      List<Customer> list = new ArrayList<>();
      while (resultset.next()) {
        list.add(new Customer(resultset.getString("dni"), resultset.getString("name"),
            resultset.getString("address"), resultset.getString("phone")));
      }
      return list;
    } catch (SQLException e) {
      throw new CustomerDAOException(e);
    }
  }

  @Override
  public void close() throws CustomerDAOException {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new CustomerDAOException(e);
    }
  }

}
