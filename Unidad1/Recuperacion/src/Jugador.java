import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Jugador {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Random rd = new Random();
    int total = Integer.parseInt(args[0]);

    int resultado = 0;
    List<Integer> lista = new ArrayList<>();

    for (int i = 0; i < 4; i++) {
      String linea = sc.nextLine();
      int numero = Integer.parseInt(linea);
      lista.add(numero);
    }
    while (true) {
      for (int i = 0; i < lista.size(); i++) {
        int opcion = rd.nextInt(4) + 1;
        switch (opcion) {
          case 1:
            resultado += lista.get(i);
            break;
          case 2:
            resultado -= lista.get(i);
            break;
          case 3:
            resultado *= lista.get(i);
            break;
          case 4:
            if (resultado != 0)
              resultado /= lista.get(i);
            else
              resultado += lista.get(i);
            break;
        }
        if (resultado > total) {
          break;
        }
      }
      if (resultado <= total) {
        break;
      }
    }
    System.out.println(resultado);
    sc.close();
  }
}
