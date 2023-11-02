import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class RecuentoAparicionesV2 {

    public static void main(String[] args) {

        RecuentoAparicionesV2 contar = new RecuentoAparicionesV2();

        try {
            
            // Verificar la linea de comandos y guardar los parametros en un mapa.
            Map<String, String> params = contar.checkCommandLine(args);
            // Crear los ProcessBuilder y almacenarlos en un ArrayList.
            ArrayList<ProcessBuilder> procesos = contar.getProcesos(args, params); 
            // Ejecutar los procesos y almacenar el contenido en el fichero de salida esperado.
            contar.execProcesos(procesos, new File(params.get("SALIDA"))); 

        } catch (CommandLineException e) {
            System.out.println("Ha ocurrido un error en la línea de comandos:");
            e.printMsg();
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error de entrada/salida.");
        } catch (InterruptedException e) {
            System.out.println("Se ha interrumpido la ejecución de un subproceso.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Función que verifica que la linea de comandos introducida contiene lo esperado.
    public Map<String, String> checkCommandLine(String[] args) throws CommandLineException {

        if (args.length >= 10) { // 10 es el número mínimo de argumentos que necesita el programa.

            // Mapa que va a almacenar los parametros necesarios para la ejecución del programa.
            Map<String, String> params = new TreeMap<>();

            // Primer bucle para recoger todos los parámetros necesarios.
            for (int i = 0; i < args.length; i++) {

                switch (args[i]) {
                    case "-cp":
                        params.put("CLASSPATH", args[i + 1]);
                        break;
                    case "-fe":
                        params.put("ENTRADA", args[i + 1]);
                        break;
                    case "-r":
                        params.put("SALIDA", args[i + 1]);
                        break;
                    default:
                        break;
                }

            }

            if (params.size() != 3) {
                // Si hay menos de 3 parámetros, salta una excepción.
                throw new CommandLineException("El número de parámetros no es el esperado.");
            } else {
                return params;
            }

        } else {
            // Si hay menos de 10 argumentos, salta una excepción.
            throw new CommandLineException("El número de parámetros no es el esperado.");
        }

    }

    // Función que recorre de nuevo los argumentos y crea los ProcessBuilder, para devolverlos en un ArrayList.
    public ArrayList<ProcessBuilder> getProcesos(String[] args, Map<String, String> params) {

        // ArrayList donde se guardarán todos los procesos a ejecutar.
        ArrayList<ProcessBuilder> procesos = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-t")) {
                // Se crea un ProcessBuilder empleando los 3 siguientes argumentos.
                ProcessBuilder pb = new ProcessBuilder(args[i + 1], args[i + 2], args[i + 3]);

                // Se redirecciona la entrada al fichero especificado.
                pb.redirectInput(new File(params.get("ENTRADA")));

                // Se le indica el classpath.
                pb.environment().put("CLASSPATH", params.get("CLASSPATH"));

                if (args[i + 4].equals("-fs")) { // Si el hijo tiene definida otra salida.

                    // Se redirecciona la salida del subproceso al archivo especificado.
                    pb.redirectOutput(new File(args[i + 5]));

                } else {

                    // Si no tiene definida una salida propia, se le redirecciona la salida a la del padre.
                    pb.redirectOutput(Redirect.appendTo(new File(params.get("SALIDA"))));

                }

                // Se guarda en el ArrayList el proceso.
                procesos.add(pb);

            }

        }

        return procesos;

    }

    // Función que ejecuta los procesos y, si tienen una salida distinta a la
    // esperada, copian el resultado de su ejecución de un fichero al esperado.
    public void execProcesos(ArrayList<ProcessBuilder> procesos, File salida) throws IOException, InterruptedException {

        for (ProcessBuilder pb : procesos) { // Se recorre el array de procesos.

            // Se ejecuta un proceso y se espera a que acabe su ejeción.
            pb.start().waitFor();

            // Se obtiene el fichero de salida del proceso.
            File salidaHijo = pb.redirectOutput().file();

            if (!salidaHijo.getAbsolutePath().equals(salida.getAbsolutePath())) { // Si las rutas de ambos archivos no coinciden.

                try (
                        BufferedReader reader = new BufferedReader(new FileReader(salidaHijo));
                        FileWriter writer = new FileWriter(salida, true)) {

                    // Se copia en el fichero de salida esperado el contenido escrito en el fichero de salida del proceso.
                    writer.write(reader.readLine() + "\n");

                }

            }

        }

    }

}