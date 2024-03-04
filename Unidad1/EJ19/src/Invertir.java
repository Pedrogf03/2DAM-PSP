import java.util.Scanner;

public class Invertir {
    public static void main(String[] args) throws Exception {
            
        Scanner sc = new Scanner(System.in);

        String cadena = sc.nextLine();

        sc.close();

        String invertido = "";

        for(int i = cadena.length() - 1; i >= 0; i--){

            invertido += cadena.charAt(i);

        }

        System.out.println(invertido);
        
    }
}
