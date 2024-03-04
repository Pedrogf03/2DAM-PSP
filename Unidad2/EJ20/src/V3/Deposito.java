package V3;

import java.util.Stack;

public class Deposito {

  private Stack<Integer> place;

  public Deposito() {
    place = new Stack<>();
  }

  public synchronized void depositar(int num) {
    while (place.size() >= 5) {
      try {
        wait();
      } catch (InterruptedException e) {
      }
    }

    notifyAll();
    place.push(num);
  }

  public synchronized int sacar() {
    while (place.empty()) {
      try {
        wait();
      } catch (InterruptedException e) {
      }
    }

    int num = place.pop();

    notifyAll();

    return num;
  }

}
