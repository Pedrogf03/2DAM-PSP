import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

  private String[] args;

  public static void main(String[] args) throws IOException {

    try {
      new Cliente(args);
    } catch (CommandLineException e) {
      e.printError();
    }

  }

  public Cliente(String[] args) throws CommandLineException {
    this.args = verificarLinea(args);
    run();
  }

  public String[] verificarLinea(String[] args) throws CommandLineException {

    if (!args[0].equals("-d")) {
      throw new CommandLineException(1);
    }
    if (!args[2].equals("-p")) {
      throw new CommandLineException(2);
    }
    if (!args[4].equals("-m")) {
      throw new CommandLineException(3);
    }

    return args;

  }

  public void run() {
    try (Socket socketCliente = new Socket(args[1], Integer.parseInt(args[3]));
        DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
        DataOutputStream salida = new DataOutputStream(socketCliente.getOutputStream());
        Scanner sc = new Scanner(System.in);) {

      String palabra = args[5];
      salida.writeUTF(palabra);
      String respuesta = entrada.readUTF();
      System.out.println("Servidor: " + respuesta);

    } catch (IOException e) {
      System.err.println("No puede establer canales de E/S para la conexión");
      System.exit(-1);
    }
  }

}