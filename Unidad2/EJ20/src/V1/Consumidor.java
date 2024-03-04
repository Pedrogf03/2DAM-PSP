package V1;

import java.util.Random;

public class Consumidor extends Thread {

  private Deposito d;

  public Consumidor(Deposito d) {
    this.d = d;
  }

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      descomponerPrimos(d.sacar());
      try {
        sleep(new Random().nextInt(3000) + 1000);
      } catch (InterruptedException e) {
      }
    }
  }

  public void descomponerPrimos(int num) {
    long numero = num;
    System.out.print("Descomposición de " + num + ": ");
    for (int i = 2; i < numero; i++) {
      while (num % i == 0) {
        num = num / i;
        System.out.print(i + " ");
      }
    }

    System.out.println();

  }

}
