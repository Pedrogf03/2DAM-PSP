package ObjectStreams;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

public class JugadorThread extends Thread {

  Socket socket;
  private int turnoJug;
  private Dealer dealer;
  private static boolean fin = false;

  private static int numJug;

  public JugadorThread(Socket socket, int turnoJug, Dealer dealer) {
    this.socket = socket;
    this.turnoJug = turnoJug;
    this.dealer = dealer;
  }

  @Override
  public void run() {

    try (ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream())) {

      salida.writeUTF("Bienvenido Jugador" + turnoJug);
      salida.writeInt(turnoJug);

      do {

        int turnoActual = dealer.getTurno();
        salida.writeInt(turnoActual);

        if (turnoJug == turnoActual) {
          salida.writeUTF("Tu turno");
          JugadorThread.numJug = entrada.readInt();
          dealer.notificarNum();
          dealer.aumentarTurno();

          switch (dealer.comprobarNumero(numJug, turnoActual)) {
            case 1:
              salida.writeUTF("El número secreto es mayor que " + numJug);
              break;
            case -1:
              salida.writeUTF("El número secreto es menor que " + numJug);
              break;
            case 0:
              salida.writeUTF("¡Has adivinado el número secreto!");
              JugadorThread.fin = true;
              break;
          }

        } else {
          salida.writeUTF("Turno del jugador" + turnoActual);
          dealer.waitForNum();

          switch (dealer.comprobarNumero(numJug, turnoActual)) {
            case 1:
              salida.writeUTF("El número secreto es mayor que " + numJug);
              break;
            case -1:
              salida.writeUTF("El número secreto es menor que " + numJug);
              break;
            case 0:
              salida.writeUTF("¡El jugador" + turnoActual + " adivinado el número secreto! (" + numJug + ")");
              JugadorThread.fin = true;
              break;
          }

        }

        salida.writeBoolean(JugadorThread.fin);

      } while (!JugadorThread.fin);

      System.out.println("Se acabó el juego");

      entrada.readInt();

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    } catch (InterruptedException e) {
      return;
    }

  }

}