import java.io.IOException;

public class RuntimeProcess {
  public static void main(String[] args) {
    if(args.length <= 0) {
      System.err.println("Se necesita un programa a ejecutar");
      return;
    }

    try {
      Runtime runtime = Runtime.getRuntime();
      Process process = runtime.exec(args);
      Thread.sleep(1000);
      process.destroy();
    } catch (IOException ex){
      System.err.println("Excepción de E/S!!");
    } catch (InterruptedException ex) {
      System.err.println("Excepción InterruptedExcepción");
    }

  }
}