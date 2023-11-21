import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Solution {
  public static void main(String[] args) throws InterruptedException {
    // create an object of BlockingQueue
    BlockingQueue<Integer> bufrShr = new LinkedBlockingQueue<>();

    // passing object of BlockingQueue as arguments
    Producer threadProd = new Producer(bufrShr);
    Consumer threadCon = new Consumer(bufrShr);

    // to start the process
    threadProd.start();
    threadCon.start();

    // to exit the process after 5 sec
    Thread.sleep(5000);
    System.exit(0);
  }
}