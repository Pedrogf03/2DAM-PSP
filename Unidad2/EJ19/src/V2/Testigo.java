package V2;

import java.util.Random;

public class Testigo {

  private int orden;

  public Testigo() {
    this.orden = 1;
  }

  public synchronized void quieroCorrer(int id, Equipo equipo) {
    try {

      while (id != orden)
        wait();

      long salida = System.currentTimeMillis();

      System.out.println("Sale: Atleta " + id + ", equipo: " + equipo.getIdEquipo());
      Thread.sleep(new Random().nextInt(2000) + 9000);

      long llegada = System.currentTimeMillis();

      System.out.println("Llega: Atleta " + id + ", equipo: " + equipo.getIdEquipo() + ", tiempo: "
          + (llegada - salida) / 1000 + "\"");

      orden++;
      notifyAll();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
