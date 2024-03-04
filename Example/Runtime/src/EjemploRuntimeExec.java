import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;


public class EjemploRuntimeExec {

  public static void main(String args[]) {

    //Windows: 
	//String commandLine="cmd /c dir";
	String [] commandLine= {"cmd",  "/c", "dir"};
	
    //Linux (comando 'ls' SIN argumentos):   String commandLine= "bash -c  ls";
	//Linux (comando 'ls' CON argumentos):
	//String [] commandLine={"bash",  "-c", "ls /"};
	//String [] commandLine={"bash",  "-c", "java HolaMundo"};

	Process p=null;
    try {
        p = Runtime.getRuntime().exec(commandLine);
    } catch (IOException e) {
        e.printStackTrace();
    }

    try (BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
		 BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()))){
			String line;

			while ((line = bri.readLine()) != null) 
				System.out.println(line);
			
			while ((line = bre.readLine()) != null) 
				System.out.println(line);

			p.waitFor();

			System.out.println("Done.");
		  
    } catch (IOException | InterruptedException err) {
		err.printStackTrace();
    }
  }
}

