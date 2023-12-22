import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

  ServerSocket server;
  final static int SERVERPORT = 4444;

  // Constructor to allocate a ServerSocket listening at the given port.
  public Servidor() {
    try {
      server = new ServerSocket(SERVERPORT);
      System.out.println("Escuchando: " + server);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Start listening.
  private void listen() {
    for (int i = 1; i <= 3; i++) {
      try {
        Socket socket = server.accept();
        // Start a new thread for each client to perform block-IO operations.
        new ClientThread(socket, i).start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  public static void main(String[] args) {
    new Servidor().listen();
  }

}