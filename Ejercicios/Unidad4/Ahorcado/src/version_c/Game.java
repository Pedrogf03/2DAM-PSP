package version_c;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

  private int currentTurno;
  private StringBuffer palabraJugadores;
  private int jugadores;
  private int jugadoresDecididos;
  private List<String> palabras;
  private String currentPalabra;
  private List<String> letrasUsadas;
  private int currentIntento;

  private boolean fin = false;

  private String resultadoAnterior;

  private int maxplayers;
  private static final int MAXINTENTOS = 6;

  public Game(List<String> palabras, int maxplayers) {

    this.palabras = palabras;
    this.jugadores = 0;
    this.jugadoresDecididos = 0;
    this.maxplayers = maxplayers;

    resetGame();

  }

  public boolean isFin() {
    return fin;
  }

  public void finalizar() {
    fin = true;
  }

  public int getTurno() {
    return currentTurno;
  }

  public synchronized void esperarJugadores() throws InterruptedException {
    jugadores++;
    while (jugadores < maxplayers) {
      wait();
    }
    notifyAll();
  }

  public synchronized void esperarDecision() throws InterruptedException {
    jugadoresDecididos++;
    while (jugadoresDecididos < maxplayers) {
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
    if (currentTurno > maxplayers) {
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

      // Si la palabra de los jugadores coincide con la secreta, ganan la partida
      if (palabraJugadores.toString().equalsIgnoreCase(currentPalabra)) {
        msg = "win;" + currentPalabra;
      }

    } else {

      boolean isP = currentPalabra.equalsIgnoreCase(s);

      if (!isP) {
        currentIntento++;

        // El mensaje contiene la longitud, si la palabra es correcta o no, 
        msg = "palabra" + ";" + isP + ";";
        // la lista de letras usadas
        if (letrasUsadas.isEmpty()) {
          msg += " ";
        } else {
          for (String letra : letrasUsadas) {
            msg += letra + ",";
          }
        }
        // la palabra que estan jugando y los intentos gastados.
        msg += ";" + palabraJugadores.toString() + ";" + currentIntento;
      } else { // Si la palabra es correcta, ganan la partida
        msg = "win;" + currentPalabra;
      }

    }

    // Si se han quedado sin intentos, pierden la partida
    if (currentIntento == MAXINTENTOS) {
      msg = "lose;" + currentPalabra;
    }

    resultadoAnterior = msg;

    notifyAll();

    return msg;

  }

  public String getResultadoAnterior() {
    return resultadoAnterior;
  }

  public void resetGame() {
    this.currentPalabra = palabras.get(new Random().nextInt(palabras.size()));
    this.currentIntento = 0;
    String s = "";
    for (int i = 0; i < currentPalabra.length(); i++) {
      s += "_";
    }
    this.palabraJugadores = new StringBuffer(s);
    this.letrasUsadas = new ArrayList<>();
    this.currentTurno = new Random().nextInt(maxplayers) + 1;
    this.jugadoresDecididos = 0;
  }

}
