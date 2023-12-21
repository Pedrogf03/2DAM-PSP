import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class BusquedaConcurrente {

  private Map<Integer, ArrayList<File>> numeros;

  public static void main(String[] args) throws Exception {

    try {
      BusquedaConcurrente bc = new BusquedaConcurrente(args);
      bc.leerRespuesta(bc.ejecutarProcesos());
    } catch (BusquedaConcurrenteException e) {
      e.printMsg();
    }

  }

  public BusquedaConcurrente(String[] args) throws BusquedaConcurrenteException {
    this.numeros = procesarLineaComandos(args); // Se procesa la linea de comandos.
  }

  private Map<Integer, ArrayList<File>> procesarLineaComandos(String[] args) throws BusquedaConcurrenteException {

    Map<Integer, ArrayList<File>> params = new LinkedHashMap<>(); // Guardo en un mapa como clave el número y como valor una lista de todos los ficheros que deben buscar ese numero.

    if (args.length < 3) {
      throw new BusquedaConcurrenteException("Error. El número de parámetros no es el esperado.");
    }

    if (!args[0].equals("-n")) {
      throw new BusquedaConcurrenteException("Error. Se esperaba '-n' al inicio.");
    }

    try {
      Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      throw new BusquedaConcurrenteException("Error. Se esperaba un número en el segundo argumento.");
    }

    params.put(Integer.parseInt(args[1]), new ArrayList<>());
    int num = Integer.parseInt(args[1]);

    // Se van guardando en el mapa los valores correspondientes (numero a procesar, List<ficheros donde buscar>)
    for (int i = 2; i < args.length; i++) {

      switch (args[i]) {
      case "-n":
        try {
          num = Integer.parseInt(args[i + 1]);
          params.put(num, new ArrayList<>());
        } catch (NumberFormatException e) {
          throw new BusquedaConcurrenteException("Error. Se esperaba un número tras un '-n'.");
        } catch (IndexOutOfBoundsException e) {
          throw new BusquedaConcurrenteException("Error. Se esperaba un número tras un '-n'.");
        }
        break;
      default:
        try {
          Integer.parseInt(args[i]);
          if (args[i + 1].equals("-n")) {
            throw new BusquedaConcurrenteException("Error. Se esperaba una lista de archivos detrás del número: " + Integer.parseInt(args[i]));
          }
        } catch (NumberFormatException e) {
          File f = new File(args[i]);
          if (!f.exists()) {
            throw new BusquedaConcurrenteException("Error. El archivo especificado no existe: " + f.getName());
          } else {
            params.get(num).add(f);
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          throw new BusquedaConcurrenteException("Error. Se esperaba una lista de archivos detrás del número: " + Integer.parseInt(args[i]));
        }

        break;
      }

    }

    // Si todo ha ido bien, se devuelve el mapa.
    return params;

  }

  private Map<Integer, ArrayList<BufferedReader>> ejecutarProcesos() throws IOException {

    Map<Integer, ArrayList<BufferedReader>> procesos = new LinkedHashMap<>(); // Mapa que guarda como clave el proceso y como valor el numero que busca.

    for (Map.Entry<Integer, ArrayList<File>> entry : numeros.entrySet()) {

      for (File f : entry.getValue()) {

        ProcessBuilder pb = new ProcessBuilder("java", "Busqueda", entry.getKey() + "");
        pb.redirectInput(f);

        Process p = pb.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

        if (!procesos.containsKey(entry.getKey())) {
          procesos.put(entry.getKey(), new ArrayList<>());
        }

        procesos.get(entry.getKey()).add(br);

      }

    }

    return procesos;

  }

  private void leerRespuesta(Map<Integer, ArrayList<BufferedReader>> procesos) throws IOException {

    for (Map.Entry<Integer, ArrayList<BufferedReader>> entry : procesos.entrySet()) {

      int vector = 1;
      boolean encontrado = false;

      for (BufferedReader br : entry.getValue()) {

        try {
          String line;
          while ((line = br.readLine()) != null) {
            if (!line.equals("false")) {
              System.out.println("De los " + entry.getValue().size() + " vectores. " + line + " del " + vector + "º vector");
              encontrado = true;
            }
          }

          vector++;
        } finally {
          br.close();
        }

      }

      if (!encontrado) {
        System.out.println("El número 5 no se encontró");
      }

      System.out.println();

    }

  }

}
