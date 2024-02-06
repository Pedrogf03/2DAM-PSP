package version_c;

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
    try (DataInputStream entrada = new DataInputStream(socket.getInputStream()); DataOutputStream salida = new DataOutputStream(socket.getOutputStream());) {

      String input, output;

      AhorcadoProtocol protocol = new AhorcadoProtocol(palabras, intentos);

      // Bienvenida
      output = protocol.processInput(null);
      salida.writeUTF(output);
      salida.flush();

      // Letra: 
      output = protocol.processInput(null);
      salida.writeUTF(output);
      salida.flush();

      while ((input = entrada.readUTF()) != null) {
        output = protocol.processInput(input);
        salida.writeUTF(output);
        salida.flush();

        String[] res = output.split(";");

        if (res[0].equals("bye")) {
          break;
        } else if (res[0].equals("checking") && (res[1].equals("win") || res[1].equals("lose"))) {
          continue;
        } else if (!res[0].equals("playing")) {
          output = protocol.processInput(null);
          salida.writeUTF(output);
        }

      }

    } catch (IOException e) {
      try {
        throw new AhorcadoException("No se puede conectar con el cliente.");
      } catch (AhorcadoException aex) {
        System.out.println(aex.getMessage());
      }
    }
  }
}
