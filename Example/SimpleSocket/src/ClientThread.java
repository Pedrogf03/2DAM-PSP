import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientThread extends Thread {

  Socket socket;

  public ClientThread(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    InputStream in;
    try {
      in = socket.getInputStream();
      int byteRead;
      while ((byteRead = in.read()) != -1) {
        System.out.println((char) byteRead);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
