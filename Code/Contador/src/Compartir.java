public class Compartir{

	public static void main(String[] args){
		Contador contador = new Contador(100);
		HiloA a = new HiloA("HiloA",contador);
		HiloB b = new HiloB("HiloB",contador);
		a.start();
		b.start();
	}
}
