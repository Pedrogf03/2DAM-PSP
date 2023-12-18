import java.util.LinkedList;
import java.util.List;

public class Principal {

  public static void main(String[] args) {

    final int capacidadVagon = 3; //se puede cambiar
    final int nPasajeros = capacidadVagon * 4; //se puede cambiar
    final int nViajes = 2; //se puede cambiar

    Tren tren = new Tren(capacidadVagon, nViajes);

    Maquinista m = new Maquinista("Vittorio", tren);
    new Thread(m).start();

    List<Pasajero> pasajeros = new LinkedList<>();
    for (int i = 1; i <= nPasajeros; i++)
      pasajeros.add(new Pasajero(i, tren));

    for (Pasajero p : pasajeros)
      new Thread(p).start();
  }
}