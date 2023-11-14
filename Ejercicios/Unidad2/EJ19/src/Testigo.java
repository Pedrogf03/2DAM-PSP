import java.util.Random;

public class Testigo {

  public synchronized void quieroCorrer(int id) {
    System.out.println("Atleta: Atleta " + id);
    System.out.println("Sale: " + System.currentTimeMillis());
    try {
      Thread.sleep(new Random().nextInt(3) + 9);
    } catch (Exception ignore) {
    }
  }

}
