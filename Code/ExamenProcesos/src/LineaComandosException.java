public class LineaComandosException extends Exception {

  String msg;

  public LineaComandosException(String msg) {
    this.msg = msg;
  }

  public void printMsg() {
    System.out.println(this.msg);
  }
}
