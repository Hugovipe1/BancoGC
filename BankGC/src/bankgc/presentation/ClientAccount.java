package bankgc.presentation;

import static util.Keyboard.readDouble;
import static util.Keyboard.readInt;
import static util.Keyboard.readStr;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import bankgc.business.AccountManager;
import bankgc.business.ImporteNegativoException;
import bankgc.business.Movimientos;
import bankgc.business.TipoNoValidoException;
import bankgc.data.CustomerDAOException;
import bankgc.data.DAOFactoryException;
import util.Menu;

public class ClientAccount  {

  private AccountManager accountManager;
  public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/cuentabancaria";
    String user = System.getenv("USER");
    String password = System.getenv("PASSWORD");

    if(args.length != 1) {
      System.err.println("Debe de pasarse un argumento por la línea de comandos");
      System.exit(1);
    }


    try {
      ClientAccount program = new ClientAccount(url, user, password);
      try {
        program.run(args[0]);
      } catch (CustomerDAOException e) {
        System.err.println("Error al conectar con la base de datos");
        e.printStackTrace();
      } catch (FormatoFechaIllegalException e) {
        System.err.println("Formato de la fecha ilegal");
        e.printStackTrace();
      }
    } catch (DAOFactoryException e) {
      e.printStackTrace();
    }

  }

  public ClientAccount(String url, String user, String password) throws DAOFactoryException {
    accountManager = new AccountManager(url, user, password);
  }

  public void run(String numberAccountString) throws CustomerDAOException, FormatoFechaIllegalException {
    int numberAccount = Integer.parseInt(numberAccountString);
    Menu menu = createMenu(numberAccount);
    int option;
    do {
      option = menu.choose();
      switch (option) {
        case 1 -> listadoMovimientos(numberAccount);
        case 2 -> getSaldo(numberAccount);
        case 3 -> income(numberAccount);
        case 4 -> withdrawals(numberAccount);
        case 5 -> transfer(numberAccount);
      }
    } while (option != menu.lastOption());
    accountManager.close();
    System.out.println("¡Hasta la próxima!");
  }

  private Menu createMenu(int numberAccount) {
    return new Menu("\nGestión de la cuenta nº " + numberAccount, "Listado de movimientos entre fechas", "Mostrar saldo", "Hacer un ingreso",
        "Hacer un cargo", "Hacer una transferencia", "Terminar");
  }

  public void income(int numberAccount) {
    try {
      accountManager.income(numberAccount, readDouble("Introduce la cantidad a insertar en la cuenta"),readStr("Introuce un concepto"));
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

  public void withdrawals(int numberAccount) {
    try {
      accountManager.withdrawals(numberAccount, readDouble("Introduce la cantidad a retirar"), readStr("Introduce un concepto"));
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

  public void transfer(int numberAccount){
    try {
      accountManager.transfer(numberAccount,
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

  public void getSaldo(int numberAccount) {
    try { 
      double saldoCuenta = accountManager.getSaldo(numberAccount);
      System.out.println("Saldo de la cuenta nº "+ numberAccount + ": "+ saldoCuenta);
    } catch (CustomerDAOException e) {
      System.err.println("Cuenta no encontrada");
      e.printStackTrace();
    }
  }

  public void listadoMovimientos(int numberAccount) throws FormatoFechaIllegalException {
    String firstTime = readStr("Introduce una fecha y hora con el formato(yyyy-MM-dd HH:mm:ss)");
    String secondTime = readStr("Introduce una fecha y hora con el formato(yyyy-MM-dd HH:mm:ss)");
    if(firstTime.length() != 19 || secondTime.length() != 19) {
      throw new FormatoFechaIllegalException("No se puede insertar una fecha que no tenga ese formato");
    }
    LocalDateTime time1 = LocalDateTime.parse(firstTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTime time2 = LocalDateTime.parse(secondTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    if(time1.isAfter(time2)) {
      LocalDateTime time3 = time1;
      time1 = time2;
      time2 = time3;
    }
    try {
      List<Movimientos> list = accountManager.getTransaction(numberAccount, time1, time2);
      double saldoCuenta = accountManager.getSaldo(numberAccount);
      System.out.println("Saldo de la cuenta nº "+ numberAccount +": " + saldoCuenta);
      for(Movimientos movimientos : list) {
        System.out.println(movimientos);
        System.out.println("------");
      }
    } catch (CustomerDAOException e) {
      e.printStackTrace();
    } catch (TipoNoValidoException e) {
      System.err.println("ERROR: Tipo incorrecto");
    }
  }


}
