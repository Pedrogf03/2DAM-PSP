package V2;

import java.util.ArrayList;

public class Equipo extends Thread {

  private Testigo t;
  public ArrayList<Atleta> atletas;
  int id;

  public Equipo(int id) {
    this.id = id;
    t = new Testigo();
    atletas = new ArrayList<>();
    crearAtletas();
  }

  private void crearAtletas() {

    for (int i = 1; i <= 4; i++)
      atletas.add(new Atleta(t, i, id));

  }

  @Override
  public void run() {
    for (Atleta atleta : atletas)
      atleta.start();
  }

  public long getFechaLlegada() {
    return t.fechaLlegada;
  }

}