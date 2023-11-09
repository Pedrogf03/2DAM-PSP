public class Hilo2 extends Thread {

  private long num;
  private int nFactores;

  public Hilo2(long n, int nFactores) {
    this.num = n;
    this.nFactores = nFactores;
  }

  @Override
  public void run() {
    System.out.println("Factores primos: " + num);
    long numero = num;
    int factores = 0;
    for (int i = 2; i < numero; i++) {
      while (num % i == 0) {
        num = num / i;
        System.out.println("Primos: " + i);
        factores++;
        if (Thread.interrupted()) {
          if (nFactores <= factores) {
            System.out.println("Se han producido " + factores + "/" + nFactores + " factores.");
            return;
          } else {
            System.out.println("Se han producido " + factores + "/" + nFactores + " factores.");
          }
          if (num == 1) {
            System.out.println("Fin de la descomposición.");
            break;
          }
        }
      }
    }
  }
}
