package bankgc.business;

import java.util.List;
import bankgc.data.CustomerDAO;
import bankgc.data.DAOFactory;
import bankgc.data.DAOFactoryException;
import bankgc.data.CustomerDAOException;

public class CustomerManager {

  private DAOFactory daoFactory;
  private CustomerDAO dao;

  public CustomerManager(DAOFactory daoFactory) throws CustomerDAOException {
    try {
      this.daoFactory = daoFactory;
      this.dao = daoFactory.getCustomerDAO();
    } catch (DAOFactoryException e) {
      throw new CustomerDAOException(e);
    }
  }

  public CustomerManager(String url, String user, String password) throws CustomerDAOException {
    try {
      daoFactory = new DAOFactory(url, user, password);
      dao = daoFactory.getCustomerDAO();
    } catch (DAOFactoryException e) {
      throw new CustomerDAOException(e);
    }
  }

  public void set(CustomerDAO customerDAO) {
    this.dao = customerDAO;
  }

  public Customer get(String dni) throws CustomerDAOException {
    return dao.get(dni);
  }

  public void add(String dni, String name, String address, String phone)
      throws CustomerDAOException {
    dao.add(new Customer(dni, name, address, phone));
  }

  public void set(String dni, String name, String address, String phone)
      throws CustomerDAOException {
    dao.set(new Customer(dni, name, address, phone));
  }

  public void setName(String dni, String name) throws CustomerDAOException {
    Customer customer = get(dni);
    customer.setName(name);
    dao.set(customer);
  }

  public void setAddress(String dni, String address) throws CustomerDAOException {
    Customer customer = get(dni);
    customer.setAddress(address);
    dao.set(customer);
  }

  public void setPhone(String dni, String phone) throws CustomerDAOException {
    Customer customer = get(dni);
    customer.setPhone(phone);
    dao.set(customer);
  }

  public void remove(String dni) throws CustomerDAOException {
    // TODO comprobar si tiene cuenta bancaria
    dao.remove(dni);
  }

  public List<Customer> getList() throws CustomerDAOException {
    return dao.getList();
  }

  public List<Customer> getList(String filter) throws CustomerDAOException {
    return dao.getCustomers(filter);
  }

  public void close() throws CustomerDAOException {
    dao.close();
  }

}
