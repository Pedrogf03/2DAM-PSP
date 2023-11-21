package V2;

public class Atleta extends Thread {

  private Testigo testigo;
  private int id;

  public Atleta(Testigo t, int id) {
    this.testigo = t;
    this.id = id;
  }

  @Override
  public void run() {
    testigo.quieroCorrer(id);
  }

}
