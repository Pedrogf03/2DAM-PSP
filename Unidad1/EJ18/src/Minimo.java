public class Minimo {
    public static void main(String[] args) {

        int min = Integer.parseInt(args[0]);

        for (String string : args) {

            int num = Integer.parseInt(string);

            if(num < min){
                min = Integer.parseInt(string);
            }
            
        }

        System.out.println("Min: " + min);

    }
}
