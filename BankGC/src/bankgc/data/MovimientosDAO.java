package bankgc.data;

import java.time.LocalDateTime;
import java.util.List;
import bankgc.business.Movimientos;
import bankgc.business.TipoNoValidoException;

public interface MovimientosDAO {
  public void income(Movimientos movimientos) throws CustomerDAOException;
  public List<Movimientos> getMovimientos(int numberAccount) throws CustomerDAOException, TipoNoValidoException;
  public void withdrawals(Movimientos movimientos) throws CustomerDAOException;
  public void transfer(Movimientos movimientos) throws CustomerDAOException;
  public List<Movimientos> getMovimientos(int numberAccount, LocalDateTime firstTime,
      LocalDateTime secondTime) throws CustomerDAOException, TipoNoValidoException;
  public List<Double> getSaldo(int numberAccount) throws CustomerDAOException;
  
}
