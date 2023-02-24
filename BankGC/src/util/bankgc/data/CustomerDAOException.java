package bankgc.data;

public class CustomerDAOException extends Exception {

  private static final long serialVersionUID = 1L;

  public CustomerDAOException() {
    super();
  }

  public CustomerDAOException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public CustomerDAOException(String message, Throwable cause) {
    super(message, cause);
  }

  public CustomerDAOException(String message) {
    super(message);
  }

  public CustomerDAOException(Throwable cause) {
    super(cause);
  }

}
