import java.time.LocalDateTime;
import java.util.LinkedList;

public class Tren {
  //TO-DO
  private int viajes;
  private LinkedList<Pasajero> vagon1;
  private LinkedList<Pasajero> vagon2;
  private int capacidadVagon;
  private boolean viajando = false;

  private boolean finViaje;

  public Tren(int capacidadVagon, int viajes) {
    //TO-DO
    this.viajes = viajes;
    this.capacidadVagon = capacidadVagon;
    this.vagon1 = new LinkedList<>();
    this.vagon2 = new LinkedList<>();
  }

  public int getViajes() {
    return viajes;
  }

  // Devuelve true si NO quedan viajes.
  private boolean viajesDone() {
    return viajes <= 0;
  }

  private boolean isLleno(LinkedList<Pasajero> vagon) {
    return vagon.size() >= capacidadVagon;
  }

  public synchronized void viajar(Pasajero p) throws InterruptedException {
    //TO-DO

    while ((isLleno(vagon1) && isLleno(vagon2)) || viajando || viajesDone()) {
      System.out.println("\tNo puede subir. El pasajero " + p + " espera");
      wait();
    }

    if (!isLleno(vagon1)) {
      vagon1.add(p);
      System.out.println("El pasajero " + p + " se sube al primer vagón");
    } else if (!isLleno(vagon2)) {
      vagon2.add(p);
      if (isLleno(vagon2)) {
        viajando = true;
        System.out.println("El pasajero " + p + " se sube al segundo vagón y es el último en subir");
        System.out.println("Tren lleno");
        notifyAll();
      } else {
        System.out.println("El pasajero " + p + " se sube al segundo vagón");
      }
    }

    while (!viajando)
      wait();

    while (!finViaje)
      wait();

    // Se bajan los pasajeros
    if (!vagon1.isEmpty()) {
      System.out.println("Se baja el pasajero " + vagon1.poll() + " del primer vagón");
    } else if (!vagon2.isEmpty()) {
      System.out.println("Se baja el pasajero " + vagon2.poll() + " del segundo vagón");
      if (vagon2.isEmpty()) {
        finViaje = false;
      }
    }

    notifyAll();

  }

  public synchronized void empezarViaje() throws InterruptedException {
    //TO-DO

    while (!isLleno(vagon1) || !isLleno(vagon2))
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

    viajando = false;
    viajes--;

    System.out.println("------------ FIN VIAJE ------------");

    if (viajesDone()) {
      System.out.println("NO QUEDAN VIAJES");
    }

  }
}