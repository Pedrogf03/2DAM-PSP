import java.util.Random;

public class Maquinista implements Runnable {
  private Tren tren;
  private String nombre;
  private Random r;

  public Maquinista(String nombre, Tren tren) {
    this.nombre = nombre;
    this.tren = tren;
    this.r = new Random();
  }

  @Override
  public void run() {
    try {
      int n = tren.getViajes();
      for (int i = 0; i < n; i++) {
        tren.empezarViaje();
        Thread.sleep(r.nextInt(1000));
        tren.finalizarViaje();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String toString() {
    return nombre;
  }
}