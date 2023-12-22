import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

  Socket socket;
  private int numCliente;

  public ClientThread(Socket socket, int numCliente) {
    this.socket = socket;
    this.numCliente = numCliente;
  }

  @Override
  public void run() {

    try (DataInputStream entrada = new DataInputStream(socket.getInputStream());
        DataOutputStream salida = new DataOutputStream(socket.getOutputStream())) {

      System.out.println("Conexión acceptada: " + socket);

      String palabra = entrada.readUTF();
      System.out.println("Cliente" + numCliente + ": " + palabra);
      String respuesta = palabra.toUpperCase();
      System.out.println("Respuesta a cliente" + numCliente + ": " + respuesta);
      salida.writeUTF(respuesta);

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }

  }

}