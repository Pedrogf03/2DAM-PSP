import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

  private static final int PORT = 4444;

  public static void main(String[] args) throws IOException {
    try (ServerSocket socketServidor = new ServerSocket(PORT)) {

      System.out.println("Escuchando: " + socketServidor);
      try (Socket socketCliente = socketServidor.accept(); BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream())); PrintWriter salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);) {

        System.out.println("Conexión acceptada: " + socketCliente);

        String str = entrada.readLine();
        System.out.println("Cliente: " + str);
        int num = (Integer.parseInt(str) * Integer.parseInt(str));
        System.out.println("Servidor: " + num);
        salida.println(num);

      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
      }

    } catch (IOException e) {
      System.out.println("No puede escuchar en el puerto: " + PORT);
      System.exit(-1);
    }

  }

}
