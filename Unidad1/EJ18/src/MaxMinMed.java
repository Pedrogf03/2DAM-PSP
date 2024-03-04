import java.io.File;
import java.io.IOException;

public class MaxMinMed {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 0) {
            System.out.println("Debe proporcionar al menos un número como argumento.");
            System.exit(1);
        }

        iniciarProceso("Maximo", args);
        iniciarProceso("Minimo", args);
        iniciarProceso("Media", args);

    }

    public static void iniciarProceso(String proceso, String[] args){

        // Crear un nuevo array, añadiendo al inicio "java" y el nombre del proceso.
        String[] newArgs = new String[args.length + 2];
        newArgs[0] = "java";
        newArgs[1] = proceso; // Nombre del proceso a ejecutar.
        System.arraycopy(args, 0, newArgs, 2, args.length); // Se copian los datos del array args a partir de la posición 0 al nuevo array a partir de la posición 2.

        // Crear un proceso para ejecutar el programa.
        ProcessBuilder pb = new ProcessBuilder(newArgs);

        // Redirigir la salida estándar del proceso hacia un archivo.
        File f = new File(proceso + ".txt");
        pb.redirectOutput(f);

        try {
            // Iniciar el proceso.
            pb.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
