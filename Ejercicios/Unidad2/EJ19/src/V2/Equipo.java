package V2;

import java.util.ArrayList;

public class Equipo {

  Testigo t;
  ArrayList<Atleta> atletas;

  public Equipo() {
    t = new Testigo();
    crearAtletas();
  }

  private void crearAtletas() {

    Atleta a1 = new Atleta(t, 1);
    Atleta a2 = new Atleta(t, 2);
    Atleta a3 = new Atleta(t, 3);
    Atleta a4 = new Atleta(t, 4);

    atletas.add(a1);
    atletas.add(a2);
    atletas.add(a3);
    atletas.add(a4);

  }

}
