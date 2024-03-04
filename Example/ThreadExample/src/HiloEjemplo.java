public class HiloEjemplo extends Thread {
    
    HiloEjemplo(String nombre){
        super(nombre);
        System.out.println("Creando hilo: " +  this.getName());
    }

    @Override
    public void run() {
        for(int i = 0; i < 5; i++){
            System.out.println("Hilo: " + this.getName() + " i= " + i);
        }
    }

    public static void main(String[] args) {
        
        HiloEjemplo ha = new HiloEjemplo("Hilo a");
        HiloEjemplo hb = new HiloEjemplo("Hilo b");
        HiloEjemplo hc = new HiloEjemplo("Hilo c");

        ha.start();
        hb.start();
        hc.start();

    }

}
