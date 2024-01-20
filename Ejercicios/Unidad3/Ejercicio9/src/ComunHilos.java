import java.net.Socket;

public class ComunHilos {
  private int conexiones; //Nº DE CONEXIONES TOTALES, OCUPADAS EN EL ARRAY
  private int actuales; //NÚMERO DE CONEXIONES ACTUALES
  private int maximo; //MÁXIMO DE CONEXIONES PERMITIDAS	
  private Socket tabla[] = new Socket[maximo];// SOCKETS CONECTADOS
  private String mensajes; //MENSAJES DEL CHAT

  public ComunHilos(int maximo, int actuales, int conexiones, Socket[] tabla) {

    this.maximo = maximo;
    this.actuales = actuales;
    this.conexiones = conexiones;
    this.tabla = tabla;
    this.mensajes = "";

  }

  public ComunHilos() {
    super();
  }

  public int getMaxmino() {
    return maximo;
  }

  public void setMaximo(int maximo) {
    this.maximo = maximo;
  }

  public int getConexiones() {
    return conexiones;
  }

  public synchronized void setConexiones(int conexiones) {
    this.conexiones = conexiones;
  }

  public String getMensajes() {
    return mensajes;
  }

  public synchronized void setMensajes(String mensajes) {
    this.mensajes = mensajes;
  }

  public int getActuales() {
    return actuales;
  }

  public synchronized void setActuales(int actuales) {
    this.actuales = actuales;
  }

  public synchronized void addTabla(Socket s, int i) {
    tabla[i] = s;
  }

  public Socket getElementoTabla(int i) {
    return tabla[i];
  }

}//ComunHilos
