package util;

/**
 * Conjunto de funciones genéricas que me serán de utilidad para simplificar procesos.
 * 
 * @author Rafael del Castillo Gomariz
 *
 */

public class Util {
  
  public static int randomInt(int min, int max) {
    return min + (int) (Math.random() * (max-min+1));
  }
}
