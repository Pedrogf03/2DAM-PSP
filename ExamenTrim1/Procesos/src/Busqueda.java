import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Busqueda {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    try {

      int num = Integer.parseInt(args[0]); // El número se recibe por terminal.
      ArrayList<File> ficheros = new ArrayList<>();

      while (true) {

        // Mientras que la cadena no sea "FIN", el bucle seguirá esperando ficheros.
        String fileName = sc.nextLine();

        if (fileName.equals("FIN")) {
          break;
        }

        ficheros.add(new File(fileName));

      }

      int vector = 1;

      for (File f : ficheros) {
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {

          int indice = 0;

          boolean encontrado = false;

          String line;
          while ((line = reader.readLine()) != null) {
            if (Integer.parseInt(line) == num) {
              System.out.println("De los " + ficheros.size() + " vectores. El número " + num + " se encontró en el índice " + indice + " de su " + vector + "º vector.");
              encontrado = true;
            }
            indice++;
          }

          if (!encontrado)
            System.out.println("El número " + num + " no se encontró.");

        }
        vector++;
      }

    } catch (NumberFormatException e) {
      System.out.println("Error. Se esperaba un número.");
    } catch (IOException e) {
      e.printStackTrace();
    }

    sc.close();

  }

}
