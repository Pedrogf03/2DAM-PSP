package version_d;

public class LoginProtocol extends Protocol {

  public LoginProtocol() {
    super(Status.LOGIN);
  }

  @Override
  public String procesarMensaje(String entrada) {

    if (estado == Status.LOGIN) {

      mensaje = "login";
      estado = Status.CHECKINGLOGIN;

    } else if (estado == Status.CHECKINGLOGIN) {

      if (entrada.equals("true")) {
        estado = Status.WAITING_PLAYERS;
        mensaje = "checkingLogin;true";
      } else {
        estado = Status.LOGIN;
        mensaje = "checkingLogin;false";
      }

    } else if (estado == Status.WAITING_PLAYERS) {

      mensaje = "waiting";

    }

    return mensaje;

  }

}
