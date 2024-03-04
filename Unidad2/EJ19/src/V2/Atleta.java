package V2;

public class Atleta extends Thread {

  private Testigo testigo;
  private int id;
  private Equipo equipo;

  public Atleta(Testigo t, int id, Equipo equipo) {
    this.testigo = t;
    this.id = id;
    this.equipo = equipo;
  }

  @Override
  public void run() {
    testigo.quieroCorrer(id, equipo);
  }

}
