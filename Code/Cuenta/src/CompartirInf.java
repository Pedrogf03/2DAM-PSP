public class CompartirInf {

  public static void main(String[] args) {
    Cuenta cuenta = new Cuenta(40);
    SacarDinero h1 = new SacarDinero("Ana", cuenta);
    SacarDinero h2 = new SacarDinero("Juan", cuenta);
    h1.start();
    h2.start();
  }
}
