package V2;

public class Main {

  public static void main(String[] args) {

    Deposito d = new Deposito();

    Productor p = new Productor(d);
    Consumidor c = new Consumidor(d);

    p.start();
    c.start();

  }

}