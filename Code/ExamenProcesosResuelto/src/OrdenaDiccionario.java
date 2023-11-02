import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.Map;
import java.util.TreeMap;

public class OrdenaDiccionario {

  public static void main(String[] args) {
    OrdenaDiccionario od = new OrdenaDiccionario();

    try {
      od.run(od.verificarLineaComandos(args));
    } catch (LineaComandosException e) {
      System.out.println("Ha ocurrido un error en la línea de comandos:");
      e.printMsg();
    } catch (IOException e) {
      System.out.println("Ha ocurrido un error de entrada/salida.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Map<String, String> verificarLineaComandos(String[] args) throws LineaComandosException {

    if (args.length < 4 || args.length > 6) {
      throw new LineaComandosException("El número de argumentos no es el esperado.");
    } else {

      Map<String, String> params = new TreeMap<>();

      if (args[0].equals("-cp")) {
        params.put("CLASSPATH", args[1]);
      } else {
        throw new LineaComandosException("Falta el parametro -cp");
      }

      if (args[2].equals("-if")) {
        params.put("INPUTF", args[3]);
      } else {
        throw new LineaComandosException("Falta el parametro -if");
      }

      if (args.length > 4) {
        if (args[4].equals("-wd")) {
          params.put("WORKINGD", args[5]);
        } else {
          throw new LineaComandosException("Falta el parametro -wd");
        }
      }

      return params;

    }

  }

  public void run(Map<String, String> params) throws IOException {
    File diccionario = new File(params.get("INPUTF"));

    try (BufferedReader reader = new BufferedReader(new FileReader(diccionario));) {
      Map<String, BufferedWriter> outputs = new TreeMap<>();
      String line;
      while ((line = reader.readLine()) != null) {
        if (!outputs.containsKey((line.charAt(0) + ""))) {
          ProcessBuilder pb = new ProcessBuilder("java", "OrdenarDefs");

          pb.environment().put("CLASSPATH", params.get("CLASSPATH"));

          if (params.get("WORKINGD") != null) {
            pb.directory(new File(params.get("WORKINGD")));
            pb.redirectOutput(Redirect.appendTo(new File(params.get("WORKINGD") + "/" + line.charAt(0) + ".txt")));
          } else {
            pb.redirectOutput(Redirect.appendTo(new File(line.charAt(0) + ".txt")));
          }

          Process p = pb.start();

          BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

          bw.write(line);
          bw.newLine();
          bw.flush();

          outputs.put(line.charAt(0) + "", bw);
        } else {
          BufferedWriter bw = outputs.get(line.charAt(0) + "");

          bw.write(line);
          bw.newLine();
          bw.flush();
        }
      }

      for (Map.Entry<String, BufferedWriter> entry : outputs.entrySet()) {
        try (BufferedWriter bw = entry.getValue();) {
          bw.write("FIN");
          bw.newLine();
          bw.flush();
        }
      }
    }
  }
}
