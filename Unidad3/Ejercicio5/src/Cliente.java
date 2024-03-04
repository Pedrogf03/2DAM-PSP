import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
  public static void main(String[] args) throws IOException {

    try (Socket socketCliente = new Socket("localhost", Servidor.PORT);
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
        PrintWriter salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);
        Scanner sc = new Scanner(System.in);) {

      int num = Integer.parseInt(sc.nextLine());
      System.out.println("Cliente: " + num);
      salida.println(num);
      String respuesta = entrada.readLine();
      System.out.println("Servidor: " + respuesta);

    } catch (IOException e) {
      System.err.println("No puede establer canales de E/S para la conexión");
      System.exit(-1);
    }

  }
}