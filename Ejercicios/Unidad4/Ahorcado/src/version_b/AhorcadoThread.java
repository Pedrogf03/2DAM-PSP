package version_b;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class AhorcadoThread extends Thread {

  private Socket socket = null;
  private List<String> palabras;
  private int intentos;

  public AhorcadoThread(Socket socket, List<String> palabras, int intentos) {
    this.socket = socket;
    this.palabras = palabras;
    this.intentos = intentos;
  }

  @Override
  public void run() {
    try (
        DataInputStream entrada = new DataInputStream(socket.getInputStream());
        DataOutputStream salida = new DataOutputStream(socket.getOutputStream());) {

      String input, output;

      AhorcadoProtocol protocol = new AhorcadoProtocol(palabras, intentos);

      output = protocol.processInput(null);
      salida.writeUTF(output);
      salida.flush();

      output = protocol.processInput(null);
      salida.writeUTF(output);

      while ((input = entrada.readUTF()) != null) {
        output = protocol.processInput(input);
        salida.writeUTF(output);
        salida.flush();

        if (output.contains("¿quieres jugar de nuevo? (S/N)")) {
          continue;
        }

        if (output.equals("¡Adios!"))
          break;

        output = protocol.processInput(input);
        salida.writeUTF(output);

      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
