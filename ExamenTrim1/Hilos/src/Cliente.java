import java.util.Random;

public class Cliente extends Thread {
  private int id;
  private Supermercado s;

  public Cliente(Supermercado s, int id) {
    this.s = s;
    this.id = id;
  }

  public void run() {
    try {
      Random r = new Random();
      Thread.sleep(r.nextInt(1000));
      s.comprarLatas(this, r.nextInt(s.getMaximo()) + 1); //Si maximo es 1 compra 1 lata porque un numero aleatorio entre [0,1) es siempre 0
      Thread.sleep(r.nextInt(200));
      s.pagar(this);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String toString() {
    return "" + id;
  }

}