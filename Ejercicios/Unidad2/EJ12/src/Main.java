import java.util.Random;

public class Main {

  public static void main(String[] args) {

    Thread pares = new Hilo1();
    Thread primos = new Hilo2(1728, 3); // 515684624612

    pares.start();
    primos.start();

    Random aleat = new Random();

    while (true) {
      if (pares.isAlive()) {
        try {
          Thread.sleep(aleat.nextInt(4000) + 1000);
          pares.interrupt();
          System.out.println("¿Pares ha sido interrumpido? " + pares.isInterrupted());
          System.out.println("¿Pares está vivo? " + pares.isAlive());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      if (primos.isAlive()) {
        try {
          Thread.sleep(aleat.nextInt(4000) + 1000);
          primos.interrupt();
          System.out.println("¿Primos ha sido interrumpido? " + primos.isInterrupted());
          System.out.println("¿Primos está vivo? " + primos.isAlive());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      if (!pares.isAlive() && !primos.isAlive()) {
        break;
      }

    }

  }

}
