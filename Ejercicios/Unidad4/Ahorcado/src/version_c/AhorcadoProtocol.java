package version_c;

public class AhorcadoProtocol {

  private Estado estado;
  private String mensaje;

  public AhorcadoProtocol() {
    this.estado = Estado.WAITING_PLAYERS;
  }

  public Estado getEstado() {
    return this.estado;
  }

  public String procesarMensaje(String e) {

    if (estado == Estado.WAITING_PLAYERS) {

      mensaje = "waiting";
      estado = Estado.WAITING_GAME;

    } else if (estado == Estado.WAITING_GAME) {

      mensaje = "bienvenido";

      if (e.equals("tu_turno")) {
        estado = Estado.PLAYING;
      } else {
        estado = Estado.WAITING_PLAY;
      }

    } else if (estado == Estado.PLAYING) {

      mensaje = "playing";
      estado = Estado.CHECKING;

    } else if (estado == Estado.WAITING_PLAY) {

      mensaje = "otro_turno";
      if (e.equals("tu_turno")) {
        mensaje = "playing";
        estado = Estado.CHECKING;
      }

    } else if (estado == Estado.CHECKING) {

      mensaje = "checking;" + e;
      estado = Estado.WAITING_PLAY;

    } else if (estado == Estado.END) {

    }

    return mensaje;

  }

}
