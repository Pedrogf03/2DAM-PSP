public class Supermercado {

  private int maximo;
  private int nLatas;
  private Cliente cliente;

  private boolean reponer = false; // Variable que dice si hay que reponer latas o no
  private boolean atendido = false; // Variable para saber si se ha atendido al cliente o no

  public Supermercado(int total) {
    this.maximo = total; //número máximo de latas
    this.nLatas = total; //número de latas disponibles
    cliente = null; //cliente atendido por el cajero
  }

  public int getMaximo() {
    return maximo;
  }

  /**
   * 	Este método lo utiliza un Cliente para coger num latas del lineal (estantería) num >=1 && num <= maximo.
   */
  public synchronized void comprarLatas(Cliente c, int num) throws InterruptedException {

    //TO-DO
    // Mientras el reponedor repone, los clientes esperan.
    while (reponer)
      wait();

    System.out.println("Cliente " + c + " quiere " + num + " latas. Hay " + nLatas);

    // Si no hay latas suficientes, se avisa al reponedor y se espera a que haya.
    while (num > nLatas) {
      System.out.println("Faltan latas");
      System.out.println("Cliente " + c + " espera");
      reponer = true;
      notifyAll();
      do
        wait(); // Se duerme y, mientras aún no se haya repuesto, se vuelve a dormir.
      while (reponer);
      System.out.println("Cliente " + c + " quiere " + num + " latas. Hay " + nLatas);
    }

    nLatas -= num; // El cliente coge las latas.

    System.out.println("Cliente " + c + " coge " + num + " latas de la estantería. Quedan: " + nLatas + " latas");

  }

  /** 	
   * Este método lo utiliza el Reponedor para esperar el aviso de un Cliente por falta de latas.		
   */
  public synchronized void esperarPeticion() throws InterruptedException {
    //TO-DO
    // Mientras que no haya que reponer, el reponedor espera.
    while (!reponer)
      wait();

    System.out.println("El reponedor ha sido despertado");
  }

  /**
   * Este método lo utiliza el Reponedor para reponer las latas en el lineal (estantería), asegurándose que haya el maximo.		
   */
  public synchronized void nuevoSuministro() throws InterruptedException {
    //TO-DO

    // Una vez despertado, repone las latas y da el aviso.
    System.out.println("El reponedor suministra nuevas latas");
    nLatas = maximo;
    reponer = false;

    notifyAll();

  }

  /** 	
   * Este método lo utiliza un Cliente para pagar en la caja.		
   */
  public synchronized void pagar(Cliente c) throws InterruptedException {

    //TO-DO
    // Si ya hay un cliente siendo atendido, espera.
    while (cliente != null) {
      wait();
    }

    cliente = c; // Se guarda el cliente que se está atendiendo.

    System.out.println("Cliente " + c + " empieza a pagar");

    // Se avisa al cajero de que el cliente está pagando y se espera a que se le atienda.
    notifyAll();
    do
      wait(); // Se va a dormir y, mientras no se le haya atendido, se vuelve a dormir.
    while (!atendido);

    System.out.println("Cliente " + c + " ha terminado de pagar y se va");

    // Cuando se va, cliente vuelve a null.
    cliente = null;

    // Notifica que ya no se está atendiendo ningún cliente.
    notifyAll();

  }

  /**
   * Este método lo utiliza el Cajero para cobrar a un Cliente.		
   */
  public synchronized void cobrar() throws InterruptedException {
    //TO-DO

    // Mientras que no haya cliente, se espera por él.
    while (cliente == null) {
      System.out.println("El cajero está esperando a que haya productos en la linea de caja");
      wait();
    }

    System.out.println("Cajero cobra al cliente " + cliente);

    atendido = true;

    // Se notifica de que se ha atendido y espera a que el cliente se vaya.
    notifyAll();
    do
      wait(); // Se duerme y, mientras el cliente no se haya ido, se vuelve a dormir.
    while (cliente != null);

    // El cliente se ha ido y el cajero ya está disponible.
    System.out.println("Cajero está disponible para otro cliente");
    atendido = false;

  }
}