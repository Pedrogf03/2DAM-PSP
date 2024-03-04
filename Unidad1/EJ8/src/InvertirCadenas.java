import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class InvertirCadenas {
  
  public static void main(String[] args) {
    
    System.out.println("Introduce tantas cadenas como quieras, para empezar a procesarlas escribe \"FIN\"");

    Scanner sc = new Scanner(System.in);

    String s = "";

    while(true) {

      s = sc.nextLine();

      if(s.equals("FIN")) {
        break;
      }

      try {

        // Ejecutar el proceso hijo InvertirCadena.
        Process invertirCadena = Runtime.getRuntime().exec("java InvertirCadena");

        // Flujo de entrada de datos del proceso hijo.
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(invertirCadena.getOutputStream()));) {
          writer.write(s); // Envíar la variable s al proceso hijo.
          writer.flush(); // Asegurar que se envíen.
        }

        // Flujo de salida del proceso hijo.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(invertirCadena.getInputStream()))) {
          invertirCadena.waitFor(); // Esperar a que el proceso hijo acabe con su ejecución.
          System.out.println(reader.readLine()); // Mostrar por pantalla los datos recibidos del proceso hijo.
        }


      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }

    }

    sc.close();

  }

}
