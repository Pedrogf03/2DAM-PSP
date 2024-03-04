/**
En este ejemplo se lanzan varios hilos se sincronizan empleando un objeto compartido:
- Los hilos 'empleado' se quedan esperando (estado WAITING) invocandoa wait sobre el objeto compartido
- El hilo 'jefe' desbloquea a todos los hilos bloquedas sobre ese objeto compartido invocando a notifyAll 
*/

public class SaludoAJefe {
  public static void main(String[] args) {

    // Objeto en comun, se encarga del wait y notify
    Saludo s = new Saludo();

    /* 
     * Instancio los hilos y le paso como parametros:
     *
     * El Nombre del Hilo (en este caso es el nombre del personal)
     * El objeto en comun (Saludo)
     * Un booleano para verificar si es jefe (notify) o empleados (wait)
     *
    */

    HiloPersonal empleado1 = new HiloPersonal("Pepe", s, false);
    HiloPersonal empleado2 = new HiloPersonal("José", s, false);
    HiloPersonal empleado3 = new HiloPersonal("Pedro", s, false);
    HiloPersonal jefe1 = new HiloPersonal("JEFE", s, true);

    //Se lanzan los hilos 'empleado' (hilos que esperan: wait)     
    empleado1.start();
    empleado2.start();
    empleado3.start();

    Thread hilos[] = { empleado1, empleado2, empleado3 };
    for (Thread t : hilos) {
      /*Invocar a 'join' daría lugar a un interbloqueo entre el hilo de main y el primer hilo 'empleado'*/

      // Error: se bloquean todos los hilos (Empleados y Jefe)
      // try {
      //   t.join();
      // } catch (InterruptedException e) {
      //   System.out.println(e);
      // }

      //Para asegurarnos que los hilos hayan ejecutado wait.
      while (t.getState() != Thread.State.WAITING)
        ;
    }

    //Se lanza el hilo 'jefe' (hilo que desbloquea a todos: notifyAll)
    jefe1.start();
  }
}