public class App {

  public static void main(String[] args) throws Exception {
    Thread t1 = new Thread() {
      public void run() {
        for (int i = 0; i < 5; i++) {
          System.out.println("Pato " + i);
          try {
            Thread.sleep(1000);
          } catch (InterruptedException ignore) {}
        }
      }
    };

    Thread t2 = new Thread() {
      public void run() {
        for (int i = 0; i < 5; i++) {
          System.out.println("Mono " + i);
          try {
            Thread.sleep(1000);
          } catch (InterruptedException ignore) {}
        }
      }
    };

    Thread t3 = new Thread() {
      public void run() {
        for (int i = 0; i < 5; i++) {
          System.out.println("Cerdo " + i);
          try {
            Thread.sleep(1000);
          } catch (InterruptedException ignore) {}
        }
      }
    };

    t1.start();
    t2.start();
    t3.start();
  }
}
