import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Jugador {
  public static void main(String[] args) throws IOException {

    try (Socket socketCliente = new Socket("localhost", ServidorAdivina.SERVERPORT);
        DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
        DataOutputStream salida = new DataOutputStream(socketCliente.getOutputStream());
        Scanner sc = new Scanner(System.in);) {

      System.out.println("Esperando jugadores...");

      boolean fin = false;

      System.out.println(entrada.readUTF());
      int turno = entrada.readInt();

      do {

        int turnoActual = entrada.readInt();

        System.out.println(entrada.readUTF());

        if (turnoActual == turno) {
          int num = sc.nextInt();
          salida.writeInt(num);

          System.out.println(entrada.readUTF());

        } else {
          entrada.readInt();
        }

        fin = entrada.readBoolean();

        if (fin) {
          System.out.println("Un jugador ha adivinado el número. Fin del juego");
        }

      } while (!fin);

      salida.writeInt(0);

    } catch (IOException e) {
      System.err.println("No puede establer canales de E/S para la conexión");
      System.exit(-1);
    }

  }
}