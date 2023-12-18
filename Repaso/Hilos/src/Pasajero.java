import java.util.Random;

public class Pasajero implements Runnable {
  private Tren tren;
  private int id;
  private static Random r = new Random();

  public Pasajero(int id, Tren tren) {
    this.tren = tren;
    this.id = id;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(r.nextInt(2000));
        tren.viajar(this);
        //Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
  }

  @Override
  public String toString() {
    return "Pasajero " + Integer.toString(id);
  }
}