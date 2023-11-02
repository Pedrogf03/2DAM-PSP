public class Main {

  public static void main(String[] args) {

    Thread pares = new Hilo1();
    Thread primos = new Hilo2(1728, 6);

    pares.start();
    primos.start();

    try {
      Thread.sleep((int) (Math.random() * (5000 + 1)));
    } catch (InterruptedException e) {
    }

    pares.interrupt();

    if (pares.isAlive()) {
      if (pares.isInterrupted()) {
        System.out.println("Hilo pares interrumpido.");
      }
    }

    try {
      Thread.sleep((int) (Math.random() * (5000 + 1)));
    } catch (InterruptedException e) {
    }

    primos.interrupt();

    if (primos.isAlive()) {
      if (primos.isInterrupted()) {
        System.out.println("Hilo primos interrumpido.");
      }
    }

  }

}
