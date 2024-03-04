package version_d;

abstract public class Protocol {

  protected Status estado;
  protected String mensaje;

  public Protocol(Status s) {
    this.estado = s;
  }

  public Status getEstado() {
    return this.estado;
  }

  abstract public String procesarMensaje(String entrada);

}
