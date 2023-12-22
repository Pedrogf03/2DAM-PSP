import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
  public static void main(String[] args) throws IOException {

    try (Socket socketCliente = new Socket(args[1], Integer.parseInt(args[3]));
        DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
        DataOutputStream salida = new DataOutputStream(socketCliente.getOutputStream());
        Scanner sc = new Scanner(System.in);) {

      String palabra = args[5];
      salida.writeUTF(palabra);
      String respuesta = entrada.readUTF();
      System.out.println("Servidor: " + respuesta);

    } catch (IOException e) {
      System.err.println("No puede establer canales de E/S para la conexión");
      System.exit(-1);
    }

  }

}