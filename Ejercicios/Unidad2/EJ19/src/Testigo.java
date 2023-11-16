import java.util.Date;
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

      long sale = (System.currentTimeMillis());
      Date saleDate = new Date(sale);
      System.out.println("Sale: " + saleDate);

      Thread.sleep(new Random().nextInt(2000) + 9000);

      long llega = (System.currentTimeMillis());
      Date llegaDate = new Date(System.currentTimeMillis());
      System.out.println("Sale: " + llegaDate);

      System.out.println("Tiempo (s): " + ((llega - sale) / 1000));

      System.out.println();

      orden++;
      notifyAll();
    } catch (Exception e) {
    }
  }

}
