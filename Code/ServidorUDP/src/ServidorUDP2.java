import java.net.*;

public class ServidorUDP2 {
  public static void main(String args[]) throws Exception {

    //Puerto por el que escucha el servidor: 9876
    DatagramSocket serverSocket = new DatagramSocket(9876);
    byte[] recibidos = new byte[1024];
    byte[] enviados = new byte[1024];
    String cadena;

    while (true) {
      System.out.println("Esperando datagrama.....");

      //RECIBO DATAGRAMA
      recibidos = new byte[1024];
      DatagramPacket paqRecibido = new DatagramPacket(recibidos, recibidos.length);
      serverSocket.receive(paqRecibido);
      cadena = new String(paqRecibido.getData());

      //DIRECCION ORIGEN
      InetAddress IPOrigen = paqRecibido.getAddress();
      int puerto = paqRecibido.getPort();
      System.out.println("\tOrigen: " + IPOrigen + ":" + puerto);
      System.out.println("\tMensaje recibido: " + cadena.trim());

      //CONVERTIR CADENA A MAYÚSCULA
      String mayuscula = cadena.trim().toUpperCase();
      enviados = mayuscula.getBytes();

      //ENVIO DATAGRAMA AL CLIENTE
      DatagramPacket paqEnviado = new DatagramPacket(enviados, enviados.length, IPOrigen, puerto);
      serverSocket.send(paqEnviado);

      //Para terminar
      if (cadena.trim().equals("*"))
        break;

    } //Fin de while   
    serverSocket.close();
    System.out.println("Socket cerrado...");
  }//Fin de main   
}//Fin de ServidorUDP2
