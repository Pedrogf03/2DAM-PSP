import java.util.Scanner;

public class Mayusculas {
  
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String cadena = sc.nextLine();

        sc.close();

        System.out.println(cadena.toUpperCase());

    }

}
