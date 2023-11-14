import java.util.ArrayList;

public class BuscaPasswd extends Thread {

  String[] palabras;
  String passwd;
  boolean found = false;
  ArrayList<BuscaPasswd> hermanos = new ArrayList<>();

  public BuscaPasswd(String[] palabras, String passwd, String name, ArrayList<BuscaPasswd> hermanos) {
    super(name);
    this.palabras = palabras;
    this.passwd = passwd;
    this.hermanos = hermanos;
  }

  public boolean encontrado() {
    return found;
  }

  @Override
  public void run() {

    for (int i = 0; i < palabras.length; i++) {
      if (palabras[i].equals(passwd)) {
        found = true;
        for (BuscaPasswd h : hermanos) {
          if (!h.equals(this)) {
            h.interrupt();
          }
        }
        break;
      }
      if (Thread.interrupted()) {
        return;
      }
      try {
        sleep(1000);
      } catch (Exception e) {

      }
    }

  }

}
