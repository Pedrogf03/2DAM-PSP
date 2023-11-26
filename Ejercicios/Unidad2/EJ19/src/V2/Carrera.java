package V2;

import java.util.ArrayList;

public class Carrera {

  public static void main(String[] args) {

    ArrayList<Equipo> equipos = new ArrayList<>();

    for (int i = 1; i <= 8; i++)
      equipos.add(new Equipo(i));

    System.out.println("Preparados ...");
    System.out.println("Listos ...");
    System.out.println("Bang!!");
    System.out.println("----------------------");

    for (Equipo equipo : equipos)
      equipo.start();

    try {

      for (Equipo equipo : equipos)
        equipo.join();

    } catch (InterruptedException e) {
    }

    long minTiempo = equipos.get(0).getTiempoTotal();
    for (Equipo equipo : equipos)
      if (equipo.getTiempoTotal() < minTiempo)
        minTiempo = equipo.getTiempoTotal();

    for (Equipo equipo : equipos)
      if (equipo.getTiempoTotal() == minTiempo)
        System.out.println(
            "Ha ganado el equipo " + equipo.getIdEquipo() + ", tiempo total: " + equipo.getTiempoTotal() / 1000 + "\"");

  }

}
