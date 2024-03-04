import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloServidorChat extends Thread {
  private DataInputStream fentrada;
  private Socket socket = null;
  private ComunHilos comun;

  public HiloServidorChat(Socket s, ComunHilos comun) {
    this.socket = s;
    this.comun = comun;

    try {
      // CREO FLUJO DE entrada para leer los mensajes
      fentrada = new DataInputStream(socket.getInputStream());
    } catch (IOException e) {
      System.out.println("ERROR DE E/S");
      e.printStackTrace();
    }

  }// ..

  public void run() {
    System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getActuales());

    // NADA MAS CONECTARSE LE ENVIO TODOS LOS MENSAJES
    String texto = comun.getMensajes();
    EnviarMensajesaTodos(texto);

    while (true) {
      String cadena = "";
      try {
        cadena = fentrada.readUTF();
        if (cadena.trim().equals("*")) {// EL CLIENTE SE DESCONECTA
          comun.setActuales(comun.getActuales() - 1);
          System.out.println("NUMERO DE CONEXIONES Actuales: " + comun.getActuales());
          break;
        }
        comun.setMensajes(comun.getMensajes() + cadena + "\n");
        EnviarMensajesaTodos(comun.getMensajes());
      } catch (Exception e) {
        e.printStackTrace();
        break;
      }
    } // fin while

    // se cierra el socket del cliente
    try {
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }// run

  // ENVIA LOS MENSAJES DEL CHAT A LOS CLIENTES
  private void EnviarMensajesaTodos(String texto) {
    int i;
    // recorremos tabla de sockets para enviarles los mensajes
    for (i = 0; i < comun.getConexiones(); i++) {
      Socket s1 = comun.getElementoTabla(i);
      if (!s1.isClosed()) {
        try {
          DataOutputStream fsalida = new DataOutputStream(s1.getOutputStream());
          fsalida.writeUTF(texto);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } // for

  }// EnviarMensajesaTodos

}// ..HiloServidorChat