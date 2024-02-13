package version_c;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class JugadorThread extends Thread {

  private Socket socket = null;
  private Partida partida;
  private int turno;
  private AhorcadoProtocol protocolo;

  public JugadorThread(Socket socket, int turno, Partida p, AhorcadoProtocol protocol) {
    this.socket = socket;
    this.partida = p;
    this.turno = turno;
    this.protocolo = protocol;
  }

  @Override
  public void run() {
    try (DataInputStream entrada = new DataInputStream(socket.getInputStream()); DataOutputStream salida = new DataOutputStream(socket.getOutputStream());) {

      String input, output;

      // Esperar jugadores
      output = protocolo.procesarMensaje(null);
      salida.writeUTF(output);
      salida.flush();

      partida.esperarJugadores();

      // Bienvenida
      if (partida.getTurno() == turno) {
        output = protocolo.procesarMensaje("tu_turno");
      } else {
        output = protocolo.procesarMensaje("otro_turno");
      }
      salida.writeUTF(output);
      salida.flush();

      //Tu turno / otro turno
      do {
        if (partida.getTurno() == turno) {
          output = protocolo.procesarMensaje("tu_turno");
        } else {
          output = protocolo.procesarMensaje("");
        }

        if (output.equals("otro_turno")) {
          output += ";" + partida.getTurno();
        }

        if (output.equals("playing")) {
          output += ";" + partida.getResultadoAnterior();
        }
        salida.writeUTF(output);
        salida.flush();
        if (output.contains("otro_turno")) {
          partida.esperarTurno(turno);
        }
      } while (output.contains("otro_turno"));

      while ((input = entrada.readUTF()) != null) {
        output = protocolo.procesarMensaje(partida.processGame(input));
        salida.writeUTF(output);
        salida.flush();

        //Tu turno / otro turno
        do {
          if (partida.getTurno() == turno) {
            output = protocolo.procesarMensaje("tu_turno");
          } else {
            output = protocolo.procesarMensaje("");
          }

          if (output.equals("otro_turno")) {
            output += ";" + partida.getTurno();
          }

          if (output.equals("playing")) {
            output += ";" + partida.getResultadoAnterior();
          }

          salida.writeUTF(output);
          salida.flush();
          if (output.contains("otro_turno")) {
            partida.esperarTurno(turno);
          }
        } while (output.contains("otro_turno"));
      }

    } catch (IOException e) {
      System.out.println("No se puede conectar con el cliente");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
