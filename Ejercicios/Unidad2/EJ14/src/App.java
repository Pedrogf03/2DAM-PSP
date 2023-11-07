import java.util.Scanner;

public class App {
  public static void main(String[] args) throws Exception {

    Scanner sc = new Scanner(System.in);

    System.out.print("Introduce un número entero positivo: ");
    long num = sc.nextLong();

    Hilo h = new Hilo(num);

    h.start();

    h.join(10);
    while (h.isAlive()) {
      System.out.println("¿Quiere esperar más?");
      long milis = sc.nextLong();
      if (milis == 0) {
        h.interrupt();
        break;
      } else {
        h.join(milis);
      }
    }

    if (h.isInterrupted()) {
      System.out.println("Hilo interrumpido.");
    }

    sc.close();

  }
}
