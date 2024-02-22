package version_d;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
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

  public AhorcadoServer() throws AhorcadoException {

    try {
      stop = false;
      Properties conf = new Properties();
      conf.load(new FileInputStream("server.properties"));
      puerto = Integer.parseInt(conf.getProperty("PORT"));
      maxplayers = Integer.parseInt(conf.getProperty("MAXPLAYERS"));
      palabras = Arrays.asList(conf.getProperty("WORDS").split(","));
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
    while (!stop) {
      try (ServerSocket server = new ServerSocket(puerto);) {
        servidor = server;
        while (!stop) {
          Game partida = new Game(palabras, maxplayers);
          for (int i = 0; i < maxplayers; i++) {
            new JugadorThread(servidor.accept(), i + 1, partida, new AhorcadoProtocol()).start();
          }
        }
      } catch (SocketTimeoutException e) {
        continue;
      } catch (IOException e) {
        System.out.println("No se ha podido escuchar en el puerto " + puerto);
      }
    }
  }

}
