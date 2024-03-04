public class CommandLineException extends Exception {

  private String msg;

  public CommandLineException(int num) {
    if (num == 1) {
      this.msg = "Se esperaba el parámetro '-d'";
    } else if (num == 2) {
      this.msg = "Se esperaba el parámetro '-p'";
    } else if (num == 3) {
      this.msg = "Se esperaba el parámetro '-m'";
    }

    this.msg += "\n Uso correcto: java Cliente -d <dirección> -p <puerto> -m <palabra>";

  }

  public void printError() {
    System.out.println(msg);
  }

}
