import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;
import java.io.IOException;

public class ClienteUDP {

  public static void main(String[] argv) {
    DatagramSocket socket = null;
    try {
      //InetAddress destino = InetAddress.getLocalHost();
      InetAddress destino = InetAddress.getByName("127.0.0.1");
      int port = 12345; //puerto al que env�o el datagrama
      byte[] mensaje = new byte[1024];

      String Saludo = "Nos vemos el jueves en la recuperacion !!";
      mensaje = Saludo.getBytes(); //codifico String a bytes

      //CONSTRUYO EL DATAGRAMA A ENVIAR
      DatagramPacket envio = new DatagramPacket(mensaje, mensaje.length, destino, port);
      socket = new DatagramSocket(34567);//Puerto local
      System.out.println("Enviando Datagrama de longitud: " + mensaje.length);
      System.out.println("Host destino                  : " + destino.getHostName());
      System.out.println("IP Destino                    : " + destino.getHostAddress());
      System.out.println("Puerto local del socket       : " + socket.getLocalPort());
      System.out.println("Puerto al que envio           : " + envio.getPort());

      //ENVIO DATAGRAMA
      socket.send(envio);

    } catch (UnknownHostException e1) {
    } catch (SocketException e2) {
    } catch (IOException e3) {
    } finally {
      socket.close(); //cierro el socket
    }

  }//Fin de main

}//Fin de ClienteUDP

//192.168.14.125
