package objectStreams;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Jugador {
  public static void main(String[] args) throws IOException {

    System.out.println("Esperando jugadores...");

    try (Socket socketCliente = new Socket("localhost", ServidorAdivina.SERVERPORT);
        ObjectOutputStream salida = new ObjectOutputStream(socketCliente.getOutputStream());
        ObjectInputStream entrada = new ObjectInputStream(socketCliente.getInputStream());
        Scanner sc = new Scanner(System.in);) {

      Datos d = (Datos) entrada.readObject();

      System.out.println("Bienvenido, jugador" + d.getTurnoJugador());

      while (!d.isFinDelJuego()) {

        System.out.print("Introduce un número: ");
        int numero = sc.nextInt();

        d.setNumeroJugador(numero);

        salida.writeObject(d);
        salida.flush();

        d = (Datos) entrada.readObject();

        switch (d.getNumeroJugador()) {
        case -1:
          System.out.println("El numero secreto es menor que " + numero);
          break;
        case 1:
          System.out.println("El numero secreto es mayor que " + numero);
          break;
        case 0:
          System.out.println("Has adivinado el número secreto");
          break;
        }

      }

      System.out.println("Un jugador ha adivinado el número secreto. Fin de la partida");

    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

  }
}