package bankgc.business;

public class Customer {

  private String dni;
  private String name;
  private String address;
  private String phone;

  public Customer(String dni, String name, String address, String phone) {
    setDni(dni);
    setName(name);
    setAddress(address);
    setPhone(phone);
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    throwExceptionIfDniIsWrong(dni);
    this.dni = dni.toUpperCase();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new CustomerIllegalArgumentException("El nombre no puede estar vacío");
    }
    this.name = name;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    if (name == null || name.isBlank()) {
      throw new CustomerIllegalArgumentException("La dirección no puede estar vacía");
    }
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    if (phone == null || !phone.matches("[679]\\d{8}")) {
      throw new CustomerIllegalArgumentException("La sintaxis del teléfono es errónea");
    }
    this.phone = phone;
  }

  private void throwExceptionIfDniIsWrong(String dni) {
    if (dni == null || !dni.matches("\\d{8}[a-zA-Z]")) {
      throw new CustomerIllegalArgumentException("La sintaxis del DNI es errónea");
    }
    String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
    int dniNumber = Integer.parseInt(dni.substring(0, 8));
    char dniLetter = letters.charAt(dniNumber % letters.length());
    if (dniLetter != dni.charAt(8)) {
      throw new CustomerIllegalArgumentException("La letra del DNI es errónea");
    }
  }

  @Override
  public String toString() {
    return "Nombre: " + name + "\n" + "DNI: " + dni + "\n" + "Dirección: " + address + "\n"
        + "Teléfono: " + phone;
  }


}
