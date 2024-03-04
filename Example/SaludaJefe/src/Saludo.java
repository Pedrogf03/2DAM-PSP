public class Saludo {

  // saludoEmpleado() es invocado por los hilos 'empleado', que da lugar a que se espere: WAITING hasta que sea notificado 
  // wait() solo se puede invocar desde un bloque 'synchronized':
  // Un hilo solo puede entrar en estado de espera (WAITING) sobre un objeto
  // si previamente ha adquirido el cerrojo (lock) sobre ese objeto

  // Si wait() no se utiliza en un bloque synchronizced se produce una
  // java.lang.IllegalMonitorStateException

  /* Si no es jefe, el empleado va a quedar esperando a que llegue el jefe
  Se hace wait de el hilo que esta ejecutando y se bloquea, hasta que
  se le avise que ya puede saludar */

  public synchronized void saludoEmpleado(String nombre) {
    try {
      wait();
      System.out.println("\n" + nombre.toUpperCase() + "-: Buenos días jefe.");
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  // saludoJefe() es invocado por los hilos 'jefe'
  // saluda y luego notifica a los empleados para que saluden, para ello
  // notifyAll notifica a todos los hilos que esten esperando (WAITING)
  // sobre el objeto compartido
  // notifyAll() solo se puede invocar desde un bloque 'synchronized':
  // Un hilo solo puede notificar a  los hilos que esperan sobre un objeto 
  // si previamente ha adquirido el cerrojo (lock) sobre ese objeto

  // Si notifyAll() no se utiliza en un bloque synchronizced se produce una
  // java.lang.IllegalMonitorStateException

  /** Si es jefe, saluda y luego avisa a los empleados para que saluden
  El notifyAll despierta a todos los hilos que esten bloqueados */

  public synchronized void saludoJefe(String nombre) {
    System.out.println("\n****** " + nombre + "-: Buenos días empleados. ******");
    notifyAll();

    // notify(); //Estaría mal: solo se saca a un hilo del estado de espera
    // El resto de los hilos seguirían esperando recibir una
    // notificación, pero como no recibirań ninguna
    // los hilos no finalizan y el proceso no acaba nunca
  }
}