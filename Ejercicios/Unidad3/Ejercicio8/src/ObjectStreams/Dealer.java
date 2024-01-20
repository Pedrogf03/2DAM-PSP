package objectStreams;

import java.util.Random;

public class Dealer {

  private int numeroSecreto;
  private int turnoActual;

  private boolean finDelJuego = false;

  public Dealer() {
    numeroSecreto = new Random().nextInt(101);
    turnoActual = 1;
  }

  public int getNumSecreto() {
    return numeroSecreto;
  }

  public int getTurnoActual() {
    return turnoActual;
  }

  public void setTurnoActual(int turnoActual) {
    this.turnoActual = turnoActual;
  }

  public synchronized int comprobarNumero(int numero, int turnoJugador) throws InterruptedException {

    while (turnoJugador != turnoActual) {
      esperarTurno(turnoJugador);
    }

    turnoActual++;
    if (turnoActual > ServidorAdivina.JUGADORES) {
      turnoActual = 1;
    }

    if (numero > numeroSecreto) {
      notifyAll();
      return -1;
    } else if (numero < numeroSecreto) {
      notifyAll();
      return 1;
    } else {
      finDelJuego = true;
      notifyAll();
      return 0;
    }

  }

  public synchronized void esperarTurno(int turnoJugador) throws InterruptedException {
    if (turnoJugador != turnoActual) {
      wait();
    }
  }

  public boolean isFinDelJuego() {
    return finDelJuego;
  }

  public void setFinDelJuego(boolean finDelJuego) {
    this.finDelJuego = finDelJuego;
  }

}
