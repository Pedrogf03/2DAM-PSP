import java.util.ArrayList;
import java.util.List;

public class VerificaPrimos implements Runnable {

  private int num;

  public VerificaPrimos(int num) {
    this.num = num;
  }

  @Override
  public void run() {
    if (num > 1) {
      Boolean esPrimo = true;

      for (int i = 2; i <= Math.sqrt(num); i++) {
        if (num % i == 0) {
          esPrimo = false;
        }
      }

      if (esPrimo) {
        System.out.println(num + " es primo.");
      } else {
        System.out.println(num + " no es primo.");
      }
    } else {
      System.out.println(num + " no es primo.");
    }
  }

  public static void main(String[] args) {
    List<Thread> hilos = new ArrayList<>();

    for (String string : args) {
      hilos.add(new Thread(new VerificaPrimos(Integer.parseInt(string))));
    }

    for (Thread h : hilos) {
      h.start();
    }
  }
}
