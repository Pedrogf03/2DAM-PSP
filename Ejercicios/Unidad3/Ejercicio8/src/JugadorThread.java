import java.io.DataInputStream;
import java.io.DataOutputStream;
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

    try (DataInputStream entrada = new DataInputStream(socket.getInputStream());
        DataOutputStream salida = new DataOutputStream(socket.getOutputStream())) {

      salida.writeUTF("Bienvenido Jugador" + turnoJug);
      salida.writeInt(turnoJug);

      do {

        int turnoActual = dealer.getTurno();
        salida.writeInt(turnoActual);

        if (turnoJug == turnoActual) {
          salida.writeUTF("Tu turno");
          dealer.setNumJug(entrada.readInt());
          dealer.notificarNum();
          dealer.aumentarTurno();

          switch (dealer.comprobarNumero(dealer.getNumJug(), turnoActual)) {
            case 1:
              salida.writeUTF("El número secreto es mayor que " + dealer.getNumJug());
              break;
            case -1:
              salida.writeUTF("El número secreto es menor que " + dealer.getNumJug());
              break;
            case 0:
              salida.writeUTF("¡Has adivinado el número secreto!");
              dealer.setFinPartida(true);
              break;
          }

        } else {
          salida.writeUTF("Turno del jugador" + turnoActual);
          dealer.waitForNum();

          switch (dealer.comprobarNumero(dealer.getNumJug(), turnoActual)) {
            case 1:
              salida.writeUTF("El número secreto es mayor que " + dealer.getNumJug());
              break;
            case -1:
              salida.writeUTF("El número secreto es menor que " + dealer.getNumJug());
              break;
            case 0:
              salida
                  .writeUTF("¡El jugador" + turnoActual + " adivinado el número secreto! (" + dealer.getNumJug() + ")");
              dealer.setFinPartida(true);
              break;
          }

        }

        salida.writeBoolean(dealer.isFinPartida());

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