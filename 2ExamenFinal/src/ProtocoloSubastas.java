public class ProtocoloSubastas {

  private Estado estado;
  private String mensaje;

  public ProtocoloSubastas() {
    this.estado = Estado.CONECTADO;
  }

  public String procesarMensaje(String entrada) {

    if (entrada != null && entrada.equals("fin")) {
      mensaje = "fin";
      estado = Estado.FIN_SUBASTA;
    } else {
      if (estado == Estado.CONECTADO) {
        mensaje = "conectado";
        if (entrada != null) {
          if (entrada.equals("listar")) {
            mensaje = "listar";
            estado = Estado.LISTANDO;
          } else if (entrada.equals("inscribir")) {
            mensaje = "inscribir";
            estado = Estado.INSCRIBIENDO;
          }
        }
      } else if (estado == Estado.LISTANDO) {
        mensaje = "conectado";
        estado = Estado.CONECTADO;
      } else if (estado == Estado.INSCRIBIENDO) {
        mensaje = "esperando";
        estado = Estado.ESPERANDO_SUBASTA;
      } else if (estado == Estado.ESPERANDO_SUBASTA) {
        mensaje = "inicio";
        estado = Estado.SUBASTANDO;
      } else if (estado == Estado.SUBASTANDO) {
        mensaje = "subastando";
        estado = Estado.COMPROBANDO;
      } else if (estado == Estado.COMPROBANDO) {
        mensaje = "comprobando;" + entrada;
        estado = Estado.SUBASTANDO;
      }
    }

    return mensaje;

  }

}
