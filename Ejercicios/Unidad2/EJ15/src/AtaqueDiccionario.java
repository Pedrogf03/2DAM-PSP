import java.util.ArrayList;
import java.util.Scanner;

public class AtaqueDiccionario {

  public static void main(String[] args) throws Exception {

    String[] palabras = { "cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve" };

    Scanner sc = new Scanner(System.in);

    System.out.print("Contraseña: ");
    String passwd = sc.nextLine();

    System.out.print("Nº de tareas: ");
    int tareas = Integer.parseInt(sc.nextLine());

    int palabrasPorTarea = palabras.length / tareas;

    ArrayList<BuscaPasswd> hilos = new ArrayList<>();

    for (int i = 0; i < tareas; i++) {

      int fin = (i + 1) * palabrasPorTarea;
      if (i == tareas - 1) {
        fin = palabras.length;
      }

      String[] palabrasHilo = new String[fin - (i * palabrasPorTarea)];
      for (int inicio = 0; inicio < (fin - (i * palabrasPorTarea)); inicio++) {
        palabrasHilo[inicio] = palabras[i * palabrasPorTarea + inicio];
      }

      hilos.add(new BuscaPasswd(palabrasHilo, passwd, "Hilo " + i, hilos));

    }

    for (Thread h : hilos) {
      h.start();
    }

    // while (true) {

    //   System.out.println("¿Cuanto tiempo quiere seguir esperando (ms)?");
    //   int time = Integer.parseInt(sc.nextLine());

    //   for (BuscaPasswd h : hilos) {
    //     if (time == 0) {
    //       h.interrupt();
    //     } else {
    //       h.join(time);
    //     }
    //   }

    //   boolean end = true;
    //   for (BuscaPasswd h : hilos) {
    //     if (h.isAlive()) {
    //       end = false;
    //     }
    //   }

    //   if (time == 0 || end) {
    //     break;
    //   }

    // }

    for (BuscaPasswd h : hilos) {
      if (!h.found) {
        if (h.interrumpido) {
          System.out.println(h.getName() + " interrumpido.");
        } else {
          System.out.println(h.getName() + " no ha encontrado la contraseña.");
        }
      } else {
        System.out.println(h.getName() + " ha encontrado la contraeña: " + passwd);
      }
    }

    sc.close();

  }

}
