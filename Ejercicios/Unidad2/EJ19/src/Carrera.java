import java.text.SimpleDateFormat;
import java.util.Date;

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

    long inicio = System.currentTimeMillis();

    t1.start();
    t2.start();
    t3.start();
    t4.start();

    try {
      t4.join();
    } catch (Exception e) {
    }

    long fin = System.currentTimeMillis();

    System.out.println("Se acabó la carrera");
    System.out.println("----------------------");

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    Date dateInicio = new Date(inicio);
    System.out.println("Inicio de la carrera " + formatter.format(dateInicio));

    Date dateFin = new Date(fin);
    System.out.println("Fin de la carrera " + formatter.format(dateFin));

    System.out.println("Duracion (milis): " + (fin - inicio));

  }

}
