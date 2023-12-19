import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
  public static void main(String[] args) throws IOException {

    Socket socketCliente = null;
    BufferedReader entrada = null;
    PrintWriter salida = null;

    try {

      socketCliente = new Socket("localhost", 4444);
      entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
      salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);

    } catch (IOException e) {
      System.err.println("No puede establer canales de E/S para la conexión");
      System.exit(-1);
    }

    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    int num;

    try {

      // Leo la entrada del usuario
      num = Integer.parseInt(stdIn.readLine());
      // La envia al servidor
      salida.println(num);
      // Envía a la salida estándar la respuesta del servidor
      num = Integer.parseInt(entrada.readLine());
      System.out.println("Respuesta servidor: " + num);
      // Si es "Adios" es que finaliza la comunicación

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    } finally {
      // Libera recursos
      salida.close();
      entrada.close();
      stdIn.close();
      socketCliente.close();
    }

  }
}