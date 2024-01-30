package version_a;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class AhorcadoServer {

  private int puerto;
  private int timeout;
  private boolean stop;
  private ServerSocket servidor;
  private List<String> palabras;
  private int intentos;

  private AhorcadoProtocol protocol;

  public AhorcadoServer() {

    try {
      stop = false;
      Properties conf = new Properties();
      conf.load(new FileInputStream("server.properties"));
      puerto = Integer.parseInt(conf.getProperty("PORT"));
      timeout = Integer.parseInt(conf.getProperty("TIMEOUT"));
      intentos = Integer.parseInt(conf.getProperty("TRIES"));
      palabras = Arrays.asList(conf.getProperty("WORDS").split(","));

      protocol = new AhorcadoProtocol(palabras, intentos);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void main(String[] args) throws Exception {

    AhorcadoServer ahorcado = new AhorcadoServer();

    try (ServerSocket servidor = new ServerSocket(ahorcado.puerto);) {
      ahorcado.servidor = servidor;
      while (true) {
        System.out.println("Esperando cliente...");
        try (
            Socket cliente = ahorcado.servidor.accept();
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());) {

          System.out.println("Cliente conectado");

          String input, output;

          output = ahorcado.protocol.processInput(null);
          salida.writeUTF(output);
          salida.flush();

          output = ahorcado.protocol.processInput(null);
          salida.writeUTF(output);

          while ((input = entrada.readUTF()) != null) {
            output = ahorcado.protocol.processInput(input);
            salida.writeUTF(output);
            salida.flush();
            if (output.equals("¡Adios!"))
              break;
            output = ahorcado.protocol.processInput(input);
            salida.writeUTF(output);
            if (output.equals("¡Adios!"))
              break;
          }

        }
      }
    } catch (IOException e) {
      System.err.println("No se ha podido escuchar en el puerto " + ahorcado.puerto);
      System.exit(1);
    }

  }

}
