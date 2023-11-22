package V1;

import java.util.Stack;

public class Deposito {

  private Stack<Integer> place;

  public Deposito() {
    place = new Stack<>();
  }

  public synchronized void depositar(int num) {
    while (place.size() >= 10) {
      try {
        System.out.println("Deposito lleno.");
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
