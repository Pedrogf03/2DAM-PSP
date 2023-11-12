import java.util.Random;

public class Arbitro {

  public int num; // Numero  a adivinar.
  private int turno; // Turno.
  private int jugadores; // Numero total de jugadores.
  private boolean terminado = false; // Se ha acabado la partida.

  public Arbitro(int jugadores) {
    if (jugadores == 0) {
      this.terminado = true;
    } else {
      this.jugadores = jugadores;
      this.num = new Random().nextInt(10) + 1;
      this.turno = 1;
    }
  }

  public int getTurno() {
    return this.turno;
  }

  public boolean isTerminado() {
    return this.terminado;
  }

  public synchronized void checkPlay(int idJug, int numJug) {
    if (numJug == this.num) {
      this.terminado = true;
      System.out.println("\tJugador " + idJug + " gana, adivinó el número!!!");
    } else {
      this.turno++;
      if (this.turno == (jugadores + 1)) {
        this.turno = 1;
      }
      System.out.println("\tLe toca a Jug" + this.turno);
    }
  }

}
