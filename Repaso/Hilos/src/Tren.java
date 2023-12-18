import java.time.LocalDateTime;
import java.util.LinkedList;

public class Tren {
  //TO-DO
  private int viajes;
  private int capacidadVagon;

  private LinkedList<Pasajero> vagon1 = new LinkedList<>();
  private LinkedList<Pasajero> vagon2 = new LinkedList<>();

  private boolean puedeSubir = true;
  private boolean viajando = false;
  private boolean finViaje = false;

  public Tren(int capacidadVagon, int viajes) {
    //TO-DO
    this.viajes = viajes;
    this.capacidadVagon = capacidadVagon;
  }

  private boolean vagonLleno(LinkedList<Pasajero> vagon) {
    return vagon.size() >= capacidadVagon;
  }

  private boolean trenLleno() {
    return vagonLleno(vagon1) && vagonLleno(vagon2);
  }

  private boolean quedanViajes() {
    return viajes > 0;
  }

  public int getViajes() {
    return viajes;
  }

  public synchronized void viajar(Pasajero p) throws InterruptedException {
    //TO-DO

    while (viajando)
      wait();

    while (!puedeSubir) {
      System.out.println("No se puede subir. El pasajero " + p + " espera");
      wait();
    }

    if (!vagonLleno(vagon1)) {
      System.out.println("Se sube el pasajero " + p + " al primer vagón");
      vagon1.add(p);
    } else if (!vagonLleno(vagon2)) {
      System.out.print("Se sube el pasajero " + p + " al segundo vagón");
      vagon2.add(p);
      if (vagonLleno(vagon2)) {
        viajando = true;
        puedeSubir = false;
        System.out.println(" y es el último en subir");
        System.out.print("Tren lleno");
        notifyAll();
      }
      System.out.println();
    }

    while (!viajando)
      wait();

    while (!finViaje)
      wait();

    if (!vagon1.isEmpty()) {
      System.out.println("Se baja el pasajero " + vagon1.poll() + " del primer vagón");
    } else if (!vagon2.isEmpty()) {
      System.out.println("Se baja el pasajero " + vagon2.poll() + " del primer vagón");
      if (!vagon2.isEmpty())
        notifyAll();
    }

  }

  public synchronized void empezarViaje() throws InterruptedException {
    //TO-DO

    while (!trenLleno())
      wait();

    LocalDateTime now = LocalDateTime.now();
    System.out.println("El Maquinista grita: 'il viaggio comincia' " + now.getMinute() + ":" + now.getSecond());
  }

  public synchronized void finalizarViaje() throws InterruptedException {
    //TO-DO
    LocalDateTime now = LocalDateTime.now();
    System.out.println("El Maquinista grita: 'il viaggio finisce' " + now.getMinute() + ":" + now.getSecond());
    //TO-DO

    finViaje = true;

    notifyAll();

    while (!vagon1.isEmpty() || !vagon2.isEmpty())
      wait();

    System.out.println("----- FIN VIAJE -----");

    finViaje = false;
    viajando = false;
    viajes--;

    if (!quedanViajes()) {
      System.out.println("No quedan viajes");
    } else {
      puedeSubir = true;
    }

    notifyAll();

  }
}