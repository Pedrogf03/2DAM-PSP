import java.util.Random;

public class Jugador extends Thread {

  private int idJug;
  private Arbitro arbitro;

  public Jugador(int idJug, Arbitro arbitro) {
    this.arbitro = arbitro;
    this.idJug = idJug;
  }

  @Override
  public void run() {

    while (!arbitro.isTerminado()) {

      if (arbitro.getTurno() == this.idJug) {
        int numJug = new Random().nextInt(10) + 1;
        System.out.println("Jugador" + this.idJug + " dice: " + numJug);
        arbitro.checkPlay(this.idJug, numJug);
      }

    }
  }

}
