package util;

import java.util.Scanner;

public class Keyboard {

  private static Scanner in = new Scanner(System.in);

  public static boolean confirm() {
    return confirm("¿Está seguro/a? (S/N)");
  }

  public static boolean confirm(String prompt) {
    String resp;
    for (;;) {
      resp = readStr(prompt).toUpperCase();
      if (resp.equals("S") || resp.equals("N")) {
        break;
      }
      System.out.println("Respuesta incorrecta, pulse S o N y después Intro");
    }
    return resp.equals("S");
  }

  public static double read(String prompt, double defaultValue) {
    String returnValue = readStr(prompt + " [" + defaultValue + "]");
    return returnValue.isEmpty() ? defaultValue : Double.parseDouble(returnValue);
  }

  public static int read(String prompt, int defaultValue) {
    String returnValue = readStr(prompt + " [" + defaultValue + "]");
    return returnValue.isEmpty() ? defaultValue : Integer.parseInt(returnValue);
  }

  public static String read(String prompt, String defaultValue) {
    String returnValue = readStr(prompt + " [" + defaultValue + "]");
    return returnValue.isEmpty() ? defaultValue : returnValue;
  }

  public static double readDouble(String prompt) {
    System.out.print(prompt + ": ");
    double n = in.nextDouble();
    in.nextLine();  // sacamos \n del buffer de teclado
    return n;
  }

  public static double readDouble(String prompt, String end) {
    double n = readDouble(prompt);
    System.out.print(end);
    return n;
  }

  public static int readInt(String prompt) {
    System.out.print(prompt + ": ");
    int n = in.nextInt();
    in.nextLine();  // sacamos \n del buffer de teclado
    return n;
  }

  public static int readInt(String prompt, String end) {
    int n = readInt(prompt);
    System.out.print(end);
    return n;
  }

  public static String readStr(String prompt) {
    System.out.print(prompt + ": ");
    return in.nextLine();
  }

  public static String readStr(String prompt, String end) {
    String str = readStr(prompt);
    System.out.print(end);
    return str;
  }

}
