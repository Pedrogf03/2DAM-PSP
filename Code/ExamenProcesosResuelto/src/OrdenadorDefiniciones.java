import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class OrdenadorDefiniciones {

  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    Set<String> defs = new TreeSet<>();

    while (true) {
      String def = sc.nextLine();

      if (def.equals("FIN"))
        break;

      defs.add(def);
    }

    for (String string : defs) {
      System.out.println(string);
    }

    sc.close();
  }
}
