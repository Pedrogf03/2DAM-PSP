package objectStreams;

import java.io.Serializable;

public class Datos implements Serializable {

  private int turnoJugador;
  private int numeroJugador;
  private int turnoActual;

  private boolean finDelJuego = false;

  public Datos(int turnoJugador) {
    this.turnoJugador = turnoJugador;
  }

  public Datos(Datos d) {
    this.turnoJugador = d.turnoJugador;
    this.numeroJugador = d.numeroJugador;
    this.turnoActual = d.turnoActual;
    this.finDelJuego = d.finDelJuego;
  }

  public int getTurnoJugador() {
    return turnoJugador;
  }

  public void setTurnoJugador(int turnoJugador) {
    this.turnoJugador = turnoJugador;
  }

  public int getNumeroJugador() {
    return numeroJugador;
  }

  public void setNumeroJugador(int numeroJugador) {
    this.numeroJugador = numeroJugador;
  }

  public boolean isFinDelJuego() {
    return finDelJuego;
  }

  public void setFinDelJuego(boolean finDelJuego) {
    this.finDelJuego = finDelJuego;
  }

  public int getTurnoActual() {
    return turnoActual;
  }

  public void setTurnoActual(int turnoActual) {
    this.turnoActual = turnoActual;
  }

  // public void aumentarTurno() {
  //   turnoActual++;
  //   if (turnoActual > ServidorAdivina.JUGADORES) {
  //     turnoActual = 1;
  //   }
  // }

}
