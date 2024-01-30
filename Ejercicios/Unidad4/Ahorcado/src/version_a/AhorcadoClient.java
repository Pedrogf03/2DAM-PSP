package version_a;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class AhorcadoClient {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    System.out.print("Direccion IP: ");
    String dir = sc.nextLine();

    System.out.print("Puerto: ");
    int port = Integer.parseInt(sc.nextLine());

    try (Socket cliente = new Socket(dir, port);
        DataInputStream entrada = new DataInputStream(cliente.getInputStream());
        DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());) {

      String fromServer, fromUser;

      while ((fromServer = entrada.readUTF()) != null) {
        System.out.println(fromServer);
        if (fromServer.equals("¡Adios!"))
          break;

        if (fromServer.equals("Letra: ") || fromServer.contains("¿quieres jugar de nuevo? (S/N)")) {
          fromUser = sc.nextLine();
          if (fromUser != null) {
            salida.writeUTF(fromUser);
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    sc.close();

  }

}
