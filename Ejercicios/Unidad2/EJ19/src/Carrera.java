public class Carrera {

  public static void main(String[] args) {

    Testigo t = new Testigo();

    Atleta a1 = new Atleta(t, 1);
    Atleta a2 = new Atleta(t, 2);
    Atleta a3 = new Atleta(t, 3);
    Atleta a4 = new Atleta(t, 4);

    Thread t1 = new Thread(a1);
    Thread t2 = new Thread(a2);
    Thread t3 = new Thread(a3);
    Thread t4 = new Thread(a4);

    System.out.println("Preparados ...");
    System.out.println("Listos ...");
    System.out.println("Bang!!");
    System.out.println("----------------------");

    t1.start();
    t2.start();
    t3.start();
    t4.start();

  }

}
