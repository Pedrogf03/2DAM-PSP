public class Hilo1 extends Thread {

  @Override
  public void run() {
    for (int i = 0; i <= 100; i++) {
      try {
        System.out.println("Pares: " + i);
        sleep(1000);
      } catch (InterruptedException e) {
        if (i % 2 == 0) {
          break;
        } else {
          continue;
        }
      }
    }
  }
}
