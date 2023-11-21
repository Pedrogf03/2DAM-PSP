package V2;

public class Atleta extends Thread {

  private Testigo testigo;
  private int id;
  private int idEquipo;

  public Atleta(Testigo t, int id, int idEquipo) {
    this.testigo = t;
    this.id = id;
    this.idEquipo = idEquipo;
  }

  @Override
  public void run() {
    testigo.quieroCorrer(id, idEquipo);
  }

}
