package V3;

import java.util.ArrayList;
import java.util.Map;

public class Main {

  public static void main(String[] args) {

    Deposito d = new Deposito();

    Productor p = new Productor(d);
    Consumidor c = new Consumidor(d);

    p.start();
    c.start();

    Thread espera = new Thread(() -> {
      boolean a = false;
      while (c.isAlive()) {
        if (!a) {
          System.out.print("Esperando que el consumidor termine");
          a = true;
        } else {
          System.out.print(".");
        }
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
      }
      System.out.println();
    });

    espera.start();

    try {
      c.join();
      espera.interrupt();
    } catch (InterruptedException e) {
    }

    for (Map.Entry<String, ArrayList<Integer>> entry : c.factores.entrySet()) {
      System.out.print(entry.getKey() + ": ");
      if (entry.getValue().isEmpty()) {
        System.out.print("es primo");
      } else {
        for (int num : entry.getValue())
          System.out.print(num + " ");

      }
      System.out.println();
    }

  }

}