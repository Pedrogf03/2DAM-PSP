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
  Scanner scanner;

  public AhorcadoClient(DataInputStream entrada, DataOutputStream salida, Scanner sc) {
    this.entrada = entrada;
    this.salida = salida;
    this.scanner = sc;
  }

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    System.out.print("Direccion IP: ");
    String dir = sc.nextLine();

    int port = 4444;
    try {
      Properties conf = new Properties();
      conf.load(new FileInputStream("client.properties"));
      port = Integer.parseInt(conf.getProperty("PORT"));
    } catch (IOException e) {
      System.out.println("No se pudo leer el archivo de propiedades, se usó el puerto por defecto(4444)");
    }

    try (Socket cliente = new Socket(dir, port); DataInputStream entrada = new DataInputStream(cliente.getInputStream()); DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());) {

      AhorcadoClient ac = new AhorcadoClient(entrada, salida, sc);
      ac.jugar();

    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("No se puede conectar con el servidor");
    } finally {
      sc.close();
    }

  }

  public void jugar() throws IOException {
    String fromServer, fromUser;

    while (!finPartida && ((fromServer = entrada.readUTF()) != null)) {

      String[] response = fromServer.split(";");
      String status = response[0];

      switch (status) {
      case "waiting":
        System.out.println("Esperando jugadores...");
        break;
      case "welcome":
        System.out.println("¡Bienvenido al juego del ahoracado!");
        break;
      case "playing":
        boolean miTurno = Boolean.parseBoolean(response[1]);
        if (miTurno) {
          do {
            System.out.print("Letra: ");
            fromUser = scanner.nextLine();
          } while (fromUser == null || fromUser == "");
          salida.writeUTF(fromUser);
          salida.flush();
        } else {
          int turnoActual = Integer.parseInt(response[2]);
          System.out.println("Turno del jugador" + turnoActual);
        }
        break;
      case "checking":
        processGame(response);
        if (response.length == 3) {
          do {
            System.out.print(" ¿Quieren jugar de nuevo? (S/N) ");
            fromUser = scanner.nextLine();
          } while (fromUser == null || fromUser == "" || (!fromUser.equalsIgnoreCase("s") && !fromUser.equalsIgnoreCase("n")));
          salida.writeUTF(fromUser);
          salida.flush();
        }
        break;
      case "bye":
        System.out.println("Un jugador ha decidido que no quiere jugar");
        finPartida = true;
        break;
      case "new_game":
        System.out.println("Se ha escogido una nueva palabra");
        break;
      default:
        break;
      }

    }
  }

  private void processGame(String[] response) {

    // response = checking;<win/lose>;<palabra>.lenght == 3
    if (response.length == 3) {
      String resultadoPartida = response[1];
      String palabraCorrecta = response[2];

      if (resultadoPartida.equals("lose")) {
        System.out.print("Os habéis quedado sin intentos, la palabra era " + palabraCorrecta);
      } else if (resultadoPartida.equals("win")) {
        System.out.print("¡Habéis adivinado la palabra secreta! (" + palabraCorrecta + ")");
      }

      // response = checking;<letra/palabra>;<true/false>;<letrasUsadas>;<palabra>;intentos.lenght == 6 
    } else if (response.length == 6) {
      String type = response[1]; // Letra o palabra
      boolean correcto = Boolean.parseBoolean(response[2]); // Booleano para saber si lo introducido por el usuario es correcto o no
      String palabrasUsadas = response[3]; // Lista de letras usadas
      String palabraAdivinar = response[4]; // Palabra  que se esta intenado adivinar
      intentos = Integer.parseInt(response[5]); // Intentos usados

      if (type.equals("letra")) {

        if (correcto) {
          System.out.println("Esa letra está en la palabra secreta");
        } else if (!correcto) {
          System.out.println("Esa letra no está en la palabra secreta");
        }

        System.out.println("Letras usadas: " + palabrasUsadas);
        System.out.println(palabraAdivinar); // Plabra a adivinar

        mostrarAhorcado();

      } else if (type.equals("palabra")) {

        if (!correcto) {

          System.out.println("Esa no es la palabra secreta");
          System.out.println("Letras usadas: " + palabrasUsadas);
          System.out.println(palabraAdivinar); // Plabra a adivinar

          mostrarAhorcado();

        }

      }
    }

  }

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