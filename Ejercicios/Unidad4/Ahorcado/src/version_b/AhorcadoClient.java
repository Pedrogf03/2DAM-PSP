package version_b;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class AhorcadoClient {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    int intentos = 0;

    System.out.print("Direccion IP: ");
    String dir = sc.nextLine();

    System.out.print("Puerto: ");
    int port = Integer.parseInt(sc.nextLine());

    try (Socket cliente = new Socket(dir, port);
        DataInputStream entrada = new DataInputStream(cliente.getInputStream());
        DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());) {

      String fromServer, fromUser;

      while ((fromServer = entrada.readUTF()) != null) {
        if (fromServer.equals("Letra: ")) {

          switch (intentos) {
            case 0:
              System.out.println("------|");
              System.out.println("|     O");
              System.out.println("|    /|\\");
              System.out.println("|    / \\");
              System.out.println("|");
              System.out.println("========");
              break;
            case 1:
              System.out.println("------|");
              System.out.println("|     O");
              System.out.println("|    /|\\");
              System.out.println("|    / ");
              System.out.println("|");
              System.out.println("========");
              break;
            case 2:
              System.out.println("------|");
              System.out.println("|     O");
              System.out.println("|    /|\\");
              System.out.println("|");
              System.out.println("|");
              System.out.println("========");
              break;
            case 3:
              System.out.println("------|");
              System.out.println("|     O");
              System.out.println("|    /|");
              System.out.println("|");
              System.out.println("|");
              System.out.println("========");
              break;
            case 4:
              System.out.println("------|");
              System.out.println("|     O");
              System.out.println("|     |");
              System.out.println("|");
              System.out.println("|");
              System.out.println("========");
              break;
            case 5:
              System.out.println("------|");
              System.out.println("|     O");
              System.out.println("|");
              System.out.println("|");
              System.out.println("|");
              System.out.println("========");
              break;
          }

        }
        System.out.println(fromServer);
        if (fromServer.equals("¡Adios!"))
          break;

        if (fromServer.contains("no"))
          intentos++;

        if (fromServer.equals("Letra: ") || fromServer.contains("¿quieres jugar de nuevo? (S/N)")) {
          fromUser = sc.nextLine();
          if (fromUser != null) {
            salida.writeUTF(fromUser);
          }
          if (fromServer.contains("¿quieres jugar de nuevo? (S/N)"))
            intentos = 0;
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    sc.close();

  }

}
