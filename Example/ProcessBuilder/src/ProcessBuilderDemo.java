import java.io.File;

public class ProcessBuilderDemo {

    public static void main(String[] args) throws Exception {
        
        // Create a new list of arguments for our process.
        String[] list = {"notepad", "archivo1.txt"};

        // Create the process builder.
        ProcessBuilder pb = new ProcessBuilder(list);

        // Ser the working directory of the process.
        pb.directory(new File("C:\\"));

        pb.directory(new File(System.getProperty("user.dir")));
        //System.out.println("" + pb.directory());

        try {
            Process p = pb.start();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
