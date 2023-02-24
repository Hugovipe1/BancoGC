package bankgc.data;

import java.time.LocalDateTime;
import java.util.List;
import bankgc.business.Account;
import bankgc.business.Customer;
import bankgc.business.Movimientos;
import bankgc.business.TipoNoValidoException;

public interface AccountDAO {
  public void add(Account account) throws CustomerDAOException;
  public void remove(Account account) throws CustomerDAOException;
  public void set(Account account) throws CustomerDAOException;
  public void transfer(int numberAccount, int numberAccountTransfer, double importe, LocalDateTime time, String concepto)
      throws CustomerDAOException, TipoNoValidoException;
  public Account get(int numberAccount) throws CustomerDAOException;
  public List<Account> getList() throws CustomerDAOException;
  public void close() throws CustomerDAOException;
  void income(int numberAccount, double importe, LocalDateTime time, String concepto) throws CustomerDAOException, TipoNoValidoException;
  public void withdrawals(int numberAccount, double importe, LocalDateTime time, String concepto)
      throws CustomerDAOException, TipoNoValidoException;
  public List<Movimientos> getMovimientos(int numberAccount) throws CustomerDAOException, TipoNoValidoException;
  public List<Movimientos> getMovimientos(int numberAccount, LocalDateTime firstTime, LocalDateTime secondTime)
      throws CustomerDAOException, TipoNoValidoException;
  public List<Account> getAccounts() throws CustomerDAOException;
  public List<Account> getAccounts(String sql) throws CustomerDAOException;
  public List<Double> getSaldo(int numberAccount) throws CustomerDAOException;
  
}
