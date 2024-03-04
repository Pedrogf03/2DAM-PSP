import java.io.*;
import java.net.*;

public class ClienteUDP2 {
  public static void main(String args[]) throws Exception {
    // FLUJO PARA ENTRADA ESTANDAR
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    DatagramSocket clientSocket = new DatagramSocket();//socket cliente   
    byte[] enviados = new byte[1024];
    byte[] recibidos = new byte[1024];

    // DATOS DEL SERVIDOR al que enviar mensaje
    InetAddress IPServidor = InetAddress.getLocalHost();// localhost
    int puerto = 9876; // puerto por el que escucha

    // INTRODUCIR DATOS POR TECLADO
    System.out.print("Introduce mensaje: ");
    String cadena = in.readLine();
    enviados = cadena.getBytes();

    // ENVIANDO DATAGRAMA AL SERVIDOR
    System.out.println("Enviando " + enviados.length + " bytes al servidor.");
    DatagramPacket envio = new DatagramPacket(enviados, enviados.length, IPServidor, puerto);
    clientSocket.send(envio);

    // RECIBIENDO DATAGRAMA DEL SERVIDOR
    DatagramPacket recibo = new DatagramPacket(recibidos, recibidos.length);
    System.out.println("Esperando datagrama....");
    clientSocket.receive(recibo);
    String mayuscula = new String(recibo.getData());

    // OBTENIDENDO INFORMACIÓN DEL DATAGRAMA
    InetAddress IPOrigen = recibo.getAddress();
    int puertoOrigen = recibo.getPort();
    System.out.println("\tProcedente de: " + IPOrigen + ":" + puertoOrigen);
    System.out.println("\tDatos: " + mayuscula.trim());

    //cerrar socket
    clientSocket.close();
  }//Fin de main   
}//Fin de ClienteUDP2
