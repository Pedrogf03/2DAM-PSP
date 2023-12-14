import java.time.LocalDateTime;
import java.util.LinkedList;

public class Tren {
  //TO-DO
  private int viajes;
  private int capacidadVagon;

  private LinkedList<Pasajero> vagon1 = new LinkedList<>();
  private LinkedList<Pasajero> vagon2 = new LinkedList<>();

  private boolean viajando = false;
  private boolean finViaje = false;
  private boolean puedeSubir = true;

  public Tren(int capacidadVagon, int viajes) {
    //TO-DO
    this.viajes = viajes;
    this.capacidadVagon = capacidadVagon;
  }

  public int getViajes() {
    return viajes;
  }

  private boolean trenLleno() {
    return vagon1.size() >= capacidadVagon && vagon2.size() >= capacidadVagon;
  }

  private boolean quedanViajes() {
    return viajes > 0;
  }

  public synchronized void viajar(Pasajero p) throws InterruptedException {
    //TO-DO

    while (!puedeSubir) {
      System.out.println("No se puede subir. El pasajero " + p + " espera");
      wait();
    }

    if (vagon1.size() < capacidadVagon) {
      vagon1.add(p);
      System.out.println("Se sube el pasajero " + p + " al primer vagón");
    } else if (vagon2.size() < capacidadVagon) {
      vagon2.add(p);
      if (trenLleno()) {
        viajando = true;
        puedeSubir = false;
        System.out.println("Se sube el pasajero " + p + " al segundo vagón y es el último en entrar");
        notifyAll();
      } else {
        System.out.println("Se sube el pasajero " + p + " al segundo vagón");
      }
    }

    while (!viajando)
      wait();

    while (!finViaje)
      wait();

    if (!vagon1.isEmpty()) {
      System.out.println("Se baja el pasajero " + vagon1.poll() + " del primer vagón.");
    } else if (!vagon2.isEmpty()) {
      System.out.println("Se baja el pasajero " + vagon2.poll() + " del segundo vagón.");
      if (vagon2.isEmpty()) {
        notifyAll();
      }
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
    viajando = false;
    viajes--;

    notifyAll();

    while (!vagon1.isEmpty() || !vagon2.isEmpty())
      wait();

    System.out.println("----- FIN DE VIAJE -----");

    finViaje = false;
    if (!quedanViajes()) {
      System.out.println("No quedan viajes");
    } else {
      puedeSubir = true;
    }

  }
}
