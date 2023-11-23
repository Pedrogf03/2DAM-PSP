package V4;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    Deposito d = new Deposito();

    Productor p = new Productor(d);
    Consumidor c = new Consumidor(d);

    p.start();
    c.start();

    Scanner sc = new Scanner(System.in);

    try {

      while (c.isAlive() || p.isAlive()) {
        System.out.println("¿Cuanto tiempo más quiere seguir esperando (s | 0 para acabar)?");
        int time = Integer.parseInt(sc.nextLine());
        if (time == 0) {
          p.interrupt();

          System.out.print("Esperando a que el consumidor acabe");
          while (!c.done()) {
            c.join(1000);
            System.out.print(".");
          }

          c.interrupt();

          System.out.println();
          break;
        } else {
          c.join(time * 1000);
        }
      }

    } catch (InterruptedException e) {
    }

    sc.close();

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