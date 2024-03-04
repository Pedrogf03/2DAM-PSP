import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorChat {
  static final int MAXIMO = 5;//MAXIMO DE Conexiones PERMITIDAS	

  public static void main(String args[]) throws IOException {
    int PUERTO = 44444;

    try (ServerSocket servidor = new ServerSocket(PUERTO);) {

      System.out.println("Servidor iniciado...");

      Socket tabla[] = new Socket[MAXIMO];//para controlar las conexiones	
      ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla);

      while (comun.getConexiones() < MAXIMO) {
        Socket socket = new Socket();
        socket = servidor.accept();// esperando cliente

        comun.addTabla(socket, comun.getConexiones());
        comun.setActuales(comun.getActuales() + 1);
        comun.setConexiones(comun.getConexiones() + 1);

        HiloServidorChat hilo = new HiloServidorChat(socket, comun);
        hilo.start();
      }
    }
  }//main
}//ServidorChat..  
