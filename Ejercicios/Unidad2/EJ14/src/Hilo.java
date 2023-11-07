public class Hilo extends Thread {

  long numero;

  public Hilo(long num) {
    this.numero = num;
  }

  @Override
  public void run() {

    long num = numero;

    for (int i = 2; i < numero; i++) {

      while (num % i == 0) {

        num /= i;

        System.out.println("Primos: " + i);

        if (Thread.interrupted()) {
          return;
        }

      }
    }

  }

}
