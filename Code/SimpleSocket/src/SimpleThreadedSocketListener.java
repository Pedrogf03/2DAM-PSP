import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A simple server socket listener that listens to port number 8888, and prints
 * whatever received to the console. It starts a thread for each connection to
 * perform IO operations.
 */
public class SimpleThreadedSocketListener {

  ServerSocket server;
  int serverPort = 8888;

  // Constructor to allocate a ServerSocket listening at the given port.
  public SimpleThreadedSocketListener() {
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
        // Start a new thread for each client to perform block-IO operations.
        new ClientThread(socket).start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    new SimpleThreadedSocketListener().listen();
  }

  class ClientThread extends Thread {

    Socket socket;

    public ClientThread(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      InputStream in;
      try {
        in = socket.getInputStream();
        int byteRead;
        // Block until the client closes the connection, results in read() returns -1
        while ((byteRead = in.read()) != -1) {
          System.out.print((char) byteRead);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
