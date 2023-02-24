package bankgc.business;

import java.time.LocalDateTime;
import java.util.List;
import bankgc.data.AccountDAO;
import bankgc.data.CustomerDAO;
import bankgc.data.CustomerDAOException;
import bankgc.data.DAOFactory;
import bankgc.data.DAOFactoryException;

public class AccountManager {
  private DAOFactory daoFactory;
  private AccountDAO dao;


  public AccountManager(DAOFactory daoFactory) throws DAOFactoryException {
    this.daoFactory = daoFactory;
    dao = daoFactory.getAccountDAO();
  }


  public AccountManager(String url, String user, String password) throws DAOFactoryException{
    daoFactory = new DAOFactory(url, user, password);
    dao = daoFactory.getAccountDAO();
  }
  
  public void set(AccountDAO accountDao) {
    this.dao = accountDao;
  }
  
  public void add(String dni) throws CustomerDAOException {
    dao.add(new Account(dni, true));
  }
  
  public void remove(int numberaccount, String dnicustomer ) throws CustomerDAOException {
    dao.remove(new Account(numberaccount, dnicustomer, true));
  }
  
  public Account get(int numberAccount) throws CustomerDAOException {
    return dao.get(numberAccount);
  }
  
  public List<Account> getAccounts() throws CustomerDAOException{
    return dao.getAccounts();
  }
  
  public void income(int numberAccount, double importe, String concepto) throws CustomerDAOException, TipoNoValidoException, ImporteNegativoException {
    importeValido(importe);
    income(numberAccount, importe, LocalDateTime.now(), concepto);
  }
  public void income(int numberAccount, double importe,LocalDateTime time, String concepto) throws CustomerDAOException, TipoNoValidoException, ImporteNegativoException {
    importeValido(importe);
    dao.income(numberAccount, importe, time, concepto);
  }
  
  public void withdrawals(int numberAccount, double importe, String concepto) throws CustomerDAOException, TipoNoValidoException, ImporteNegativoException {
    importeValido(importe);
    withdrawals(numberAccount, importe, LocalDateTime.now(), concepto);
  }
  
  public void withdrawals(int numberAccount, double importe, LocalDateTime time, String concepto) throws CustomerDAOException, TipoNoValidoException, ImporteNegativoException {
    importeValido(importe);
    dao.withdrawals(numberAccount, importe, time, concepto);
  }
  
  public List<Movimientos> getTransaction(int numberAccount) throws CustomerDAOException, TipoNoValidoException{
    return dao.getMovimientos(numberAccount);
  }
  
  public List<Movimientos> getTransaction(int numberAccount, LocalDateTime firstTime , LocalDateTime secondTime) throws CustomerDAOException, TipoNoValidoException{
    return dao.getMovimientos(numberAccount, firstTime, secondTime);
  }
  
  public Double getSaldo(int numberAccount) throws CustomerDAOException{
    List<Double> list = dao.getSaldo(numberAccount);
    double saldoCuenta = 0;
    for(double saldo : list) {
      saldoCuenta += saldo;
    }
    return saldoCuenta;
  }
  
  public void transfer(int numberAccount, int numberAccountTransfer, double importe, String concepto) throws CustomerDAOException, TipoNoValidoException, ImporteNegativoException {
    importeValido(importe);
    transfer(numberAccount, numberAccountTransfer, importe, LocalDateTime.now(), concepto);
  }
  
  public void transfer(int numberAccount, int numberAccountTransfer, double importe, LocalDateTime time, String concepto) throws CustomerDAOException, TipoNoValidoException, ImporteNegativoException {
    importeValido(importe);
    dao.transfer(numberAccount, numberAccountTransfer, importe, time, concepto);
  }
  
  public void importeValido(double importe) throws ImporteNegativoException {
    if (importe <= 0) {
      throw new ImporteNegativoException("No se puede pasar un importe negativo");
    }
  }

  /*public Customer get(String dni) throws CustomerDAOException {
    return dao.get(dni);*/
  
  public void close() throws CustomerDAOException {
    dao.close();
  }
}
