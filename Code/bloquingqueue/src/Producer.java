import java.util.concurrent.BlockingQueue;

class Producer extends Thread {
  protected BlockingQueue<Integer> blcque;

  Producer(BlockingQueue<Integer> blcque) { // constructor
    this.blcque = blcque;
  }

  public void run() { // overriding run method
    while (true) {
      for (int i = 1; i <= 5; i++) {
        try {
          System.out.println("Producer is running " + i);
          blcque.put(i); // to produce data
          // produce data with an interval of 1 sec
          Thread.sleep(1000);
        }
        // to handle exception
        catch (InterruptedException exp) {
          System.out.println("An interruption occurred at Producer");
        }
      }
    }
  }
}