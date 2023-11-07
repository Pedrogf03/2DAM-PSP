public class HiloASync extends Thread {

  private Contador contador;

  public HiloASync(String n, Contador c) {
    setName(n);
    contador = c;
  }

  public void run() {
    synchronized (contador) {
      for (int j = 0; j < 300; j++)
        contador.incrementa();
      System.out.println("Fin " + getName() + " contador vale " + contador.valor());
    }
  }
}
