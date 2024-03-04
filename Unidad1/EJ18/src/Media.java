public class Media {
    public static void main(String[] args) {
    
        float suma = 0;

        for (String num : args) {
            suma += Float.parseFloat(num);
        }

        float div = args.length;

        float media = suma / div;

        System.out.println("Media: " + media);

    }
}
