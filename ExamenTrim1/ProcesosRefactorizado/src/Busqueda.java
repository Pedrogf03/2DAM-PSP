import java.util.ArrayList;
import java.util.Scanner;

public class Busqueda {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    try {

      int numeroBuscar = Integer.parseInt(args[0]); // El número se recibe por terminal.
      ArrayList<Integer> numeros = new ArrayList<>();

      while (sc.hasNextLine()) {

        // Mientras que la cadena no sea "FIN", el bucle seguirá esperando numeros.
        String number = sc.nextLine();

        numeros.add(Integer.parseInt(number));

      }

      boolean encontrado = false;
      int indice = 0;

      for (int num : numeros) {

        if (num == numeroBuscar) {
          System.out.println("El numero " + numeroBuscar + " se encontró en el índice " + indice);
          encontrado = true;
          break;
        }
        indice++;

      }

      if (!encontrado) {
        System.out.println(encontrado);
      }

    } catch (NumberFormatException e) {
      System.out.println("Error. Se esperaba un número.");
    }

    sc.close();

  }

}
