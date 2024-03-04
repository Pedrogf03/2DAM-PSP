public class HiloTic extends Thread {

  @Override
  public void run() {
    while (true) {
      System.out.println("TIC");
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
