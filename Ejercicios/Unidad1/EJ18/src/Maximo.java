public class Maximo {
    public static void main(String[] args) {

        int max = Integer.parseInt(args[0]);

        for (String string : args) {

            int num = Integer.parseInt(string);

            if(num > max){
                max = Integer.parseInt(string);
            }
            
        }

        System.out.println("Max: " + max);

    }
}
