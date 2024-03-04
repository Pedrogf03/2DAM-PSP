public class TestMessageBox {
  public static void main(String[] args) {
    final MessageBox box = new MessageBox();

    Thread producerThread = new Thread() {
      public void run() {
        System.out.println("Producer thread started...");
        for (int i = 1; i <= 6; ++i) {
          box.putMessage("message " + i);
          System.out.println("Put message " + i);
        }
      }
    };

    Thread consumerThread1 = new Thread() {
      public void run() {
        System.out.println("Consumer thread 1 started...");
        for (int i = 1; i <= 3; ++i) {
          System.out.println("Consumer thread 1 Get " + box.getMessage());
        }
      }
    };

    Thread consumerThread2 = new Thread() {
      public void run() {
        System.out.println("Consumer thread 2 started...");
        for (int i = 1; i <= 3; ++i) {
          System.out.println("Consumer thread 2 Get " + box.getMessage());
        }
      }
    };

    consumerThread1.start();
    consumerThread2.start();
    producerThread.start();
  }
}