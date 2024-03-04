public class HiloTac extends Thread {

  @Override
  public void run() {
    while (true) {
      System.out.println("TAC");
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
