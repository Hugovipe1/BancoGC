package bankgc.presentation;

import bankgc.business.Customer;
import bankgc.business.CustomerIllegalArgumentException;
import bankgc.business.CustomerManager;
import bankgc.data.CustomerDAOException;
import static util.Keyboard.*;
import util.Menu;

public class CustomersManagement {

  private CustomerManager customerManager;

  public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/BankGC";
    String user = System.getenv("USER");
    String password = System.getenv("PASSWORD");

    try {
      CustomersManagement program = new CustomersManagement(url, user, password);
      program.run();
      System.out.println("¡Hasta la próxima! ;-)");
    } catch (CustomerDAOException e) {
      System.err.println("Error al conectar con la base de datos");
      e.printStackTrace();
    }
  }

  public CustomersManagement(String url, String user, String password) throws CustomerDAOException {
    customerManager = new CustomerManager(url, user, password);
  }

  private void run() throws CustomerDAOException {
    Menu menu = createMenu();
    int option;
    do {
      option = menu.choose();
      switch (option) {
        case 1 -> list();
        case 2 -> add();
        case 3 -> remove();
        case 4 -> modify();
        case 5 -> show();
      }
    } while (option != menu.lastOption());
    customerManager.close();
  }

  private void list() {
    try {
      for (Customer customer: customerManager.getList()) {
        System.out.println(customer);
        System.out.println("---");
      }
    } catch (CustomerDAOException e) {
      System.err.println("No se ha podido obtener la lista de clientes: " + e.getMessage());
    }
  }

  private void add() {
    try {
      customerManager.add(readStr("DNI del cliente a añadir"), readStr("Nombre"), readStr("Dirección"),
          readStr("Teléfono"));
      System.out.println("\n¡Cliente añadido!");
    } catch (CustomerIllegalArgumentException | CustomerDAOException e) {
      System.err.println("No se ha podido añadir el cliente: " + e.getMessage());
    }
  }

  private void remove() {
    try {
      customerManager.remove(readStr("DNI del cliente a borrar"));
      System.out.println("\n¡Cliente borrado!");
    } catch (CustomerDAOException e) {
      System.err.println("No se ha podido borrar el cliente: " + e.getMessage());
    }
  }

  private void modify() {
    try {
      Customer customer = customerManager.get(readStr("DNI del cliente a modificar"));
      customerManager.set(customer.getDni(), read("Nombre", customer.getName()),
          read("Dirección", customer.getAddress()), read("Teléfono", customer.getPhone()));
      System.out.println("\n¡Cliente modificado!");
    } catch (CustomerIllegalArgumentException | CustomerDAOException e) {
      System.err.println("No se ha podido modificar el cliente: " + e.getMessage());
    }
  }
  
  private void show() {
    try {
      Customer customer = customerManager.get(readStr("DNI del cliente a mostrar"));
      System.out.println("\n" + customer);
    } catch (CustomerDAOException e) {
      System.err.println("El DNI no existe en la base de datos.");
    }
  }

  private Menu createMenu() {
    return new Menu("\nGestión de clientes del BankGC", "Listado", "Alta de cliente", "Baja de cliente",
        "Modificación de cliente", "Mostrar cliente", "Terminar");
  }

}
