import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    int numJug = 0;

    System.out.print("Número de jugadores: ");

    try {
      numJug = Integer.parseInt(sc.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("No ha introducido un número.");
      System.exit(0);
    } finally {
      sc.close();
    }

    Arbitro arbi = new Arbitro(numJug);
    System.out.println("NÚMERO A ADIVINAR: " + arbi.num);

    ArrayList<Jugador> jugadores = new ArrayList<>();

    for (int i = 1; i <= numJug; i++) {
      jugadores.add(new Jugador(i, arbi));
    }

    for (Jugador j : jugadores) {
      j.start(); // Iniciar cada jugador.
    }

  }

}
