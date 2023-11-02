import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//Recibe datos de la salida del subproceso
public class RecibeDelSubproceso {

  public static void main(String args[]){
	
		if (args.length != 1){
			System.err.println("Se necesita un programa para ejecutar");
			System.exit(0);
		} 
		BufferedReader br = null;
	
		try {	
			Process process = new ProcessBuilder("java", args[0]).start();
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
			String line;
		
			System.out.println("Salida del proceso: " + args[0]);
		
			while ((line=br.readLine())!=null)
				System.out.println(line);
			
		} catch (IOException e){
			e.printStackTrace();
		
		} finally {
			try{
				if (br != null) br.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
  }
}
