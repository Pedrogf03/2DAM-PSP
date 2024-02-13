package version_c;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class AhorcadoClient {

  private int intentos = 0;
  private boolean finPartida = false;

  DataInputStream entrada = null;
  DataOutputStream salida = null;

  public AhorcadoClient(DataInputStream entrada, DataOutputStream salida) {
    this.entrada = entrada;
    this.salida = salida;
  }

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    // System.out.print("Direccion IP: ");
    // String dir = sc.nextLine();

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

    try (Socket cliente = new Socket("localhost", port); DataInputStream entrada = new DataInputStream(cliente.getInputStream()); DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());) {

      AhorcadoClient ac = new AhorcadoClient(entrada, salida);

      String fromServer, fromUser;

      while (!ac.finPartida && ((fromServer = ac.entrada.readUTF()) != null)) {

        //System.out.println(fromServer);

        String[] res = fromServer.split(";");

        switch (res[0]) {
        case "waiting":
          System.out.println("Esperando jugadores...");
          break;
        case "bienvenido":
          System.out.println("¡Bienvenido al juego el ahorcado!");
          break;
        case "playing":
          if (res.length > 1) {
            if (res[1].equals("letra")) {

              System.out.println("Letras usadas: " + res[3]);
              System.out.println(res[4]); // Plabra a adivinar
              ac.intentos = Integer.parseInt(res[5]); // Intentos usados

              ac.mostrarAhorcado();

            } else if (res[1].equals("palabra")) {

              if (res[2].equals("false")) {

                System.out.println("Letras usadas: " + res[3]);
                System.out.println(res[4]); // Plabra a adivinar
                ac.intentos = Integer.parseInt(res[5]); // Intentos usados

                ac.mostrarAhorcado();

              }
            }
          }
          do {
            System.out.print("Letra: ");
            fromUser = sc.nextLine();
          } while (fromUser == null || fromUser == "");
          salida.writeUTF(fromUser);
          salida.flush();
          break;
        case "otro_turno":
          System.out.println("Turno del jugador" + res[1]);
          break;
        case "checking":
          if (res[1].equals("letra")) {

            if (res[2].equals("true")) {

              System.out.println("Esa letra está en la palabra secreta");

            } else if (res[2].equals("false")) {

              System.out.println("Esa letra no está en la palabra secreta");

            }

            System.out.println("Letras usadas: " + res[3]);
            System.out.println(res[4]); // Plabra a adivinar
            ac.intentos = Integer.parseInt(res[5]); // Intentos usados

            ac.mostrarAhorcado();

          } else if (res[1].equals("palabra")) {

            if (res[2].equals("false")) {

              System.out.println("Esa no es la palabra secreta");
              System.out.println("Letras usadas: " + res[3]);
              System.out.println(res[4]); // Plabra a adivinar
              ac.intentos = Integer.parseInt(res[5]); // Intentos usados

              ac.mostrarAhorcado();

            }

          }
          break;
        default:
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

  // private void checkgame(String[] res, Scanner sc) throws IOException {
  //   if (res[1].equals("letra")) {

  //     if (res[2].equals("true")) {

  //       System.out.println("Esa letra está en la palabra secreta");

  //     } else if (res[2].equals("false")) {

  //       System.out.println("Esa letra no está en la palabra secreta");

  //     }

  //     System.out.println("Letras usadas: " + res[3]);
  //     System.out.println(res[4]); // Plabra a adivinar
  //     intentos = Integer.parseInt(res[5]); // Intentos usados

  //     mostrarAhorcado();

  //   } else if (res[1].equals("palabra")) {

  //     if (res[2].equals("false")) {

  //       System.out.println("Esa no es la palabra secreta");
  //       System.out.println("Letras usadas: " + res[3]);
  //       System.out.println(res[4]); // Plabra a adivinar
  //       intentos = Integer.parseInt(res[5]); // Intentos usados

  //       mostrarAhorcado();

  //     }

  //   } else if (res[1].equals("win")) {

  //     System.out.println("¡Has adivinado la palabra secreta!");
  //     intentos = 0;

  //     do {
  //       System.out.print("¿Quieres jugar de nuevo? (S/N) ");
  //       fromUser = sc.nextLine();
  //     } while (fromUser == null || fromUser == "" || (!fromUser.equalsIgnoreCase("s") && !fromUser.equalsIgnoreCase("n")));

  //     salida.writeUTF(fromUser);
  //     salida.flush();

  //   } else if (res[1].equals("lose")) {

  //     System.out.println("Lo siento, te quedaste sin intentos");

  //     intentos = Integer.parseInt(res[3]);

  //     mostrarAhorcado();

  //     System.out.println("La palabra era " + res[2]);
  //     intentos = 0;
  //     do {
  //       System.out.print("¿Quieres jugar de nuevo? (S/N) ");
  //       fromUser = sc.nextLine();
  //     } while (fromUser == null || fromUser == "" || (!fromUser.equalsIgnoreCase("s") && !fromUser.equalsIgnoreCase("n")));

  //     salida.writeUTF(fromUser);
  //     salida.flush();

  //   }
  // }

  private void mostrarAhorcado() {
    switch (intentos) {
    case 6:
      System.out.println("------|");
      System.out.println("|     O");
      System.out.println("|    /|\\");
      System.out.println("|    / \\");
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
    case 4:
      System.out.println("------|");
      System.out.println("|     O");
      System.out.println("|    /|\\");
      System.out.println("|     ");
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
    case 2:
      System.out.println("------|");
      System.out.println("|     O");
      System.out.println("|     |");
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
    case 0:
      System.out.println("------|");
      System.out.println("|");
      System.out.println("|");
      System.out.println("|");
      System.out.println("|");
      System.out.println("========");
      break;
    }
  }

}