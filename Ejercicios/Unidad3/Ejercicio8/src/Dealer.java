public class Dealer {

  private int numSecreto;
  private int turno = 1;

  private boolean finPartida = false;

  private int numJug;

  public Dealer(int num) {
    this.numSecreto = num;
  }

  public int comprobarNumero(int numJugado, int turnoJug) throws InterruptedException {

    if (numSecreto < numJugado) {
      return -1;
    } else if (numSecreto > numJugado) {
      return 1;
    } else {
      return 0;
    }

  }

  public int getNumJug() {
    return numJug;
  }

  public void setNumJug(int numJug) {
    this.numJug = numJug;
  }

  public boolean isFinPartida() {
    return finPartida;
  }

  public void setFinPartida(boolean finPartida) {
    this.finPartida = finPartida;
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
