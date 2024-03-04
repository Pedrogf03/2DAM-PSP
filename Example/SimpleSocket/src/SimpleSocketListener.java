import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A simple server socket listener that listens to port number 8888, and prints
 * whatever received to the console.
 */
public class SimpleSocketListener {

  ServerSocket server;
  int serverPort = 8888;

  // Constructor to allocate a ServerSocket listening at the given port.
  public SimpleSocketListener() {
    try {
      server = new ServerSocket(serverPort);
      System.out.println("ServerSocket: " + server);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Start listening.
  private void listen() {
    while (true) { // run until you terminate the program
      try {
        // Wait for connection. Block until a connection is made.
        Socket socket = server.accept();
        System.out.println("Socket: " + socket);
        InputStream in = socket.getInputStream();
        int byteRead;
        // Block until the client closes the connection (i.e., read() returns -1)
        while ((byteRead = in.read()) != -1) {
          System.out.println("leyendo->" + (char) byteRead);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    new SimpleSocketListener().listen(); // Start the server and listening
  }
}
