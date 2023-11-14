public class HiloCadena extends Thread {

  private ObjetoCompartido objeto;
  private String cadena;

  public HiloCadena(ObjetoCompartido c, String s) {
    this.objeto = c;
    this.cadena = s;
  }

  public void run() {
    synchronized (objeto) {
      for (int i = 1; i < 10; i++) {
        objeto.pintaCadena(cadena);
        objeto.notify(); //aviso de que ha usado el objeto
        try {
          objeto.wait(); // espera a que llegue un notify
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      objeto.notify(); //despertar a todos los wait sobre el objeto
    }
    System.out.println("\n" + cadena + " finalizado");
  }
}
