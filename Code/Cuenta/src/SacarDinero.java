public class SacarDinero extends Thread {
  private Cuenta c;

  public SacarDinero(String n, Cuenta c) {
    super(n);
    this.c = c;
  }

  @Override
  public void run() {
    for (int i = 1; i <= 4; i++) {
      c.retirarDinero(10, getName());
    }
  }
}
