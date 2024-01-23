import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ClienteMC {

  public static void main(String args[]) throws Exception {

    //Se crea el socket multicast
    int Puerto = 12345;//Puerto multicast

    MulticastSocket ms = new MulticastSocket(Puerto);
    InetAddress grupo = InetAddress.getByName("225.0.0.79");//Grupo

    //Nos unimos al grupo
    ms.joinGroup(grupo);
    String msg = "";

    while (!msg.trim().equals("*")) {

      // Recibe el paquete del servidor multicast
      // Genero dentro el buffer para que se sobreescriba 
      // al enviar un nuevo mensaje
      byte[] buf = new byte[1000];
      DatagramPacket paquete = new DatagramPacket(buf, buf.length);
      ms.receive(paquete);
      msg = new String(paquete.getData());
      System.out.println("Recibo: " + msg.trim());

    } //Fin de while

    ms.leaveGroup(grupo); //abandonamos grupo

    //cierra socket
    ms.close();
    System.out.println("Socket cerrado...");

  }//Fin de main

}//Fin de ClienteMC
