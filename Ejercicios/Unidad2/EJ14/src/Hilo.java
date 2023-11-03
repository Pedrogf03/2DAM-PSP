public class Hilo extends Thread {

  int numero;

  public Hilo(int num) {
    this.numero = num;
  }

  @Override
  public void run() {

    int num = numero;

    for (int i = 2; i < numero; i++) {

      try {
        while (num % i == 0) {

          num /= i;

          System.out.println("Primos: " + i);

          sleep(500);

        }
      } catch (InterruptedException e) {
        break;
      }

    }

  }

}
