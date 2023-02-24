package bankgc.business;

public class CustomerIllegalArgumentException extends IllegalArgumentException {

  private static final long serialVersionUID = 1L;

  public CustomerIllegalArgumentException() {
    super();
  }

  public CustomerIllegalArgumentException(String s) {
    super(s);
  }

  public CustomerIllegalArgumentException(Throwable cause) {
    super(cause);
  }

  public CustomerIllegalArgumentException(String message, Throwable cause) {
    super(message, cause);
  }

}
