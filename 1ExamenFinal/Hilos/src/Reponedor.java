import java.util.Random;

public class Reponedor extends Thread {

  private Supermercado s;

  public Reponedor(Supermercado s) {
    this.s = s;
  }

  public void run() {
    boolean fin = false;
    Random r = new Random();
    while (!fin) {
      try {
        s.esperarPeticion();
        Thread.sleep(r.nextInt(200));
        s.nuevoSuministro();
      } catch (InterruptedException e) {
        fin = true;
      }
    }
    System.out.println("El reponedor se va a su casa");
  }
}