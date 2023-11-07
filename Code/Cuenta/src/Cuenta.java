public class Cuenta {
  private int saldo;

  Cuenta(int saldo) {
    this.saldo = saldo;
  }

  public int getSaldo() {
    return saldo;
  }

  public void restar(int cantidad) {
    saldo -= cantidad;
  }

  public void retirarDinero(int cantidad, String nombre) {
    if (getSaldo() >= cantidad) {
      System.out.println(nombre + ": se va a retirar dinero. El saldo actual es: " + getSaldo());
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
      }
      restar(cantidad);
      System.out.println(nombre + ": retira " + cantidad + ". El saldo actual es: " + getSaldo());
    } else
      System.out.println(nombre + " no se puede retirar dinero. No hay saldo: " + getSaldo());
    if (getSaldo() < 0)
      System.out.println("Saldo negativo: " + getSaldo());
  }
}
