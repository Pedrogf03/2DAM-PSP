import java.util.Scanner;

public class Suma {
    public static void main(String[] args) throws Exception {
        
        Scanner sc = new Scanner(System.in);

        int num1 = sc.nextInt();
        int num2 = sc.nextInt();

        sc.close();

        System.out.println(num1 + num2);

    }
}
