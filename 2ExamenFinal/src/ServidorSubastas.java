import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class ServidorSubastas implements Runnable {

  private boolean stop;
  private int puerto;
  private ServerSocket socketServidor;
  private Set<Subasta> subastasDelDia;

  public ServidorSubastas() throws IOException {
    subastasDelDia = new HashSet<>();
    Properties conf = new Properties();
    conf.load(new FileInputStream("server.properties"));
    puerto = Integer.parseInt(conf.getProperty("PUERTO"));
  }

  public static void main(String[] args) {

    try {
      ServidorSubastas ss = new ServidorSubastas();
      Thread server = new Thread(ss);
      server.start();

      Scanner sc = new Scanner(System.in);
      System.out.print("Pulse s para parar el servidor ");
      String res = sc.nextLine();
      while (!res.equalsIgnoreCase("s")) {
        res = sc.nextLine();
      }

      ss.stop = true;
      sc.close();
    } catch (IOException e) {
      System.err.println("Ha ocurrido un error leyendo las propiedades del servidor");
    }

  }

  @Override
  public void run() {
    start();
  }

  private void start() {
    try (ServerSocket server = new ServerSocket(puerto)) {
      socketServidor = server;
      Subasta s1 = new Subasta(LocalTime.of(15, 5, 0), LocalTime.of(15, 5, 5), "uno", 5);
      Subasta s2 = new Subasta(LocalTime.of(15, 30, 0), LocalTime.of(16, 30, 0), "dos", 10);
      Subasta s3 = new Subasta(LocalTime.of(15, 30, 0), LocalTime.of(16, 30, 0), "tres", 15);
      subastasDelDia.add(s1);
      subastasDelDia.add(s2);
      subastasDelDia.add(s3);
      while (!stop) {
        Socket cliente = socketServidor.accept();
        new HiloSubasta(cliente, subastasDelDia, new ProtocoloSubastas()).start(); // Al hilo del cliente, le paso solo la lista de subastas del dia
      }
    } catch (IOException e) {
      System.err.println("No se ha podido escuchar en el puerto " + puerto);
    }
  }

}
