public class HiloEjemploGrupos extends Thread {

    public void run () {
        System.out.println("Información del hilo: " + currentThread());
        for(int i = 0; i < 5; i++) {
            System.out.println("Hilo: " + currentThread().getName() + " i= " + i);
        }
    }
    public static void main(String[] args) throws Exception {

        Thread.currentThread().setName("Principal");
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread());

        ThreadGroup grupo = new ThreadGroup("Grupo de hilos que molan");
        HiloEjemploGrupos h = new HiloEjemploGrupos();

        Thread h1 = new Thread(grupo, h,"Hilo 1");
        Thread h2 = new Thread(grupo, h,"Hilo 2");
        Thread h3 = new Thread(grupo, h,"Hilo 3");

        h1.start();
        h2.start();
        h3.start();

    }
}
