import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.Map;
import java.util.TreeMap;

public class OrdenadorDiccionario {

  private Map<String, String> commandLine;

  public static void main(String[] args) {

    try {
      OrdenadorDiccionario od = new OrdenadorDiccionario(args);
      od.createProcess();
    } catch (CommandLineException e) {
      System.out.println(e.getMsg());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public OrdenadorDiccionario(String[] args) throws CommandLineException {

    this.commandLine = verificarLineaComandos(args);

  }

  public Map<String, String> verificarLineaComandos(String[] args) throws CommandLineException {

    Map<String, String> map = new TreeMap<>();

    if (args.length != 4 && args.length != 6) {
      throw new CommandLineException("Error. Número de argumentos inválido.");
    }

    if (!args[0].equals("-cp")) {
      throw new CommandLineException("Error. Se esperaba el parámetro '-cp'.");
    } else {
      map.put("CP", args[1]);
    }

    if (!args[2].equals("-if")) {
      throw new CommandLineException("Error. Se esperaba el parámetro '-if'.");
    } else {
      map.put("IF", args[3]);
    }

    if (args.length > 4) {
      if (!args[4].equals("-wd")) {
        throw new CommandLineException("Error. Se esperaba el parámetro '-wd'.");
      } else {
        map.put("WD", args[5]);
      }
    }

    return map;

  }

  public void createProcess() throws IOException {

    File diccionario = new File(commandLine.get("IF"));

    Map<String, BufferedWriter> flujos = new TreeMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(diccionario))) {

      String line;

      while ((line = reader.readLine()) != null) {

        String fLetter = line.substring(0, 1).toUpperCase();

        if (flujos.containsKey(fLetter)) {

          BufferedWriter writer = flujos.get(fLetter);

          writer.write(line);
          writer.newLine();
          writer.flush();

        } else {

          ProcessBuilder pb = new ProcessBuilder("java", "OrdenadorStrings");

          pb.environment().put("CLASSPATH", commandLine.get("CP"));

          if (commandLine.containsKey("WD")) {
            pb.directory(new File(commandLine.get("WD")));
            pb.redirectOutput(Redirect.appendTo(new File(commandLine.get("WD") + "/" + fLetter + ".txt")));
          } else {
            pb.redirectOutput(Redirect.appendTo(new File(fLetter + ".txt")));
          }

          Process p = pb.start();

          BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

          writer.write(line);
          writer.newLine();
          writer.flush();

          flujos.put(fLetter, writer);

        }

      }

      for (Map.Entry<String, BufferedWriter> entry : flujos.entrySet()) {

        BufferedWriter writer = entry.getValue();

        writer.write("FIN");
        writer.newLine();
        writer.flush();
        writer.close();

      }

    }

  }

}
