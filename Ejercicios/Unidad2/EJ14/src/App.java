import java.util.Scanner;

public class App {
  public static void main(String[] args) throws Exception {

    Scanner sc = new Scanner(System.in);

    System.out.print("Introduce un número entero positivo: ");
    int num = Integer.parseInt(sc.nextLine());

    Hilo h = new Hilo(num);

    h.start();

    Thread.sleep(10);

    while (true) {

      if (!h.isAlive())
        break;

      System.out.println("¿Quiere seguir esperando? (y)");
      String respuesta = sc.nextLine();

      if (respuesta.equalsIgnoreCase("y")) {
        System.out.println("Indique durante cuanto tiempo (ms) quiere seguir esperando: ");
        int sleep = Integer.parseInt(sc.nextLine());
        Thread.sleep(sleep);
      } else {
        System.out.println("Hilo interrumpido.");
        h.interrupt();
      }

    }

    sc.close();

  }
}
