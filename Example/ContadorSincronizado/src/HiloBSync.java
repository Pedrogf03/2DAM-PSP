public class HiloBSync extends Thread {

  private Contador contador;

  public HiloBSync(String n, Contador c) {
    setName(n);
    contador = c;
  }

  public void run() {
    synchronized (contador) {
      for (int j = 0; j < 300; j++)
        contador.decrementa();
      System.out.println("Fin " + getName() + " contador vale " + contador.valor());
    }
  }
}
