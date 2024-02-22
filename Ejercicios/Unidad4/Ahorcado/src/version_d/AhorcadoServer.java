package version_d;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class AhorcadoServer implements Runnable {

  private int puerto;
  private boolean stop;
  private ServerSocket servidor;
  private List<String> palabras;
  private int maxplayers;

  private List<Socket> jugadores;

  public AhorcadoServer() throws AhorcadoException {

    try {
      stop = false;
      Properties conf = new Properties();
      conf.load(new FileInputStream("server.properties"));
      puerto = Integer.parseInt(conf.getProperty("PORT"));
      maxplayers = Integer.parseInt(conf.getProperty("MAXPLAYERS"));
      palabras = Arrays.asList(conf.getProperty("WORDS").split(","));
      jugadores = new ArrayList<>();
    } catch (IOException e) {
      throw new AhorcadoException("Ha ocurrido un error al leer las propiedades del servidor");
    }

  }

  public static void main(String[] args) {

    try {
      AhorcadoServer as = new AhorcadoServer();
      Thread server = new Thread(as);
      server.start();

      Scanner sc = new Scanner(System.in);
      System.out.print("Pulse s para parar el servidor ");
      String res = sc.nextLine();
      while (!res.equalsIgnoreCase("s")) {
        res = sc.nextLine();
      }

      as.stop = true;
      sc.close();
    } catch (AhorcadoException e) {
      System.out.println(e.getMessage());
    }

  }

  @Override
  public void run() {
    start();
  }

  private void start() {
    try (ServerSocket server = new ServerSocket(puerto);) {
      servidor = server;
      while (!stop) {
        Socket cliente = servidor.accept(); // Se conecta un cliente
        new Thread(() -> { // Se lanza un hilo el cual lanzara un hilo para el inicio de sesion
          LoginThread login = new LoginThread(cliente, new LoginProtocol());
          login.start();
          try {
            login.join();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          if (login.succesful()) {
            jugadores.add(cliente); // Se guarda el socket
            if (jugadores.size() == 3) { // Si el numero de jugadores conectados es tres, se crea la partida y se lanzan los 3 jugadores
              Game partida = new Game(palabras, maxplayers);
              int contador = 1;
              for (Socket jugador : jugadores) {
                new JugadorThread(jugador, contador, partida, new AhorcadoProtocol()).start();
                contador++;
              }
              contador = 0;
              jugadores.clear(); // Se limpia el arraylist para esperar a otros tres nuevos jugadores
            }
          }
        }).start();
      }
    } catch (IOException e) {
      System.out.println("No se ha podido escuchar en el puerto " + puerto);
    }
  }

}
