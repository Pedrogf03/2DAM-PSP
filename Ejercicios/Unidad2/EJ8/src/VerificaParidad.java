import java.util.ArrayList;
import java.util.List;

public class VerificaParidad {

  public static void main(String[] args) throws Exception {
    List<Thread> hilos = new ArrayList<>();

    for (String string : args) {
      Thread th = new Thread() {
        public void run() {
          if (Integer.parseInt(string) % 2 == 0) {
            System.out.println(Integer.parseInt(string) + " es par.");
          } else {
            System.out.println(Integer.parseInt(string) + " es impar.");
          }
        }
      };
      hilos.add(th);
    }
    for (Thread th : hilos) {
      th.start();
    }
  }
}
