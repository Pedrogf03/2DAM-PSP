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
      System.out.println("El jugador" + turnoActual + " está pensando su número...");
      wait();
      System.out.println("El jugador" + turnoActual + " ha puesto el número " + numero);
    }

    turnoActual++;
    if (turnoActual > ServidorAdivina.JUGADORES) {
      turnoActual = 1;
    }

    System.out.println("Tu turno, jugador" + turnoJugador);

    if (numero > numeroSecreto) {
      System.out.println("El numero secreto es menor que " + numero);
      notifyAll();
      return -1;
    } else if (numero < numeroSecreto) {
      System.out.println("El numero secreto es mayor que " + numero);
      notifyAll();
      return 1;
    } else {
      System.out.println("El jugador" + turnoJugador + " ha adivinado el número secreto");
      notifyAll();
      return 0;
    }

  }

  public boolean isFinDelJuego() {
    return finDelJuego;
  }

  public void setFinDelJuego(boolean finDelJuego) {
    this.finDelJuego = finDelJuego;
  }

}
