public class Hilo2 extends Thread {

  private int numero;
  private int nFactores;

  public Hilo2(int n, int nFactores) {
    this.numero = n;
    this.nFactores = nFactores;
  }

  @Override
  public void run() {

    int num = numero;

    for (int i = 2; i < numero; i++) {

      while (num % i == 0) {

        num /= i;

        System.out.println("Primos: " + i);

        this.nFactores--;

        try {
          sleep(1000);
        } catch (InterruptedException e) {
          if (this.nFactores == 0) {
            break;
          } else {
            continue;
          }
        }

        if (this.nFactores == 0) {
          break;
        }

      }

      if (this.nFactores == 0) {
        break;
      }

    }

  }
}
