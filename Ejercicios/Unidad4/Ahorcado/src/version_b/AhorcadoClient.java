package version_b;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class AhorcadoClient {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    int intentos = 0;

    System.out.print("Direccion IP: ");
    String dir = sc.nextLine();

    int port = 4444;
    try {
      Properties conf = new Properties();
      conf.load(new FileInputStream("client.properties"));
      port = Integer.parseInt(conf.getProperty("PORT"));
    } catch (IOException e) {
      try {
        throw new AhorcadoException("No se pudo leer el archivo de propiedades, se usó el puerto por defecto(4444)");
      } catch (AhorcadoException aex) {
        System.out.println(aex.getMessage());
      }
    }

    try (Socket cliente = new Socket(dir, port); DataInputStream entrada = new DataInputStream(cliente.getInputStream()); DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());) {

      String fromServer, fromUser;

      while ((fromServer = entrada.readUTF()) != null) {

        if (fromServer.contains("waiting_game")) {

          System.out.println("¡Bienvenido al juego del ahorcado!");

        } else if (fromServer.contains("playing")) {

          switch (intentos) {
          case 0:
            System.out.println("------|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("========");
            break;
          case 1:
            System.out.println("------|");
            System.out.println("|     O");
            System.out.println("|     ");
            System.out.println("|");
            System.out.println("|");
            System.out.println("========");
            break;
          case 2:
            System.out.println("------|");
            System.out.println("|     O");
            System.out.println("|     |");
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
            System.out.println("|    /|\\");
            System.out.println("|     ");
            System.out.println("|");
            System.out.println("========");
            break;
          case 5:
            System.out.println("------|");
            System.out.println("|     O");
            System.out.println("|    /|\\");
            System.out.println("|    / ");
            System.out.println("|");
            System.out.println("========");
            break;
          case 6:
            System.out.println("------|");
            System.out.println("|     O");
            System.out.println("|    /|\\");
            System.out.println("|    / \\");
            System.out.println("|");
            System.out.println("========");
            break;
          }

          System.out.print("Letra: ");
          fromUser = sc.nextLine();
          while (fromUser == null || fromUser == "") {
            fromUser = sc.nextLine();
          }
          salida.writeUTF(fromUser);

        } else if (fromServer.contains("cheking")) {

          if (fromServer.contains("letra")) {

            if (fromServer.contains("true")) {
              System.out.println("Esa letra está en la palabra secreta");
            } else if (fromServer.contains("false")) {
              System.out.println("Esa letra no está en la palabra secreta");
            }
            String[] res = fromServer.split(";");
            System.out.println("Letras usadas: " + res[3]);
            System.out.println(res[4]);

            intentos = Integer.parseInt(res[5]);

          } else if (fromServer.contains("palabra")) {

            if (fromServer.contains("false")) {
              System.out.println("Esa no es la palabra secreta");
            }
            String[] res = fromServer.split(";");
            System.out.println("Letras usadas: " + res[3]);
            System.out.println(res[4]);

            intentos = Integer.parseInt(res[5]);

          } else if (fromServer.contains("win")) {
            System.out.println("¡Has adivinado la palabra secreta!, ¿Quieres jugar de nuevo? (S/N)");
            fromUser = sc.nextLine();
            while (fromUser == null || fromUser == "" || (!fromUser.equalsIgnoreCase("s") && !fromUser.equalsIgnoreCase("n"))) {
              fromUser = sc.nextLine();
            }
            salida.writeUTF(fromUser);
            if (fromUser.equalsIgnoreCase("s")) {
              intentos = 0;
            }
          } else if (fromServer.contains("lose")) {
            String palabra = fromServer.split(";")[2];
            System.out.println("Te quedaste sin intentos, la palabra secreta era " + palabra + ", ¿Quieres jugar de nuevo? (S/N)");
            fromUser = sc.nextLine();
            while (fromUser == null || fromUser == "" || (!fromUser.equalsIgnoreCase("s") && !fromUser.equalsIgnoreCase("n"))) {
              fromUser = sc.nextLine();
            }
            salida.writeUTF(fromUser);
            if (fromUser.equalsIgnoreCase("s")) {
              intentos = 0;
            }
          }

        } else if (fromServer.contains("bye")) {
          break;
        }

      }

    } catch (IOException e) {
      e.printStackTrace();
      try {
        throw new AhorcadoException("No se puede conectar con el servidor.");
      } catch (AhorcadoException aex) {
        System.out.println(aex.getMessage());
      }
    } finally {
      sc.close();
    }

  }

}
