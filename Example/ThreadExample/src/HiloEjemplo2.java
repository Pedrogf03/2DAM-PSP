public class HiloEjemplo2 extends Thread{

    public void run() {

        System.out.println(
          "Dentro del hilo      : " + getName() +
          "\n\tPrioridad    : " + getPriority() +
          "\n\tID           : " + getId() +
          "\n\tHilos activos: " + activeCount()
        );

    }

    public static void main(String[] args) {
      
        Thread.currentThread().setName("Principal");
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread());

        HiloEjemplo2 h = null;

        for(int i = 0; i < 3; i++){
          h = new HiloEjemplo2();
          h.setName("Hilo " + i);
          h.setPriority(i+1);
          h.start();
          System.out.println("Información del " + h.getName() + ": " + h);
        }

        System.out.println("3 HILOS CREADOS");
        System.out.println("Hilos activos: " + Thread.activeCount());

    }
  
}
