import java.util.Scanner;

public class Cuenta {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        String cadena = sc.nextLine();
        sc.close();

        int contar = 0;
    
        for(int i = 0; i < cadena.length(); i++){

            if(String.valueOf(cadena.charAt(i)).equalsIgnoreCase(args[0])){
                
                contar++;

            }

        }

        System.out.println("Se han encontrado " + contar + " " + args[0]);

    }
}
