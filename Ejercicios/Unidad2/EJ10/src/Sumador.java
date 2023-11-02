public class Sumador implements Runnable {

  private String[] nums;

  public Sumador(String[] args) {
    this.nums = args;
  }

  @Override
  public void run() {
    int suma = 0;

    for (String string : nums) {
      suma += Integer.parseInt(string);

      System.out.println(suma);

      try {
        Thread.sleep(1000);
      } catch (Exception ignore) {
      }
    }
  }

  public static void main(String[] args) throws Exception {
    Thread h = new Thread(new Sumador(args));
    h.start();
  }
}
