import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;

public class HelloSubprocess {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    System.out.println("Indique el nombre del programa a ejecutar:");
    String program = sc.nextLine();

    sc.close();

    ProcessBuilder pb = new ProcessBuilder("java", program);
    Map<String,String> env = pb.environment();

    env.put("CLASSPATH", "H:\\Mi unidad\\Classroom\\PSP 23-24\\Ejercicios\\EJ17\\exe");

    try {

      Process hello = pb.start();

      try (
        BufferedReader reader = new BufferedReader(new InputStreamReader(hello.getInputStream()));
      ) {
        System.out.println(reader.readLine());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  
}