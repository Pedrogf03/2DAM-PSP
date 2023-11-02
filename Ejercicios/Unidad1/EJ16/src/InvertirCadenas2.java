import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class InvertirCadenas2 {

    public static void main(String[] args) throws Exception {

        System.out.println("Introduce tantas cadenas como quieras, para empezar a procesarlas escribe \"FIN\"");
        Scanner sc = new Scanner(System.in);
        String s = "";

        File f = new File("cadena.txt");

        while(true) {

            s = sc.nextLine();

            if(s.equals("FIN")) {
                break;
            }

            try {

                ProcessBuilder pb = new ProcessBuilder("java", "InvertirCadena");
                pb.redirectOutput(ProcessBuilder.Redirect.appendTo(f));
                Process invertirCadena = pb.start();

                try(
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(invertirCadena.getOutputStream()));
                ) {

                    writer.write(s);
                    writer.flush();

                }

            } catch (Exception e){
                e.printStackTrace();
            }

        }

        sc.close();

    }

}