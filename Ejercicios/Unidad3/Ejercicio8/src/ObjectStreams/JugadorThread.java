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

      salida.writeObject(d);
      salida.flush();

      while (!dealer.isFinDelJuego()) {

        d = (Datos) entrada.readObject();

        d.setNumeroJugador(dealer.comprobarNumero(d.getNumeroJugador(), turnoJugador));

        if (d.getNumeroJugador() == 0) {
          dealer.setFinDelJuego(true);
        }

        d.setFinDelJuego(dealer.isFinDelJuego());

        salida.writeObject(d);
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