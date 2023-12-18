public class CommandLineException extends Exception {

  private String msg;

  public CommandLineException(String msg) {
    this.msg = msg + "\n"
        + "Uso correcto: -cp 'ruta/del/classpath' -if 'ruta/fichero/diccionario' [-wd 'ruta/directorio/trabajo']";
  }

  public String getMsg() {
    return msg;
  }

}
