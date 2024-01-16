package objectStreams;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Jugador {
  public static void main(String[] args) throws IOException {

    try (Socket socketCliente = new Socket("localhost", ServidorAdivina.SERVERPORT);
        ObjectInputStream entrada = new ObjectInputStream(socketCliente.getInputStream());
        ObjectOutputStream salida = new ObjectOutputStream(socketCliente.getOutputStream());
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
          salida.flush();
        }

        System.out.println(entrada.readUTF());

        fin = entrada.readBoolean();

      } while (!fin);

      salida.writeInt(0);
      salida.flush();

    } catch (IOException e) {
      System.err.println("No puede establer canales de E/S para la conexión");
      System.exit(-1);
    }

  }
}