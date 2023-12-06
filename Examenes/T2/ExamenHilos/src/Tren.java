import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.LinkedList;

public class Tren {

  //TO-DO
  private int viajes;
  private int capacidadVagon;
  private boolean viajando = false;
  private Map<Integer, LinkedList<Pasajero>> vagon = new TreeMap<>(); // Mapa que contiene como clave el numero del vagon y de valor una lista de pasajeros.

  public Tren(int capacidadVagon, int viajes) {
    //TO-DO
    this.viajes = viajes;
    this.capacidadVagon = capacidadVagon;
    for (int i = 0; i < 2; i++) { // 2 debido que solamente existen dos vagones en nuestro tren.
      vagon.put(i + 1, new LinkedList<>());
    }
  }

  public int getViajes() {
    return viajes;
  }

  // Devuelve true si NO quedan viajes.
  private boolean viajesDone() {
    return viajes <= 0;
  }

  // Devuelve true si ambos vagones están llenos.
  private boolean isLleno() {
    return vagon.get(1).size() >= capacidadVagon && vagon.get(2).size() >= capacidadVagon;
  }

  // Devuelve true si el pasajero ya se encuentra en el tren, en cualquiera de los dos vagones.
  private boolean sentado(Pasajero p) {
    return vagon.get(1).contains(p) || vagon.get(2).contains(p);
  }

  public synchronized void viajar(Pasajero p) throws InterruptedException {
    //TO-DO

    while (sentado(p))
      wait(); // Si el pasajero ya está viajando, debe esperar.

    while (isLleno() || viajando || viajesDone()) {
      System.out.println("No puede subir. El pasajero " + p + " espera");
      wait(); // Si el tren está lleno, viajando o no quedan viajes, los pasajeros han de esperar.
    }

    if (vagon.get(1).size() < capacidadVagon) {
      vagon.get(1).add(p); // Si el primer vagon NO está lleno, se puede subir el pasajero.
      System.out.println("El pasajero " + p + " se sube al vagón 1");
    } else if (vagon.get(2).size() < capacidadVagon) {
      vagon.get(2).add(p); // Si el primer vagon está lleno, pero el segundo NO, se puede subir el pasajero al segundo vagón.
      if (isLleno()) {
        System.out.println("El pasajero " + p + " se sube al vagón 2 y es el último en entrar");
        System.out.println("Tren lleno"); // Si el tren está lleno, se notifica para que el maquinista pueda iniciar el viaje.
        notifyAll();
      } else {
        System.out.println("El pasajero " + p + " se sube al vagón 2");
      }
    }

  }

  public synchronized void empezarViaje() throws InterruptedException {
    //TO-DO
    while (!isLleno())
      wait(); // Mientras el tren no se llene, se esperará.

    viajando = true;
    LocalDateTime now = LocalDateTime.now();
    System.out.println("El Maquinista grita: 'il viaggio comincia' " + now.getMinute() + ":" + now.getSecond());
  }

  public synchronized void finalizarViaje() throws InterruptedException {
    //TO-DO
    LocalDateTime now = LocalDateTime.now();
    System.out.println("El Maquinista grita: 'il viaggio finisce' " + now.getMinute() + ":" + now.getSecond());

    bajarPasajeros();

    System.out.println("------------ FIN VIAJE ------------");

    viajes--;
    viajando = false; // Una vez que hayan bajado todos los pasajeros, es realmente cuando el viaje acaba, y ya pueden entrar nuevos pasajeros.

    notifyAll(); // Se notifica que se ha acabado el viaje.

    if (viajesDone()) {
      System.out.println("No hay más viajes");
    }

  }

  // Función para bajar a los pasajeros del tren.
  public synchronized void bajarPasajeros() {
    //TO-DO
    for (Map.Entry<Integer, LinkedList<Pasajero>> entry : vagon.entrySet()) {
      // Se recorre el mapa de vagones, en orden del primero al último.
      for (int i = 0; i < capacidadVagon; i++) {
        Pasajero p = entry.getValue().poll(); // Se van bajando los pasajeros.
        System.out.println("Se baja el pasajero " + p + " del vagón " + entry.getKey());
      }
    }

  }

}