import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class HiloSubasta extends Thread {

  private String nombre;
  private Socket cliente = null;
  private Subasta subasta;
  private ProtocoloSubastas protocolo;
  private DataInputStream in = null;
  private DataOutputStream out = null;
  private Set<Subasta> subastasDelDia;
  private int turno;

  public HiloSubasta(Socket cliente, Set<Subasta> subastasDelDia, ProtocoloSubastas protocolo) {
    this.cliente = cliente;
    this.subastasDelDia = subastasDelDia;
    this.protocolo = protocolo;
  }

  @Override
  public void run() {
    try (DataInputStream entrada = new DataInputStream(cliente.getInputStream()); DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());) {

      String input, output;

      in = entrada;
      out = salida;

      do {
        // estado: conectado -> conectado
        output = protocolo.procesarMensaje(null);
        out.writeUTF(output); // conectado
        out.flush();

        input = in.readUTF();
        // estado: conectado -> listando / inscribiendo
        output = protocolo.procesarMensaje(input);
        output += ";";

        for (Subasta subasta : subastasDelDia) {
          if (!subasta.enCurso()) {
            output += subasta.getArticulo() + ",";
          }
        }

        out.writeUTF(output); // listar/inscribir;Set<subastas>
        out.flush();

      } while (!output.contains("inscribir")); // Sale del bucle en caso de que se haya inscrito en una subasta

      input = in.readUTF();
      nombre = input.split(";")[0];
      String articulo = input.split(";")[1];
      for (Subasta subasta : subastasDelDia) {
        if (!subasta.enCurso() && subasta.getArticulo().equals(articulo)) {
          this.subasta = subasta; // Se une a la subasta
          subasta.addParticipante(this);
          this.turno = subasta.getParticipantes(); // Se le pone de turno el numero total de participantes
          System.out.println(turno);
          break;
        }
      }

      // estado: inscribir -> esperando_subasta
      output = protocolo.procesarMensaje(null);
      out.writeUTF(output + ";" + subasta.getInicio()); // esperando;horaDeInicio
      out.flush();

      Duration duration = Duration.between(LocalTime.now(), subasta.getInicio());
      long delay = duration.toMillis();
      // Programo una tarea para notificar a todos los participantes cuando inicie la subasta
      Timer timer = new Timer();
      TimerTask task = new TimerTask() {
        @Override
        public void run() {
          subasta.comenzarSubasta();
        }
      };
      timer.schedule(task, delay);

      subasta.esperarComienzo();

      // estado: esperando_subasta -> subastando
      output = protocolo.procesarMensaje(null);
      out.writeUTF(output + ";" + subasta.getParticipantes() + ";" + subasta.getPrecio()); // inicio;numeroDeParticipantes;precioDelArticulo
      out.flush();

      while (!subasta.haAcabado()) {

        int turnoActual = subasta.getCurrentTurno();

        if (turnoActual == turno) {

          // estado: subastando -> comprobando
          output = protocolo.procesarMensaje(null);
          out.writeUTF(output + ";true"); // subastando;true (true porque es su turno)
          out.flush();

          input = in.readUTF();
          int oferta = Integer.parseInt(input);

          String resultado = subasta.actualizarPrecio(oferta, nombre);

          // estado: comprobando -> subastando
          output = protocolo.procesarMensaje(resultado);
          out.writeUTF(output); // resultado de haber comprobado el precio
          out.flush();

        } else {

          // estado: subastando -> comprobando
          output = protocolo.procesarMensaje(null);
          out.writeUTF(output + ";false;" + turnoActual); // subastando;false;<turnoActual> (false porque NO es su turno)
          out.flush();

          subasta.esperarTurno(turno);

          // estado: comprobando -> subastando
          output = protocolo.procesarMensaje(subasta.getResultadoAnterior());
          out.writeUTF(output + ";" + turnoActual); // resultado de haber comprobado el precio del participante anterior
          out.flush();

        }

      }

      // estado: x -> fin
      output = protocolo.procesarMensaje("fin");
      out.writeUTF(output + ";" + subasta.getGanador() + ";" + subasta.getPrecio()); // fin;ganador;precio
      out.flush();

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}
