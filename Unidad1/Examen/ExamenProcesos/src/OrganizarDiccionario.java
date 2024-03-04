import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class OrganizarDiccionario {

  public static void main(String[] args) throws Exception {
    // Se crea un objeto OrganizarDiccionario para ejecutar sus métodos.
    OrganizarDiccionario od = new OrganizarDiccionario();

    try {
      // Se verifica la linea de comandos y guarda los parametros en un mapa.
      Map<String, String> params = od.verificarLineaComandos(args);
      // A partir del mapa, se crean los ProcessBuilder y se almacenan en un ArrayList.
      ArrayList<ProcessBuilder> procesos = od.getProcesos(params);
      // Con el ArrayList creado en el paso anterior, se ejecutan los procesos.
      od.ejecutarProcesos(procesos);
    } catch (LineaComandosException e) { // Si ocurre una excepción en la linea de comandos, se captura y se imprime un mensaje.
      System.out.println("Ha ocurrido un error en la línea de comandos:");
      e.printMsg();
    } catch (IOException e) { // Si ocurre una excepción de entrada/salida, se captura y se imprime un mensaje.
      System.out.println("Ha ocurrido un error de entrada/salida.");
    } catch (InterruptedException e) { // Si ocurre una excepción de interrupción, se captura y se imprime un mensaje.
      System.out.println("Se ha interrumpido la ejecución de un subproceso.");
    } catch (Exception e) { // Si ocurre cualquier otra excepción, se captura y se imprime un mensaje.
      e.printStackTrace();
    }
  }

  // Método que recibe la linea de comandos y verifica si es correcto su formato.
  public Map<String, String> verificarLineaComandos(String[] args)
    throws LineaComandosException {
    if (args.length >= 4 && args.length <= 6) { // 4 es el número minimo de argumentos que necesita el programa, y 6 es el máximo.
      // Mapa que va a almacenar los parametros necesarios para la ejecución del programa.
      Map<String, String> params = new TreeMap<>();

      // Bucle para recoger todos los parámetros necesarios.
      for (int i = 0; i < args.length; i++) {
        // Se van guardando los valores en el mapa para usarlos posteriormente.
        switch (args[i]) {
          case "-cp":
            params.put("CLASSPATH", args[i + 1]); // Variable de entorno CLASSPATH.
            break;
          case "-if":
            params.put("INPUTF", args[i + 1]); // Ruta del archivo de texto de entrada del programa.
            break;
          case "-wd":
            params.put("WORKINGD", args[i + 1]); // Directorio de trabajo donde los subprocesos generan la salida (Opcional).
            break;
          default:
            break;
        }
      }

      if (
        !params.containsKey("CLASSPATH") ||
        !params.containsKey("INPUTF") ||
        params.size() > 3
      ) {
        // Si hay más de 3 o falta un argumento obligatorio, salta una excepción de linea de comandos.
        throw new LineaComandosException(
          "No se han introducido los parámetros esperados."
        );
      } else {
        return params;
      }
    } else {
      // Si no más de 4 pero menos de 6 argumentos, salta una excepción de linea de comandos.
      throw new LineaComandosException(
        "El número de parámetros no es el esperado."
      );
    }
  }

  // Método que recibe el mapa generado en el método anterior y crea un ArrayList de ProcessBuilders
  public ArrayList<ProcessBuilder> getProcesos(Map<String, String> params) {
    ArrayList<ProcessBuilder> procesos = new ArrayList<>(); // ArrayList donde se almacenarán los ProcessBuilders.
    ArrayList<String> definiciones = new ArrayList<>(); // ArrayList donde se almacenarán las definiciones del diccionario desordenado.
    ArrayList<String> palabras = new ArrayList<>(); // ArrayList donde se almacenará cada palabra sin su definición.

    try (
      BufferedReader reader = new BufferedReader(
        new FileReader(new File(params.get("INPUTF")))
      ); // Flujo para leer los datos del archivo de texto de entrada.
    ) {
      // Se lee linea a linea el fichero de texto de entrada.
      String line;
      while ((line = reader.readLine()) != null) {
        // Por cada definición, se separa la cadena por los espacios y se guardan las palabras en un array.
        String[] def = line.split(" ");

        // Se comprueba en el ArrayList de palabras que NO exista ya el primer valor de la cadena del fichero de entrada,
        // es decir, la palabra que se va a definir.
        if (!palabras.contains(def[0])) {
          // Si no existe, se añade al ArrayList.
          palabras.add(def[0]);
          // Y se añade al ArrayList de definiciones la linea completa obtenida.
          definiciones.add(line);
        }
      }

      // Una vez están todas las definiciones en el ArrayList, se ordenan alfabéticamente.
      Collections.sort(definiciones);

      // Y, por cada definición, se crea un ProcessBuilder.
      for (String s : definiciones) {
        // El ProcessBuilder ejecuta el progama "GenerarArchivo", que recibe una única definición.
        ProcessBuilder pb = new ProcessBuilder("java", "GenerarArchivo", s);

        // Se le especifica al ProcessBuilder su variable de entorno CLASSPATH, empleando el mapa de parámetros.
        pb.environment().put("CLASSPATH", params.get("CLASSPATH"));

        File output; // Este archivo será donde se le redirija la salida a cada ProcessBuilder.

        // Si en el mapa existe el directorio de trabajo, se le especifica al ProcessBuilder.
        if (params.get("WORKINGD") != null) {
          pb.directory(new File(params.get("WORKINGD")));
          // En este caso, output almacenará la ruta del directorio de trabajo + el nombre del archivo donde debe escribir el resultado.
          output =
            new File(params.get("WORKINGD") + "/" + s.charAt(0) + ".txt");
        } else {
          // Si no hay directorio de trabajo, output almacenará solamente el nombre del archivo donde debe escribir el resultado.
          output = new File(s.charAt(0) + ".txt");
        }

        // Se le redirecciona la salida al proceso en modo append, al fichero output.
        pb.redirectOutput(Redirect.appendTo(output));

        // Se añade el ProcessBuilder al ArrayList.
        procesos.add(pb);
      }
    } catch (Exception e) { // Si ocurre una excepción, se captura y se muestra por pantalla.
      e.printStackTrace();
    }

    // Se devuelve el array de procesos.
    return procesos;
  }

  // Método que recibe el ArrayList de procesos y los ejecuta uno a uno.
  public void ejecutarProcesos(ArrayList<ProcessBuilder> procesos)
    throws InterruptedException, IOException {
    for (ProcessBuilder pb : procesos) {
      // Se ejecuta cada proceso, esperando a que acabe su ejecución para proceder con el siguiente.
      pb.start().waitFor();
    }
  }
}
