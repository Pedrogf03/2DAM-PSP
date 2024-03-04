public class HiloPersonal extends Thread {
  private String nombre;
  private Saludo saludo;
  private boolean esJefe;

  public HiloPersonal(String nombre, Saludo salu, boolean esJefe) {
    this.nombre = nombre;
    this.saludo = salu;
    this.esJefe = esJefe;
  }

  public void run() {
    System.out.println(nombre + " llegó.");
    try {
      Thread.sleep(1000);
      //Verifico si es personal que esta es jefe o no
      if (esJefe)
        saludo.saludoJefe(nombre);
      else
        saludo.saludoEmpleado(nombre);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }
}