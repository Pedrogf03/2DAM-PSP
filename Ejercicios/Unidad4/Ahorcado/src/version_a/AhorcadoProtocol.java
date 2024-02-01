package version_a;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AhorcadoProtocol {

  private enum Estado {
    WAITING_GAME, PLAYING, WAITING_LETTER, CHECKING, END
  }

  private int intentos = 6;

  private List<String> palabras;
  private List<String> letrasUsadas;

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

      try {
        Integer.parseInt(entrada);
        estado = Estado.PLAYING;
        respuesta = "Introduce una letra o palabra";
      } catch (NumberFormatException e) {
        int longitud = entrada.length();
        boolean resultado = processGame(entrada);

        if (longitud == 1) {
          letrasUsadas.add(entrada);
          if (resultado) {
            respuesta = "Esa letra está en la palabra secreta\nLetras usadas: ";
          } else {
            respuesta = "Esa letra no está en la palabra secreta\nLetras usadas: ";
            currentIntento++;
          }
          for (String s : letrasUsadas) {
            respuesta = respuesta + s + ", ";
          }
          respuesta = respuesta + "\n" + palabraJugador.toString();
          estado = Estado.PLAYING;

          if (currentPalabra.equalsIgnoreCase(palabraJugador.toString())) {
            respuesta = "¡Has adivinado la palabra!, ¿quieres jugar de nuevo? (S/N)";
            estado = Estado.END;
          }

        } else {
          if (resultado) {
            respuesta = "¡Has adivinado la palabra!, ¿quieres jugar de nuevo? (S/N)";
            estado = Estado.END;
          } else {
            respuesta = "Esa no es la palabra secreta\nLetras usadas: ";
            for (String s : letrasUsadas) {
              respuesta = respuesta + s + ", ";
            }
            respuesta = respuesta + "\n" + palabraJugador.toString();
            estado = Estado.PLAYING;
            currentIntento++;
          }
        }

        if (currentIntento == intentos) {
          respuesta = "Lo siento, te has quedado sin intentos, la palabra era " + currentPalabra
              + " ¿quieres jugar de nuevo? (S/N)";
          estado = Estado.END;
        }
      }

    } else if (estado == Estado.END) {
      resetGame();
      if (entrada.equalsIgnoreCase("S")) {
        respuesta = "Se ha escogido una nueva palabra";
        estado = Estado.PLAYING;
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
    letrasUsadas = new ArrayList<>();
  }

  private boolean processGame(String s) {

    int longitud = s.length();

    if (longitud == 1) {

      boolean isC = false;

      char c = s.toLowerCase().charAt(0);
      for (int i = 0; i < currentPalabra.length(); i++) {
        if (currentPalabra.toLowerCase().charAt(i) == c) {
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
