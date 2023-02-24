package bankgc.business;

import java.time.LocalDateTime;
import java.util.List;

public class Movimientos {
  private int numberAccount;
  private double importe;
  private LocalDateTime time;
  private String tipo;
  private int numeroCuentaTransferencia;
  private String concepto;
  
  
  public Movimientos(int numberAccount, double importe, LocalDateTime time, String tipo, String concepto) throws TipoNoValidoException{
    this.numberAccount = numberAccount;
    this.importe = importe;
    this.time = time;
    setTipo(tipo);
    this.concepto = concepto;
  }
  
  public Movimientos(int numberAccount, double importe, LocalDateTime time, String tipo, int numeroCuentaTransferencia, String concepto) throws TipoNoValidoException{
    this.numberAccount = numberAccount;
    this.importe = importe;
    this.time = time;
    this.tipo = tipo;
    this.numeroCuentaTransferencia = numeroCuentaTransferencia;
    this.concepto = concepto;
  }


  public int getNumberAccount() {
    return numberAccount;
  }


  public void setNumberAccount(int numberAccount) {
    this.numberAccount = numberAccount;
  }


  public double getImporte() {
    return importe;
  }


  public void setImporte(double importe) {
    this.importe = importe;
  }


  public LocalDateTime getTime() {
    return time;
  }


  public void setTime(LocalDateTime time) {
    this.time = time;
  }


  public String getTipo() {
    return tipo;
  }


  public void setTipo(String tipo) throws TipoNoValidoException {
    if((tipo != "ingreso") && (tipo != "salida") && (tipo != "transenv") && (tipo != "transrec")) {
      throw new TipoNoValidoException("Tipo incorrecto: tiene que ser (ingreso, salida, transenv o transrec)");
    }
    this.tipo = tipo;
  }


  public int getNumeroCuentaTransferencia() {
    return numeroCuentaTransferencia;
  }


  public void setNumeroCuentaTransferencia(int numeroCuentaTransferencia) {
    this.numeroCuentaTransferencia = numeroCuentaTransferencia;
  }


  public String getConcepto() {
    return concepto;
  }


  public void setConcepto(String concepto) {
    this.concepto = concepto;
  }

  @Override
  public String toString() {
    return "numberAccount: " + numberAccount +"\n"+ "importe: " + importe +"\n" + "time: " + time
        +"\n" + "tipo: " + tipo +"\n" + "numeroCuentaTransferencia: " + numeroCuentaTransferencia
        +"\n" + "concepto: " + concepto +"\n" + "\n";
  }
  
  
  
  
  }
