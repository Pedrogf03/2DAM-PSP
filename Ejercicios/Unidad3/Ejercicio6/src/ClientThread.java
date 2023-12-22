import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

  Socket socket;
  private int numCliente;

  public ClientThread(Socket socket, int numCliente) {
    this.socket = socket;
    this.numCliente = numCliente;
  }

  @Override
  public void run() {

    try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);) {

      System.out.println("Conexión acceptada: " + socket);

      String str = entrada.readLine();
      System.out.println("Cliente" + numCliente + ": " + str);
      int num = (Integer.parseInt(str) * Integer.parseInt(str));
      System.out.println("Respuesta a cliente" + numCliente + ": " + num);
      salida.println(num);

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }

  }

}