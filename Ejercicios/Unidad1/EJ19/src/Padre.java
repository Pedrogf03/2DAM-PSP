import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Padre {

    public static void main(String[] args) {

        File in = new File("H:\\Mi unidad\\Classroom\\PSP 23-24\\Ejercicios\\EJ19\\Archivo1.txt"); // Archivo origen de la cadena.
        File out = new File("H:\\Mi unidad\\Classroom\\PSP 23-24\\Ejercicios\\EJ19\\Archivo2.txt"); // Archivo destino de la cadena.

        // Proceso para invertir la cadena.
        ProcessBuilder pbInv = new ProcessBuilder("java", "Invertir");
        pbInv.redirectInput(in); // Se redirige la entrada estandar.
            
        // Proceso para poner en mayusculas la cadena.
        ProcessBuilder pbMayus = new ProcessBuilder("java", "Mayusculas");
        pbMayus.redirectOutput(out); // Se redirige la salida estandar.

        try {

            // Se inician ambos procesos.
            Process invertir = pbInv.start();
            Process mayus = pbMayus.start();

            try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(invertir.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(mayus.getOutputStream()));
            ){
                writer.write(reader.readLine()); // Lo recibido del proceso invertir se envia al proceso mayusculas.
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
