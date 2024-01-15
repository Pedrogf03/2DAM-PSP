package ObjectStreams;

public class Dealer {

  private int num;
  private int turno = 1;

  public Dealer(int num) {
    this.num = num;
  }

  public int comprobarNumero(int numJugado, int turnoJug) throws InterruptedException {

    if (num < numJugado) {
      return -1;
    } else if (num > numJugado) {
      return 1;
    } else {
      return 0;
    }

  }

  public int getTurno() {
    return turno;
  }

  public synchronized void waitForNum() throws InterruptedException {
    wait();
  }

  public synchronized void notificarNum() throws InterruptedException {
    notifyAll();
  }

  public synchronized void aumentarTurno() {
    turno++;
    if (turno > ServidorAdivina.JUGADORES) {
      turno = 1;
    }
  }

}
