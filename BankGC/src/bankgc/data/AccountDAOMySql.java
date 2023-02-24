package bankgc.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import bankgc.business.Account;
import bankgc.business.Customer;
import bankgc.business.Movimientos;
import bankgc.business.TipoNoValidoException;

public class AccountDAOMySql implements AccountDAO{
  private Connection connection;
  private MovimientosDAO dao;

  public AccountDAOMySql(Connection connection) throws DAOFactoryException {
    this.connection = connection;
    DAOFactory daoFactory = new DAOFactory(connection);
    dao = daoFactory.getMovimientosDAO();
  }

  @Override
  public void add(Account account) throws CustomerDAOException {
    String sql = "INSERT INTO account (dnicustomer, actived) VALUES ('" + account.getDniCustomer()
    + "'," + true + ")";
    executeUpdate(sql);

  }

  @Override
  public void remove(Account account) throws CustomerDAOException {
    String sql =
        "UPDATE account SET actived= " + false+ " WHERE numberaccount=" + account.getNumberAccount()+
        "and dnicustomer = '" + account.getDniCustomer()+"'";
    int rowsUpdated = executeUpdate(sql);
    if (rowsUpdated == 0) {
      throw new CustomerDAOException("No existe ninguna cuenta con el número de cuenta: " + account.getNumberAccount());
    }
  }

  public void set(Account account) throws CustomerDAOException {
    if(!account.isActived()) {
      String sql =
          "UPDATE account SET activated=" + true  + " WHERE numberaccount=" + account.getNumberAccount();
      int rowsUpdated = executeUpdate(sql);
      if (rowsUpdated == 0) {
        throw new CustomerDAOException("No existe ninguna cuenta con " + account.getNumberAccount());
      }

    }else {
      remove(account);
    }
  }

  @Override
  public void income(int numberAccount, double importe, LocalDateTime time, String concepto)
      throws CustomerDAOException, TipoNoValidoException {
    Account account = get(numberAccount);
    if(!account.isActived()) {
      throw new CustomerDAOException("No se puede hacer un ingreso en una cuenta que está desactivada");
    }
    dao.income(new Movimientos(numberAccount, importe, time, "ingreso", concepto));
  }
  @Override
  public void withdrawals(int numberAccount, double importe, LocalDateTime time, String concepto)
      throws CustomerDAOException, TipoNoValidoException{
    Account account = get(numberAccount);
    if(!account.isActived()) {
      throw new CustomerDAOException("No se puede hacer un cargo en una cuenta que está desactivada");
    }
    dao.withdrawals(new Movimientos(numberAccount, importe, time, "salida", concepto ));
  }


  @Override
  public List<Movimientos> getMovimientos(int numberAccount) throws CustomerDAOException, TipoNoValidoException {
    Account account = get(numberAccount);
    return dao.getMovimientos(numberAccount);
  }

  @Override
  public List<Movimientos> getMovimientos(int numberAccount, LocalDateTime firstTime, LocalDateTime secondTime)
      throws CustomerDAOException, TipoNoValidoException {
    Account account = get(numberAccount);
    return dao.getMovimientos(numberAccount, firstTime, secondTime);
  }

  @Override
  public List<Double> getSaldo(int numberAccount) throws CustomerDAOException {
    Account account = get(numberAccount);
    return dao.getSaldo(numberAccount);
  }

  @Override
  public void transfer(int numberAccount, int numberAccountTransfer, double importe,
      LocalDateTime time, String concepto) throws CustomerDAOException, TipoNoValidoException {
    Account account = get(numberAccount);
    if(!account.isActived()) {
      throw new CustomerDAOException("No se puede hacer una transferencia de una cuenta que está desactivada");
    }
    Account accountTransfer = get(numberAccountTransfer);
    if(!accountTransfer.isActived()) {
      throw new CustomerDAOException("No se puede hacer una transferencia a una cuenta que está desactivada");
    }

    dao.transfer(new Movimientos(numberAccount, importe, time, "transferencia", numberAccountTransfer, concepto));
  }

  public List<Account> getAccounts() throws CustomerDAOException{
    String sql = "SELECT * FROM account";
    try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      List<Account> list = new ArrayList<>();
      while(resultSet.next()) {
        list.add(new Account(resultSet.getInt("numberAccount"), resultSet.getString("dnicustomer"), resultSet.getBoolean("actived")));
      }
      return list;
    } catch (SQLException e) {
      throw new CustomerDAOException();
    }
  }





  @Override
  public Account get(int numberAccount) throws CustomerDAOException {
    String sql = "SELECT * FROM account WHERE numberaccount=" + numberAccount;
    try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      if(!resultSet.next()) {
        throw new CustomerDAOException("La cuenta " + numberAccount + " no existe en la base de datos");
      }
      return new Account(resultSet.getInt("numberAccount"), resultSet.getString("dnicustomer"), resultSet.getBoolean("actived"));
    } catch (SQLException e) {
      throw new CustomerDAOException();
    }
  }

  @Override
  public List<Account> getList() throws CustomerDAOException {
    return getAccounts("SELECT * FROM CUSTOMERS ORDER BY name");

  }

  @Override
  public List<Account> getAccounts(String sql) throws CustomerDAOException{
    try(Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery(sql)) {
      List<Account> list = new ArrayList<>();
      while (resultset.next()) {
        list.add(new Account(resultset.getInt("numberaccount"), resultset.getString("dnicustomer"),
            resultset.getBoolean("actived")));
      }
      return list;
    } catch (SQLException e) {
      throw new CustomerDAOException();
    }
  }
  public void close() throws CustomerDAOException {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new CustomerDAOException(e);
    }
  }

  private int executeUpdate(String sql) throws CustomerDAOException {
    try (Statement statement = connection.createStatement()) {
      return statement.executeUpdate(sql);
    } catch (SQLException e) {
      throw new CustomerDAOException(e);
    }
  }

}
