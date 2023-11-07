public class CompartirInfSync{

	public static void main(String[] args){
		CuentaSync cuenta = new CuentaSync(40);
		SacarDineroSync h1 = new SacarDineroSync("Ana",cuenta);
		SacarDineroSync h2 = new SacarDineroSync("Juan",cuenta);
		h1.start();
		h2.start();
	}
}
