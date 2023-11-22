package V4;

import java.util.Random;

public class Productor extends Thread {

  private Deposito d;

  public Productor(Deposito d) {
    this.d = d;
  }

  @Override
  public void run() {
    while (true) {
      int num = new Random().nextInt(100) + 1;
      d.depositar(num);
      try {
        sleep(new Random().nextInt(3000) + 1000);
      } catch (InterruptedException e) {
        return;
      }
    }
  }

}
