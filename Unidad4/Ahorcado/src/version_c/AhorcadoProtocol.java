package version_c;

public class AhorcadoProtocol {

  private Status estado;
  private String mensaje;

  public AhorcadoProtocol() {
    this.estado = Status.WAITING_PLAYERS;
  }

  public Status getEstado() {
    return this.estado;
  }

  public String procesarMensaje(String entrada) {

    if (estado == Status.WAITING_PLAYERS) {

      mensaje = "waiting";
      estado = Status.WAITING_GAME;

    } else if (estado == Status.WAITING_GAME) {

      mensaje = "welcome";
      estado = Status.PLAYING;

    } else if (estado == Status.PLAYING) {

      mensaje = "playing";
      estado = Status.CHECKING;

    } else if (estado == Status.CHECKING) {

      mensaje = "checking;" + entrada;
      estado = Status.PLAYING;
      if (entrada.contains("win") || entrada.contains("lose")) {
        estado = Status.END;
      }

    } else if (estado == Status.END) {
      if (entrada.equalsIgnoreCase("S")) {
        mensaje = "new_game";
        estado = Status.PLAYING;
      } else if (entrada.equalsIgnoreCase("N")) {
        mensaje = "bye";
        estado = Status.WAITING_PLAYERS;
      }
    }

    return mensaje;

  }

}
