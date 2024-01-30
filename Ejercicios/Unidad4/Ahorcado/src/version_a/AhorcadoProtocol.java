package version_a;

import java.util.List;
import java.util.Random;

public class AhorcadoProtocol {

  private enum Estado {
    WAITING_GAME, PLAYING, WAITING_LETTER, CHECKING, END
  }

  private int intentos = 6;

  private List<String> palabras;

  private Estado estado;
  private String currentPalabra;
  private int currentIntento;
  private StringBuffer palabraJugador;

  public AhorcadoProtocol(List<String> palabras, int intentos) {
    this.palabras = palabras;
    this.intentos = intentos;

    resetGame();
    this.estado = Estado.WAITING_GAME;
  }

  public String processInput(String entrada) {

    String respuesta = null;

    if (estado == Estado.WAITING_GAME) {
      respuesta = "¡Bienvenido al juego del ahorcado!";
      estado = Estado.PLAYING;
    } else if (estado == Estado.PLAYING) {
      respuesta = "Letra: ";
      estado = Estado.CHECKING;
    } else if (estado == Estado.CHECKING) {

      int longitud = entrada.length();

      if (processGame(entrada)) {

        if (longitud == 1) {
          respuesta = "Esa letra está en la palabra secreta";
          estado = Estado.PLAYING;

          if (currentPalabra.equalsIgnoreCase(palabraJugador.toString())) {
            respuesta = "¡Has adivinado la palabra!, ¿quieres jugar de nuevo? (S/N)";
            estado = Estado.END;
          }

        } else {
          respuesta = "¡Has adivinado la palabra!, ¿quieres jugar de nuevo? (S/N)";
          estado = Estado.END;
        }

      } else {

        if (longitud == 1) {
          respuesta = "Esa letra no está en la palabra secreta";
        } else {
          respuesta = "Esa no es la palabra secreta";
        }
        currentIntento++;
        estado = Estado.PLAYING;

        if (currentIntento == intentos) {
          respuesta = "Lo siento, te has quedado sin intentos, ¿quieres jugar de nuevo? (S/N)";
          estado = Estado.END;
        }

      }

    } else if (estado == Estado.END) {
      resetGame();
      if (entrada.equalsIgnoreCase("S")) {
        respuesta = "Letra: ";
        estado = Estado.CHECKING;
      } else {
        respuesta = "¡Adios!";
        estado = Estado.WAITING_GAME;
      }
    }

    return respuesta;

  }

  private void resetGame() {
    this.currentPalabra = palabras.get(new Random().nextInt(palabras.size()));
    this.currentIntento = 0;
    String s = "";
    for (int i = 0; i < currentPalabra.length(); i++) {
      s += "_";
    }
    this.palabraJugador = new StringBuffer(s);
  }

  private boolean processGame(String s) {

    int longitud = s.length();

    if (longitud == 1) {

      boolean isC = false;

      char c = s.charAt(0);
      for (int i = 0; i < currentPalabra.length(); i++) {
        if (currentPalabra.charAt(i) == c) {
          palabraJugador.setCharAt(i, c);
          isC = true;
        }
      }

      return isC;

    } else {
      return currentPalabra.equalsIgnoreCase(s);
    }

  }

}
