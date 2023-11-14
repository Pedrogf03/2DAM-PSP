public class HiloCadena extends Thread {
  private ObjetoCompartido objeto;
  private String cadena;

  public HiloCadena(ObjetoCompartido c, String s) {
    this.objeto = c;
    this.cadena = s;
  }

  public void run() {
    for (int i = 1; i < 10; i++)
      objeto.pintaCadena(cadena);
    System.out.println("\n" + cadena + " finalizado");
  }
}
