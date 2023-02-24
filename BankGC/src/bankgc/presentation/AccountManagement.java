package bankgc.presentation;

import bankgc.business.Account;
import bankgc.business.AccountManager;
import bankgc.business.Customer;
import bankgc.business.ImporteNegativoException;
import bankgc.business.Movimientos;
import bankgc.business.TipoNoValidoException;
import bankgc.data.CustomerDAOException;
import bankgc.data.DAOFactoryException;
import util.Menu;
import static util.Keyboard.*;
import java.util.List;

public class AccountManagement {

  private AccountManager accountManager;

  public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/cuentabancaria";
    String user = System.getenv("USER");
    String password = System.getenv("PASSWORD");


    try {
      AccountManagement program = new AccountManagement(url, user, password);
      try {
        program.run();
      } catch (CustomerDAOException e) {
        System.err.println("Error al conectar con la base de datos");
        e.printStackTrace();
      }
    } catch (DAOFactoryException e) {
      e.printStackTrace();
    }


  }

  public AccountManagement(String url, String user, String password) throws DAOFactoryException {
    accountManager = new AccountManager(url, user, password);
  }

  public void run() throws CustomerDAOException {
    Menu menu = createMenu();
    int option;
    do {
      option = menu.choose();
      switch (option) {
        case 1 -> add();
        case 2 -> remove();
        case 3 -> income();
        case 4 -> withdrawals();
        case 5 -> transfer();
        case 6 -> showAccount();
        case 7 -> showAccounts();
        case 8 -> showTransaction();
      }
    } while (option != menu.lastOption());
    accountManager.close();
    System.out.println("¡Hasta la próxima!");
  }

  private Menu createMenu() {
    return new Menu("\nGestión de cuentas del BankGC", "Alta de cuenta", "Eliminar cuenta", "Ingreso",
        "Retirada de dinero", "Transferencia", "Listar cuenta","Listar todas las cuentas", "Mostrar movimientos de una cuenta", "Terminar");
  }

  private void add() {
    try {
      accountManager.add(readStr("Introduce el dni de la cuenta a dar de alta"));
      System.out.println("Cuenta dada de alta");
    } catch (CustomerDAOException e) {
      System.err.println("No se ha podido dar de alta la cuenta");
      e.printStackTrace();
    }
  }

  public void remove() {
    try {
      accountManager.remove(readInt("Introduce el numero de cuenta a borrar"), readStr("Introduce el dni de la cuenta a eliminar"));
      System.out.println("Cuenta borrada");
    } catch (CustomerDAOException e) {
      System.err.println("No se ha podido eliminar la cuenta");
      e.printStackTrace();
    }
  }

  public void income() {
    try {
      accountManager.income(readInt("Introduce el número de cuenta para hacer el ingreso"), readDouble("Introduce la cantidad a insertar en la cuenta"),readStr("Introduce un concepto"));
    } catch (CustomerDAOException e) {
      System.err.println("No se puede hacer un ingreso de una cuenta que está desactivada");
      e.printStackTrace();
    } catch (TipoNoValidoException e) {
      System.err.println("ERROR: Tipo no valido");
      e.printStackTrace();
    } catch (ImporteNegativoException e) {
      System.err.println("no puedes poner un importe negativo");
      e.printStackTrace();
    }   
  }
  
  public void withdrawals() {
    try {
      accountManager.withdrawals(readInt("Introduce el número de la cuenta para hacer el cargo"), readDouble("Introduce la cantidad a retirar"), readStr("Introduce un concepto"));
    } catch (CustomerDAOException e) {
      System.err.println("No se puede hacer un cargo la cuenta está desactivada");
      e.printStackTrace();
    } catch (TipoNoValidoException e) {
      System.err.println("ERROR: Tipo no válido");
      e.printStackTrace();
    } catch (ImporteNegativoException e) {
      System.err.println("no puedes poner un importe negativo");
      e.printStackTrace();
    }
  }
  
  public void showAccount() {
    try {
      Account account = accountManager.get(readInt("Número de la cuenta a mostrar"));
      System.out.println("\n" + account);
    } catch (CustomerDAOException e) {
      System.err.println("La cuenta introducida no existe");
    }   
  }
  
  public void showAccounts() {
    try {
      List<Account> list = accountManager.getAccounts();
      for(Account account : list) {
        System.out.println(account);
        System.out.println("------");
      }
    } catch (CustomerDAOException e) {
      e.printStackTrace();
    }  
    
  }
  
  public void showTransaction() {
    try {
      int numberAccount = readInt("Número de cuenta a mostrar movimientos");
      List<Movimientos> list = accountManager.getTransaction(numberAccount);
      double saldoCuenta = accountManager.getSaldo(numberAccount);
      System.out.println("Saldo de la cuenta nº "+ numberAccount +": " + saldoCuenta);
      for(Movimientos movimientos : list) {
        System.out.println(movimientos);
        System.out.println("------");
      }
    } catch (CustomerDAOException e) {
      System.err.println("La cuenta introducida no existe");
    } catch (TipoNoValidoException e) {
      System.err.println("ERROR: Tipo incorrecto");
    }
    
  }
  
  public void transfer(){
    try {
      accountManager.transfer(readInt("Número de cuenta desde la que se hace la transferencia"),
          readInt("Número de cuenta para enviar la transferencia"), readDouble("Introduce el importe de la transferencia"),
          readStr("Introduce el concepto de la transferencia"));
    } catch (CustomerDAOException e) {
      System.err.println("La cuenta introducida no existe");
      e.printStackTrace();
    } catch (TipoNoValidoException e) {
      System.err.println("ERROR: Tipo incorrecto1");
      e.printStackTrace();
    } catch (ImporteNegativoException e) {
      System.err.println("no puedes poner un importe negativo");
      e.printStackTrace();
    }
  }

}
