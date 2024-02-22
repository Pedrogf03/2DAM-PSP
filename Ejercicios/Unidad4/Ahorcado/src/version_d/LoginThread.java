package version_d;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LoginThread extends Thread {

  private Socket cliente;
  private LoginProtocol protocolo;
  private boolean logged;
  private UserDAO dao;
  private String username;
  private String passwd;

  public LoginThread(Socket cliente, LoginProtocol protocolo) {
    this.cliente = cliente;
    this.protocolo = protocolo;
    this.dao = new UserDAO();
  }

  public boolean succesful() {
    return logged;
  }

  @Override
  public void run() {

    DataInputStream entrada = null;
    DataOutputStream salida = null;

    try {

      entrada = new DataInputStream(cliente.getInputStream());
      salida = new DataOutputStream(cliente.getOutputStream());

      String input, output;

      Boolean res;
      do {
        // status: login -> checklogin
        output = protocolo.procesarMensaje(null);
        salida.writeUTF(output); // login
        salida.flush();

        // status: if(checklogin) -> waiting_players || if(!checklogin) -> login
        input = entrada.readUTF();
        username = input.split(";")[0];
        passwd = input.split(";")[1];
        res = dao.getUser(username, passwd);
        output = protocolo.procesarMensaje("" + res);
        salida.writeUTF(output);
        salida.flush();
      } while (!res);

      logged = true;

      // status: waiting_players -> waiting_game
      output = protocolo.procesarMensaje(null);
      salida.writeUTF(output); // waiting
      salida.flush();

    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("No se puede conectar con el cliente");
    }
  }

}
