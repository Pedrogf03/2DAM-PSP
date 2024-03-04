import java.util.concurrent.BlockingQueue;

class Consumer extends Thread {
  protected BlockingQueue<Integer> blcque;

  Consumer(BlockingQueue<Integer> blcque) { // constructor
    this.blcque = blcque;
  }

  public void run() { // overriding run method
    try {
      while (true) {
        Integer elem = blcque.take(); // to consume data
        System.out.println("Consumer is running " + elem);
      }
    }
    // to handle exception
    catch (InterruptedException exp) {
      System.out.println("An interruption occurred at Producer");
    }
  }
}