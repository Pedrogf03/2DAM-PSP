package V3;

import java.util.Random;

public class Consumidor extends Thread {

  private Deposito d;

  public Consumidor(Deposito d) {
    this.d = d;
  }

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      d.sacar();
      try {
        sleep(new Random().nextInt(3000) + 1000);
      } catch (InterruptedException e) {
      }
    }
  }

}
