import java.util.Random;

public class Testigo {

  private int orden;

  public Testigo() {
    this.orden = 1;
  }

  public synchronized void quieroCorrer(int id) {
    try {
      while (id != orden) {
        wait();
      }
      System.out.println("Atleta: Atleta " + id);

      long sale = System.currentTimeMillis();
      System.out.println("Sale: " + sale);

      Thread.sleep(new Random().nextInt(2000) + 9000);

      long llega = System.currentTimeMillis();
      System.out.println("Sale: " + llega);

      System.out.println("Tiempo (milis): " + (llega - sale));

      System.out.println();

      orden++;
      notifyAll();
    } catch (Exception e) {
    }
  }

}
