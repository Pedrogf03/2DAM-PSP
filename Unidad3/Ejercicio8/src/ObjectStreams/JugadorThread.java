package objectStreams;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

public class JugadorThread extends Thread {

  Socket socket;
  private int turnoJugador;
  private Dealer dealer;

  public JugadorThread(Socket socket, int turnoJugador, Dealer dealer) {
    this.socket = socket;
    this.turnoJugador = turnoJugador;
    this.dealer = dealer;
  }

  @Override
  public void run() {

    try (ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());) {

      Datos d = new Datos(turnoJugador);
      d.setTurnoActual(dealer.getTurnoActual());

      salida.writeObject(d);
      salida.flush();

      while (!dealer.isFinDelJuego()) {

        if (turnoJugador == dealer.getTurnoActual()) {

          d = (Datos) entrada.readObject();

          d.setNumeroJugador(dealer.comprobarNumero(d.getNumeroJugador(), turnoJugador));

          salida.writeObject(d);
          salida.flush();

        } else {

          dealer.esperarTurno(turnoJugador);

        }

        d.setTurnoActual(dealer.getTurnoActual());
        d.setFinDelJuego(dealer.isFinDelJuego());

        // Crear un nuevo objeto Datos con los valores actualizados
        Datos dActualizado = new Datos(d);

        // Enviar el nuevo objeto Datos al cliente
        salida.writeObject(dActualizado);
        salida.flush();

      }

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}