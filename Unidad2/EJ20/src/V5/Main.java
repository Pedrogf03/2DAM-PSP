package V5;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    Deposito d = new Deposito();

    Scanner sc = new Scanner(System.in);

    System.out.print("Indique el número de productores: ");
    int numP = Integer.parseInt(sc.nextLine());

    List<Productor> productores = new ArrayList<>();
    for (int i = 0; i < numP; i++)
      productores.add(new Productor(d));

    System.out.print("Indique el número de consumidores: ");
    int numC = Integer.parseInt(sc.nextLine());

    List<Consumidor> consumidores = new ArrayList<>();
    for (int i = 0; i < numC; i++)
      consumidores.add(new Consumidor(d));

    for (Productor p : productores)
      p.start();

    for (Consumidor c : consumidores)
      c.start();

    try {
      while (true) {
        System.out.println("¿Cuanto tiempo más quiere seguir esperando (s | 0 para acabar)?");
        int time = Integer.parseInt(sc.nextLine());
        if (time == 0) {
          System.out.print("Parando la producción");
          for (Productor p : productores) {
            p.interrupt();
          }

          System.out.println();

          System.out.print("Esperando a que los consumidores acaben");
          for (Consumidor c : consumidores) {
            while (!c.done()) {
              c.join(1000);
              System.out.print(".");
            }

            System.out.println();

            while (c.isAlive())
              c.interrupt();
          }
          break;
        } else {
          Thread.sleep(time * 1000);
        }
      }
    } catch (InterruptedException e) {
    }

    sc.close();

    int cont = 0;
    for (Consumidor c : consumidores) {
      System.out.println("Consumidor " + cont++ + ": ");
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

}