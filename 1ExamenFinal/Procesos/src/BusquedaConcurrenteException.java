public class BusquedaConcurrenteException extends Exception {

  private String msg;

  public BusquedaConcurrenteException(String msg) {
    this.msg = msg + "\nUso correcto -n <numero> <archivo> [<archivo>...] [-n <numero> <archivo>...]";
  }

  public void printMsg() {
    System.out.println(msg);
  }

}
