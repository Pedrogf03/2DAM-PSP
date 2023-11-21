import java.util.ArrayList;

public class BuscaPasswd extends Thread {

  private String[] palabras;
  private String passwd;
  boolean found = false;
  private ArrayList<BuscaPasswd> hermanos = new ArrayList<>();
  boolean interrumpido = false;

  public BuscaPasswd(String[] palabras, String passwd, String name, ArrayList<BuscaPasswd> hermanos) {
    super(name);
    this.palabras = palabras;
    this.passwd = passwd;
    this.hermanos = hermanos;
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
        interrumpido = true;
        return;
      }
    }

  }

}
