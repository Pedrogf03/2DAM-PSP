package version_c;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Partida {

  private int currentTurno;
  private StringBuffer palabraJugadores;
  int jugadores;
  private String entrada;
  private List<String> palabras;
  private String currentPalabra;
  private List<String> letrasUsadas;
  private int currentIntento;

  private String resultadoAnterior;

  private static final int MAXPLAYERS = 2;
  private static final int MAXINTENTOS = 6;

  public Partida(List<String> palabras) {

    this.palabras = palabras;
    resetGame();
    this.jugadores = 0;

  }

  public int getTurno() {
    return currentTurno;
  }

  public synchronized void esperarJugadores() throws InterruptedException {
    jugadores++;
    while (jugadores < MAXPLAYERS) {
      wait();
    }
    notifyAll();
  }

  public synchronized void esperarTurno(int turno) throws InterruptedException {
    if (turno != currentTurno) {
      wait();
    }
  }

  public synchronized String processGame(String s) {

    currentTurno++;
    if (currentTurno > MAXPLAYERS) {
      currentTurno = 1;
    }

    int longitud = s.length();
    String msg = "";

    if (longitud == 1) {

      letrasUsadas.add(s);

      boolean isC = false;

      char c = s.toLowerCase().charAt(0);
      for (int i = 0; i < currentPalabra.length(); i++) {
        if (currentPalabra.toLowerCase().charAt(i) == c) {
          palabraJugadores.setCharAt(i, c);
          isC = true;
        }
      }

      if (!isC) {
        currentIntento++;
      }

      // El mensaje contiene la longitud, si el caracter esta o no en la palabra, 
      msg = "letra" + ";" + isC + ";";
      // la lista de letras usadas
      for (String letra : letrasUsadas) {
        msg += letra + ",";
      }
      // la palabra que estan jugando y los intentos gastados.
      msg += ";" + palabraJugadores.toString() + ";" + currentIntento;

    } else {

      boolean isP = currentPalabra.equalsIgnoreCase(s);

      if (!isP) {
        currentIntento++;
      }

      // El mensaje contiene la longitud, si la palabra es correcta o no, 
      msg = "palabra" + ";" + isP + ";";
      // la lista de letras usadas
      for (String letra : letrasUsadas) {
        msg += letra + ",";
      }
      // la palabra que estan jugando y los intentos gastados.
      msg += ";" + palabraJugadores.toString() + ";" + currentIntento;
    }

    if (currentIntento == MAXINTENTOS) {
      msg = "lose;" + currentPalabra;
    }

    notifyAll();

    resultadoAnterior = msg;

    return msg;

  }

  public String getResultadoAnterior() {
    return resultadoAnterior;
  }

  private void resetGame() {
    this.currentPalabra = palabras.get(new Random().nextInt(palabras.size()));
    this.currentIntento = 0;
    String s = "";
    for (int i = 0; i < currentPalabra.length(); i++) {
      s += "_";
    }
    this.palabraJugadores = new StringBuffer(s);
    this.letrasUsadas = new ArrayList<>();
    this.currentTurno = new Random().nextInt(MAXPLAYERS) + 1;
    // this.currentTurno = 2;
  }

}
