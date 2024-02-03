package version_b;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AhorcadoProtocol {

  private int intentos;

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
      respuesta = "waiting_game";
      estado = Estado.PLAYING;
    } else if (estado == Estado.PLAYING) {
      respuesta = "playing";
      estado = Estado.CHECKING;
    } else if (estado == Estado.CHECKING) {

      int longitud = entrada.length();
      boolean resultado = processGame(entrada);

      if (longitud == 1) {
        letrasUsadas.add(entrada);
        if (resultado) {
          respuesta = "cheking;letra;true;";
        } else {
          respuesta = "cheking;letra;false;";
          currentIntento++;
        }
        for (String s : letrasUsadas) {
          respuesta = respuesta + s + ",";
        }

        respuesta = respuesta.substring(0, respuesta.length() - 1);
        respuesta = respuesta + ";" + palabraJugador.toString() + ";" + currentIntento;
        estado = Estado.PLAYING;

        if (currentPalabra.equalsIgnoreCase(palabraJugador.toString())) {
          respuesta = "cheking;win";
          estado = Estado.END;
        }

      } else {
        if (resultado) {
          respuesta = "cheking;win";
          estado = Estado.END;
        } else {
          currentIntento++;
          respuesta = "cheking;palabra;false;";
          for (String s : letrasUsadas) {
            respuesta = respuesta + s + ",";
          }
          respuesta = respuesta.substring(0, respuesta.length() - 1);
          respuesta = respuesta + ";" + palabraJugador.toString() + ";" + currentIntento;
          estado = Estado.PLAYING;
        }
      }

      if (currentIntento == intentos) {
        respuesta = "cheking;lose;" + currentPalabra;
        estado = Estado.END;
      }

    } else if (estado == Estado.END) {
      resetGame();
      if (entrada.equalsIgnoreCase("S")) {
        respuesta = "playing";
        estado = Estado.PLAYING;
      } else if (entrada.equalsIgnoreCase("N")) {
        respuesta = "bye";
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
