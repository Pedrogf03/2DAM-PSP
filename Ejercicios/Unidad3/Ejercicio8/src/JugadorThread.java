import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class JugadorThread extends Thread {

  Socket socket;
  private int turnoJug;
  private Dealer dealer;
  private static boolean fin = false;

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

        salida.writeInt(dealer.getTurno());

        if (turnoJug == dealer.getTurno()) {
          salida.writeUTF("Tu turno");
          int numJug = entrada.readInt();

          switch (dealer.comprobarNumero(numJug, turnoJug)) {
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
          salida.writeUTF("Turno del jugador" + dealer.getTurno());
          dealer.waitTurno(turnoJug);
          salida.writeInt(1);
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