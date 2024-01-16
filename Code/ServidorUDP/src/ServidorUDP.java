import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.io.IOException;

public class ServidorUDP {

  public static void main(String[] argv) {

    byte[] bufer = new byte[1024];//bufer para recibir el datagrama

    DatagramSocket socket = null;
    //ASOCIO EL SOCKET AL PUERTO 12345
    try {
      socket = new DatagramSocket(12345);

      //ESPERANDO DATAGRAMA
      System.out.println("Esperando Datagrama ................");
      DatagramPacket recibo = new DatagramPacket(bufer, bufer.length);
      socket.receive(recibo);//recibo datagrama
      int bytesRec = recibo.getLength();//obtengo numero de bytes   
      String paquete = new String(recibo.getData());//obtengo String

      //VISUALIZO INFORMACI�N
      System.out.println("Número de Bytes recibidos : " + bytesRec);
      System.out.println("Contenido del Paquete     : " + paquete.trim());
      System.out.println("Puerto origen del mensaje : " + recibo.getPort());
      System.out.println("IP de origen              : " + recibo.getAddress().getHostAddress());
      System.out.println("Puerto destino del mensaje:" + socket.getLocalPort());
    } catch (SocketException es) {
      es.printStackTrace();
    } catch (IOException ei) {
      ei.printStackTrace();
    } finally {
      socket.close(); //cierro el socket
    }

  }//Fin de main

}// Fin de SerivdorUDP
