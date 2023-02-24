package bankgc.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import bankgc.business.Account;
import bankgc.business.Movimientos;
import bankgc.business.TipoNoValidoException;

public class MovimientosDAOMySql implements MovimientosDAO{
  private Connection connection;

  public MovimientosDAOMySql(Connection connection) {
    this.connection = connection;
  }
  
  @Override
  public void income(Movimientos movimientos) throws CustomerDAOException {
    String sql = "INSERT INTO movimientos(numerocuenta, importe, fechahora, tipo, concepto) VALUES(" + movimientos.getNumberAccount()
    + "," + movimientos.getImporte() + ",'"+ movimientos.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))
    + "', '"+ movimientos.getTipo() +"','"+ movimientos.getConcepto()+"')";
    executeUpdate(sql);
  }
  
  @Override
  public void withdrawals(Movimientos movimientos) throws CustomerDAOException {
    String sql = "INSERT INTO movimientos(numerocuenta, importe, fechahora, tipo, concepto) VALUES(" + movimientos.getNumberAccount()
    + "," + movimientos.getImporte() + ",'"+ movimientos.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))
    + "', '"+ movimientos.getTipo() +"','"+ movimientos.getConcepto()+"')";
    executeUpdate(sql);
  }
  
  public void transfer(Movimientos movimientos) throws CustomerDAOException {
    String sql = "INSERT INTO movimientos VALUES(" + movimientos.getNumberAccount()
    + "," + movimientos.getImporte() + ",'"+ movimientos.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))
    + "', 'transfenv',"+ movimientos.getNumeroCuentaTransferencia() + ",'" + movimientos.getConcepto()+"')";
    executeUpdate(sql);
    
    String sqlTransfRecibida = "INSERT INTO movimientos VALUES(" + movimientos.getNumeroCuentaTransferencia()
    + "," + movimientos.getImporte() + ",'"+ movimientos.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))
    + "', 'transrec',"+ movimientos.getNumberAccount() + ",'" + movimientos.getConcepto()+"')";
    executeUpdate(sqlTransfRecibida);
  }
  
  
  @Override
  public List<Movimientos> getMovimientos(int numberAccount) throws CustomerDAOException, TipoNoValidoException {
    String sql = "SELECT * FROM movimientos WHERE numerocuenta=" + numberAccount;
    try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)){
      List<Movimientos> list = new ArrayList<>();
      while(resultSet.next()) {
        list.add(new Movimientos(resultSet.getInt("numerocuenta"),resultSet.getDouble("importe"), LocalDateTime.parse(resultSet.getString("fechahora"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), resultSet.getString("tipo"), resultSet.getInt("numcuentatrans"), resultSet.getString("concepto"))); 
      }
      return list;
    } catch (SQLException e) {
        throw new CustomerDAOException();
    }
  }
  
  @Override
  public List<Movimientos> getMovimientos(int numberAccount, LocalDateTime firstTime, LocalDateTime secondTime)
      throws CustomerDAOException, TipoNoValidoException {
    String sql = "SELECT * FROM movimientos WHERE numerocuenta=" + numberAccount + " and fechahora BETWEEN '"+ firstTime +"' and '" + secondTime + "'";
    try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)){
      List<Movimientos> list = new ArrayList<>();
      while(resultSet.next()) {
        list.add(new Movimientos(resultSet.getInt("numerocuenta"),resultSet.getDouble("importe"), LocalDateTime.parse(resultSet.getString("fechahora"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), resultSet.getString("tipo"), resultSet.getInt("numcuentatrans"), resultSet.getString("concepto"))); 
      }
      return list;
    } catch (SQLException e) {
        throw new CustomerDAOException();
    }
  }
  
  @Override
  public List<Double> getSaldo(int numberAccount) throws CustomerDAOException{
    String sql = "SELECT importe, tipo FROM movimientos WHERE numerocuenta=" + numberAccount;
    try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)){
      List<Double> list = new ArrayList<>();
      while(resultSet.next()) {
        double importe = resultSet.getDouble("importe");
        if(resultSet.getString("tipo").equals("salida")) {
          importe = importe * -1;
        }
        if(resultSet.getString("tipo").equals("transfenv")) {
          importe = importe * -1;
        }
        list.add(importe); 
      }
      return list;
    } catch (SQLException e) {
        throw new CustomerDAOException();
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
