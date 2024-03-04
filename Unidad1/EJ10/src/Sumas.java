import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Sumas {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    
    System.out.println("Introduce un número");
    int num1 = sc.nextInt();

    System.out.println("Introduce otro número");
    int num2 = sc.nextInt();

    sc.close();

    try {
      // Proceso
      ProcessBuilder pb = new ProcessBuilder("java", "Suma");

      // Se inicia el proceso.
      Process suma = pb.start();

      try (
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(suma.getOutputStream())); // Flujo de entrada del hijo
      ) {
        
        writer.write(num1 + "\n"); // Escribe
        writer.flush(); // Se asegura que se envíen los datos

        writer.write(num2 + "\n"); // Escribe otra vez
        writer.flush(); // Se asegura que se vuelvan a enviar los datos

      }

      try (
        BufferedReader reader = new BufferedReader(new InputStreamReader(suma.getInputStream())); // Flujo de salida del hijo
      ) {
        
        suma.waitFor(); // Se espera a que se ejecute el proceso
        System.out.println(reader.readLine()); // Se muestra por pantalla el resultado del proceso hijo.

      }

    } catch (Exception e){
      e.printStackTrace();
    }

  }

  
}