import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ServidorAdivina {

  private ServerSocket server;

  final static int SERVERPORT = 4444;
  final static int JUGADORES = 5;

  private Dealer dealer;
  private ArrayList<JugadorThread> jugadores;
  private int secretNum;

  // Constructor to allocate a ServerSocket listening at the given port.
  public ServidorAdivina() {
    try {
      server = new ServerSocket(SERVERPORT);
      System.out.println("Esperando jugadores(0/" + JUGADORES + ")");
    } catch (IOException e) {
      e.printStackTrace();
    }
    secretNum = new Random().nextInt(101);
    dealer = new Dealer(secretNum);
    jugadores = new ArrayList<>();
  }

  // Start listening.
  private void listen() {
    for (int i = 1; i <= JUGADORES; i++) {
      try {
        Socket socket = server.accept();
        jugadores.add(new JugadorThread(socket, i, dealer));
        System.out.println("Esperando jugadores(" + i + "/" + JUGADORES + ")");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    for (JugadorThread j : jugadores) {
      j.start();
    }

    System.out.println("JUEGO INICIADO");
    System.out.println("El número secreto es: " + secretNum);

  }

  public static void main(String[] args) {
    new ServidorAdivina().listen();
  }

}