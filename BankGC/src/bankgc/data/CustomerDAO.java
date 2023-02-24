package bankgc.data;

import java.util.List;
import bankgc.business.Customer;

public interface CustomerDAO {
  
  public void add(Customer customer) throws CustomerDAOException;

  public void close() throws CustomerDAOException;

  public void set(Customer customer) throws CustomerDAOException;

  public Customer get(String dni) throws CustomerDAOException;

  public void remove(String dni) throws CustomerDAOException;

  public List<Customer> getList() throws CustomerDAOException;

  public List<Customer> getCustomers(String filter) throws CustomerDAOException;

}
