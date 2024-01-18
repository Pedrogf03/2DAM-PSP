package objectStreams;

import java.io.Serializable;

public class Datos implements Serializable {

  private int turnoJugador;
  private int numeroJugador;

  private boolean finDelJuego = false;

  public Datos(int turnoJugador) {
    this.turnoJugador = turnoJugador;
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

}
