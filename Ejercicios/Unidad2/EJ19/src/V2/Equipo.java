package V2;

import java.util.ArrayList;

public class Equipo extends Thread {

  private Testigo t;
  private ArrayList<Atleta> atletas;
  private int idEquipo;
  private long momentoLlegada = 0;
  private long momentoSalida = 0;

  public int getIdEquipo() {
    return idEquipo;
  }

  public Equipo(int id) {
    this.idEquipo = id;
    t = new Testigo();
    atletas = new ArrayList<>();
    crearAtletas();
  }

  private void crearAtletas() {

    for (int i = 1; i <= 4; i++)
      atletas.add(new Atleta(t, i, this));

  }

  @Override
  public void run() {
    momentoSalida = System.currentTimeMillis();
    ;
    for (Atleta atleta : atletas)
      atleta.start();
    try {
      for (Atleta atleta : atletas)
        atleta.join();
    } catch (InterruptedException e) {
    }
    momentoLlegada = System.currentTimeMillis();
  }

  public long getTiempoTotal() {
    return momentoLlegada - momentoSalida;
  }

}
