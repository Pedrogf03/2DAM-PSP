public class SacarDineroSync extends Thread {

  private CuentaSync c;

  public SacarDineroSync(String n, CuentaSync c) {
    super(n);
    this.c = c;
  }

  public void run() {
    for (int i = 1; i <= 4; i++) {
      c.retirarDinero(10, getName());
      try {
        sleep(500);
      } catch (InterruptedException e) {
      }
    }
  }
}
