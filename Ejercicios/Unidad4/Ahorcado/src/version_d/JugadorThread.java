package version_d;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class JugadorThread extends Thread {

  private Socket socket = null;
  private Game partida;
  private int turno;
  private AhorcadoProtocol protocolo;
  private DataInputStream in = null;
  private DataOutputStream out = null;

  public JugadorThread(Socket socket, int turno, Game p, AhorcadoProtocol protocol) {
    this.socket = socket;
    this.partida = p;
    this.turno = turno;
    this.protocolo = protocol;
  }

  @Override
  public void run() {
    try (DataInputStream entrada = new DataInputStream(socket.getInputStream()); DataOutputStream salida = new DataOutputStream(socket.getOutputStream());) {

      String input, output;

      in = entrada;
      out = salida;

      partida.esperarJugadores();

      // status: waiting_game -> playing
      output = protocolo.procesarMensaje(null);
      out.writeUTF(output); // bienvenido
      out.flush();

      while (!partida.isFin()) {

        int turnoActual = partida.getTurno();

        if (turnoActual == turno) {
          // status: playing -> checking
          output = protocolo.procesarMensaje(null);
          out.writeUTF(output + ";true"); // playing;true (true porque es su turno)
          out.flush();

          input = in.readUTF();
          String resultadoJugada = partida.processGame(input);
          // status: cheking -> playing || if(output.contains("win" or "lose")) then status: cheking -> end
          output = protocolo.procesarMensaje(resultadoJugada);
          salida.writeUTF(output + ";" + turnoActual); // resultado de procesar la jugada
          salida.flush();
        } else {
          // status: playing -> checking
          output = protocolo.procesarMensaje(null);
          out.writeUTF(output + ";false;" + turnoActual); // playing;false;<turnoActual> (false porque NO es su turno)
          out.flush();
          partida.esperarTurno(turno);
          // status: checking -> playing || if(output.contains("win" or "lose")) then status: checking -> end
          output = protocolo.procesarMensaje(partida.getResultadoAnterior());
          salida.writeUTF(output + ";" + turnoActual); // resultado de procesar la jugada anterior
          salida.flush();
        }

        if (output.contains("win") || output.contains("lose")) {
          input = in.readUTF();

          if (input.equalsIgnoreCase("N")) {
            partida.finalizar();
          }

          // Cada jugador toma una decisión, pero para continuar se deberá esperar a que el resto también tome la suya.
          // En el caso de que todos digan que si quieren continuar jugando, se reiniciará el juego, pero con que sólo
          // un único jugador diga que no quiere continuar, la partida acabará.
          partida.esperarDecision();

          if (partida.isFin()) {
            output = protocolo.procesarMensaje("N");
          } else {
            output = protocolo.procesarMensaje("S");
            partida.resetGame();
          }

          salida.writeUTF(output); // Se le comunica al usuario la decisión tomada.
          salida.flush();

        }

      }

    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("No se puede conectar con el cliente");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
