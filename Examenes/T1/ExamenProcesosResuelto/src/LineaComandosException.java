public class LineaComandosException extends Exception {

  String msg;

  public LineaComandosException(String msg) {
    this.msg = msg
        + "\nUso correcto de la linea de comandos: -cp <classpath> -if <input file> [-wd <directorio de trabajo>]";
  }

  public void printMsg() {
    System.out.println(this.msg);
  }
}
