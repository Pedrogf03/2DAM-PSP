import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class ClienteSubastas {

  DataInputStream entrada = null;
  DataOutputStream salida = null;
  boolean salir = false;

  public ClienteSubastas(DataInputStream entrada, DataOutputStream salida) {
    this.entrada = entrada;
    this.salida = salida;
  }

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    int puerto = 4444;
    String dir = "localhost";
    try {
      Properties conf = new Properties();
      conf.load(new FileInputStream("client.properties"));
      puerto = Integer.parseInt(conf.getProperty("PUERTO"));
    } catch (IOException e) {
      System.out.println("No se pudo leer el archivo de propiedades, se usó el puerto por defecto (4444)");
    }

    try (Socket cliente = new Socket(dir, puerto); DataInputStream entrada = new DataInputStream(cliente.getInputStream()); DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());) {

      ClienteSubastas ac = new ClienteSubastas(entrada, salida);
      ac.conectar();

    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("No se puede conectar con el servidor");
    } finally {
      sc.close();
    }

  }

  private void conectar() throws IOException {

    Scanner sc = new Scanner(System.in);

    String fromServer, fromUser;

    while (!salir && ((fromServer = entrada.readUTF()) != null)) {

      // System.out.println(fromServer);

      String[] respuesta = fromServer.split(";");
      String estado = respuesta[0];

      switch (estado) {
      case "conectado":
        System.out.println("Bienvenido a las subastas del día");
        System.out.println("1-. Listar Subastas");
        System.out.println("2-. Inscribirse en una subasta");
        System.out.println("0-. Salir");
        do {
          System.out.println("Elige una opción:");
          fromUser = sc.nextLine();
        } while (!fromUser.equals("1") && !fromUser.equals("2") && !fromUser.equals("0"));

        switch (fromUser) {
        case "1":
          fromUser = "listar";
          break;
        case "2":
          fromUser = "inscribir";
          break;
        case "0":
          fromUser = "salir";
          salir = true;
          break;
        }
        salida.writeUTF(fromUser);
        salida.flush();
        break;
      case "listar":
        if (respuesta.length > 1) {
          String subastas = respuesta[1];
          System.out.println("Subastas disponibles: ");
          for (String articulo : subastas.split(",")) {
            System.out.println(" " + articulo);
          }
        } else {
          System.out.println("Actualmente no hay subastas disponibles");
        }
        break;
      case "inscribir":
        if (respuesta.length > 1) {
          String subastas = respuesta[1];
          System.out.println("Nombre: ");
          String nombre = sc.nextLine();
          System.out.println("Articulo que quiere adquirir: ");
          String articulo = sc.nextLine();
          while (!subastas.contains(articulo)) {
            System.out.println("Ese articulo no está disponible");
            System.out.println("Articulo que quiere adquirir: ");
            articulo = sc.nextLine();
          }
          fromUser = nombre + ";" + articulo;
          salida.writeUTF(fromUser);
          salida.flush();
        } else {
          System.out.println("Actualmente no hay subastas disponibles");
        }
        break;
      case "esperando":
        String inicio = respuesta[1];
        System.out.println("La subasta comienza a las " + inicio + "h");
        break;
      case "inicio":
        String numParticipantes = respuesta[1];
        String precioInicial = respuesta[2];
        System.out.println("La subasta ha comenzado, hay un total de " + numParticipantes + " participantes.");
        System.out.println("El precio inicial del articulo es de " + precioInicial + " euros");
        break;
      case "subastando":
        boolean miTurno = Boolean.parseBoolean(respuesta[1]);
        if (miTurno) {
          boolean exit = false;
          do {
            System.out.println("Nueva oferta: ");
            fromUser = sc.nextLine();
            try {
              Integer.parseInt(fromUser);
              exit = true;
            } catch (NumberFormatException e) {
              continue;
            }
          } while (!exit);
          salida.writeUTF(fromUser);
          salida.flush();
        } else {
          int turnoActual = Integer.parseInt(respuesta[2]);
          System.out.println("Turno del participante nº" + turnoActual);
        }
        break;
      case "comprobando":
        if (respuesta[1].equals("menos")) {
          System.out.println("No puede hacer una oferta menor que el precio actual.");
        } else if (respuesta[1].equals("igual")) {
          System.out.println("No puede hacer una oferta igual que el precio actual.");
        } else if (respuesta[1].equals("cambio")) {
          System.out.println("Se ha actualizado el precio. El nuevo precio es " + respuesta[2]);
        }
        break;
      case "fin":
        System.out.println("Se ha acabado la subasta.");
        String ganador = respuesta[1];
        String precioAlcanzado = respuesta[2];
        System.out.println("El ganador ha sido " + ganador + ", apostando un total de " + precioAlcanzado + " euros");
        salir = true;
      default:
        break;
      }

    }

    sc.close();

  }

}
