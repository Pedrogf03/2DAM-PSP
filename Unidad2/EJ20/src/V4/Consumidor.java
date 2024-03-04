package V4;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Consumidor extends Thread {

  private Deposito d;
  public Map<String, ArrayList<Integer>> factores;
  // Clave: Contador + "numero", valor: lista de factores primos
  private int contador = 0;

  public Consumidor(Deposito d) {
    this.d = d;
    factores = new TreeMap<>();
  }

  @Override
  public void run() {
    while (true) {
      try {
        descomponerPrimos(d.sacar());
        sleep(new Random().nextInt(3000) + 1000);
      } catch (InterruptedException e) {
        return;
      }
    }
  }

  public void descomponerPrimos(int num) {

    String clave = contador + "º- " + num;

    contador++;

    long numero = num;

    factores.put(clave, new ArrayList<Integer>());

    for (int i = 2; i < numero; i++) {
      while (num % i == 0) {

        num = num / i;
        factores.get(clave).add(i);

      }
    }

  }

  public boolean done() {
    return d.place.isEmpty();
  }

}
