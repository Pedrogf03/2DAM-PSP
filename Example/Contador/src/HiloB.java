public class HiloB extends Thread{

	private Contador contador;
	
	public HiloB (String n, Contador c){
		setName(n);
		contador =c;	
	}

	 public void run(){
		System.out.println(getName());
		for(int j=0; j<300 ; j++){
			contador.decrementa();
			System.out.println(getName() + " decrementa contador, vale " + contador.valor());
			try{
				sleep(100);
			} catch(InterruptedException e) {}
		}
		System.out.println("Fin " + getName() + " contador vale " + contador.valor());
	}
}
