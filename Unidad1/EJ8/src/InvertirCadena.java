import java.util.Scanner;

public class InvertirCadena {
    public static void main(String[] args) {
    
        Scanner sc = new Scanner(System.in);
        String cadena = sc.nextLine();
        sc.close();

        String inv = "";
        for(int i = cadena.length() - 1; i >= 0; i--){
            inv += cadena.charAt(i);
        }

        System.out.println(inv);
        
    }
}
