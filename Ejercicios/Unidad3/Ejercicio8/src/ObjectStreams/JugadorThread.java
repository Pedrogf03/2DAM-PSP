package objectStreams;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

public class JugadorThread extends Thread {

  Socket socket;
  private int turnoJug;
  private Dealer dealer;

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
      salida.flush();
      salida.writeInt(turnoJug);
      salida.flush();

      do {

        int turnoActual = dealer.getTurno();
        salida.writeInt(turnoActual);
        salida.flush();

        if (turnoJug == turnoActual) {
          salida.writeUTF("Tu turno");
          salida.flush();
          dealer.setNumJug(entrada.readInt());
          dealer.notificarNum();
          dealer.aumentarTurno();

          switch (dealer.comprobarNumero(dealer.getNumJug(), turnoActual)) {
          case 1:
            salida.writeUTF("El número secreto es mayor que " + dealer.getNumJug());
            salida.flush();
            break;
          case -1:
            salida.writeUTF("El número secreto es menor que " + dealer.getNumJug());
            salida.flush();
            break;
          case 0:
            salida.writeUTF("¡Has adivinado el número secreto!");
            salida.flush();
            dealer.setFinPartida(true);
            break;
          }

        } else {
          salida.writeUTF("Turno del jugador" + turnoActual);
          salida.flush();
          dealer.waitForNum();

          switch (dealer.comprobarNumero(dealer.getNumJug(), turnoActual)) {
          case 1:
            salida.writeUTF("El número secreto es mayor que " + dealer.getNumJug());
            salida.flush();
            break;
          case -1:
            salida.writeUTF("El número secreto es menor que " + dealer.getNumJug());
            salida.flush();
            break;
          case 0:
            salida.writeUTF("¡El jugador" + turnoActual + " adivinado el número secreto! (" + dealer.getNumJug() + ")");
            salida.flush();
            dealer.setFinPartida(true);
            break;
          }

        }

        salida.writeBoolean(dealer.isFinPartida());
        salida.flush();

      } while (!dealer.isFinPartida());

      System.out.println("Se acabó el juego");

      entrada.readInt();

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    } catch (InterruptedException e) {
      return;
    }

  }

}