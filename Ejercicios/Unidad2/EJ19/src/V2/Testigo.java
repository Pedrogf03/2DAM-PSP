package V2;

import java.util.Random;

public class Testigo {

  private int orden;
  public long momentoLlegada = 0;

  public Testigo() {
    this.orden = 1;
  }

  public synchronized void quieroCorrer(int id, int idEquipo) {
    try {

      while (id != orden)
        wait();

      System.out.println("Sale: Atleta " + id + ", equipo: " + idEquipo);
      Thread.sleep(new Random().nextInt(2000) + 9000);
      System.out.println("Llega: Atleta " + id + ", equipo: " + idEquipo);

      momentoLlegada = System.currentTimeMillis();

      orden++;
      notifyAll();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
