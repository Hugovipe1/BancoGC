package bankgc.business;

public class Account {
	private int numberAccount;
	private String dniCustomer;
	private boolean actived;

	public Account(int numberAccount, String dniCustomer, boolean activated ){
		this.numberAccount = numberAccount;
		this.dniCustomer = dniCustomer;
		this.actived = activated;
	}
	
	public Account(String dni, boolean actived) {
	  dniCustomer = dni;
	  this.actived = actived;
	}
	
	public int getNumberAccount() {
		return numberAccount;
	}

	public void setNumberAccount(int numberAccount) {
		this.numberAccount = numberAccount;
	}

	public String getDniCustomer() {
		return dniCustomer;
	}

	public void setDniCustomer(String dniCustomer) {
		this.dniCustomer = dniCustomer;
	}

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}

  @Override
  public String toString() {
    return "numberAccount: " + numberAccount +"\n" + "dniCustomer: " + dniCustomer +"\n" + "actived: "
        + actived + "\n";
  }
	
	

}
