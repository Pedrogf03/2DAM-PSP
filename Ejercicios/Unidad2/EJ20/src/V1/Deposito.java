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

  public synchronized void sacar() {
    while (place.empty()) {
      try {
        wait();
      } catch (InterruptedException e) {
      }
    }

    descomponerPrimos(place.pop());
    notifyAll();
  }

  public synchronized void descomponerPrimos(int num) {
    long numero = num;
    System.out.print("Descomposición de " + num + ": ");
    for (int i = 2; i < numero; i++) {
      while (num % i == 0) {
        num = num / i;
        System.out.print(i + " ");
      }
    }

    System.out.println();

  }

}
