package version_b;

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
  private int timeout;
  private boolean stop;
  private ServerSocket servidor;
  private List<String> palabras;
  private int intentos;

  public AhorcadoServer() throws AhorcadoException {

    try {
      stop = false;
      Properties conf = new Properties();
      conf.load(new FileInputStream("server.properties"));
      puerto = Integer.parseInt(conf.getProperty("PORT"));
      timeout = Integer.parseInt(conf.getProperty("TIMEOUT"));
      intentos = 6;
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
    try {
      start();
    } catch (AhorcadoException e) {
      System.out.println(e.getMessage());
      return;
    }
  }

  private void start() throws AhorcadoException {
    while (!stop) {
      try (ServerSocket server = new ServerSocket(puerto);) {
        servidor = server;
        servidor.setSoTimeout(timeout);
        while (true) {
          new AhorcadoThread(servidor.accept(), palabras, intentos).start();
        }
      } catch (SocketTimeoutException e) {
        continue;
      } catch (IOException e) {
        throw new AhorcadoException("No se ha podido escuchar en el puerto " + puerto);
      }
    }
  }

}
