public class Principal {

  public static void main(String[] args) {

    final int MAX_LATAS_CLIENTE = 10;
    final int CLIENTES = 10;

    Supermercado s = new Supermercado(MAX_LATAS_CLIENTE);

    Cajero cajero = new Cajero(s);
    Reponedor reponedor = new Reponedor(s);
    Cliente[] cliente = new Cliente[CLIENTES];

    int id = 1;
    for (int i = 0; i < cliente.length; i++)
      cliente[i] = new Cliente(s, id++);

    cajero.start();
    reponedor.start();

    for (int i = 0; i < cliente.length; i++)
      cliente[i].start();

    //Aquí debes implementar la condición de sincronización CS Principal
    try {
      for (Cliente c : cliente) {
        c.join();
      }
      System.out.println("Todos los clientes se han ido");
      cajero.interrupt();
      reponedor.interrupt();
    } catch (InterruptedException e) {

    }

  }

}