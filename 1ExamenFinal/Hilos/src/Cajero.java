import java.util.Random;

public class Cajero extends Thread {
  private Supermercado s;

  public Cajero(Supermercado s) {
    this.s = s;
  }

  public void run() {
    Random r = new Random();
    boolean fin = false;
    while (!fin) {
      try {
        s.cobrar();
        Thread.sleep(r.nextInt(200));
      } catch (InterruptedException e) {
        fin = true;
      }
    }
    System.out.println("El cajero se va a su casa");
  }
}