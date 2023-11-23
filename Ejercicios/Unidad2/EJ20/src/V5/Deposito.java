package V5;

import java.util.Stack;

public class Deposito {

  public Stack<Integer> place;

  public Deposito() {
    place = new Stack<>();
  }

  public synchronized void depositar(int num) throws InterruptedException {
    while (place.size() >= 5)
      wait();

    place.push(num);
    notifyAll();
  }

  public synchronized int sacar() throws InterruptedException {
    while (place.empty()) {
      wait();
    }

    int num = place.pop();

    notifyAll();

    return num;
  }

}
