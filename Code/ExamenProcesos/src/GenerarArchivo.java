public class GenerarArchivo {

  public static void main(String[] args) {
    
    // Se crea una cadena separada por espacios juntando cada argumento recibido por linea de comandos.
    String def = String.join(" ", args);

    // Se muestra por pantalla la cadena.
    System.out.println(def);

  }
  
}
