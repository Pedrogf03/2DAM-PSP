public class CompartirSync {

  public static void main(String[] args) {
    Contador contador = new Contador(100);
    HiloASync a = new HiloASync("HiloA", contador);
    HiloBSync b = new HiloBSync("HiloB", contador);
    b.start();
    a.start();
  }
}
