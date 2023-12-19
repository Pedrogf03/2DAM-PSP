import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class BusquedaConcurrente {

  private Map<Integer, ArrayList<File>> numeros; // Este mapa tiene como key el numero y como valor la lista de archivos en los que buscar ese numero.

  public static void main(String[] args) throws Exception {

    try {
      BusquedaConcurrente bc = new BusquedaConcurrente(args); // En el constructor se valida la linea de comandos.
      bc.ejecutarProcesos(bc.crearProcesos()); // Se crean los procesos y se devuelve un mapa que tiene de key el proceso y de valor el numero que busca; y se ejecutan dichos procesos.
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

  // Se crean los procesos.
  private Map<Process, Integer> crearProcesos() throws IOException {

    Map<Process, Integer> procesos = new LinkedHashMap<>(); // Mapa que guarda como clave el proceso y como valor el numero que busca.

    // Se van creando los procesos y guardando en el mapa.
    for (Map.Entry<Integer, ArrayList<File>> entry : numeros.entrySet()) {
      ProcessBuilder pb = new ProcessBuilder("java", "Busqueda", entry.getKey() + "");
      Process p = pb.start();

      procesos.put(p, entry.getKey()); // Se guarda el proceso y el número que busca.

    }

    return procesos;

  }

  // Funcion que ejecuta los procesos
  private void ejecutarProcesos(Map<Process, Integer> procesos) throws IOException {

    // Se recorre el mapa de procesos.
    for (Map.Entry<Process, Integer> entry : procesos.entrySet()) {

      // Se guarda el proceso y el numero.
      Process p = entry.getKey();
      int num = entry.getValue();

      // Se abren canales de escritura y de lectura.
      try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream())); BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {

        // Lista donde están todos los ficheros en los que buscar el numero.
        ArrayList<File> archivos = numeros.get(num);

        for (File f : archivos) {

          // Por cada fichero, se le manda al subproceso la ruta absoluta del mismo.
          writer.write(f.getAbsolutePath());
          writer.newLine();
          writer.flush();

        }

        // Cerrar el bucle del proceso hijo.
        writer.write("FIN");
        writer.newLine();
        writer.flush();

        // Se van leyendo todas las lineas que el hijo devolvió.
        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }

        System.out.println();

      }

    }

  }
}
