public class Dealer {

  private int num;
  private int turno = 1;

  public Dealer(int num) {
    this.num = num;
  }

  public synchronized int comprobarNumero(int numJugado, int turnoJug) throws InterruptedException {

    while (turno != turnoJug) {
      wait();
    }

    turno++;
    if (turno > ServidorAdivina.JUGADORES) {
      turno = 1;
    }

    notifyAll();

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

  public synchronized void waitTurno(int turnoJug) throws InterruptedException {
    wait();
  }

}
