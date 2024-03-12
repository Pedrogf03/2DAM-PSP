import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Organizador {

  private Map<Integer, List<ProcessBuilder>> subProcesos;
  private Map<Integer, List<Integer>> numerosSubProcesos;
  private Map<Integer, List<InputStream>> inputProcesos;

  public Organizador(String[] argumentos) {
    subProcesos = new HashMap<>();
    numerosSubProcesos = new HashMap<>();
    inputProcesos = new HashMap<>();
    try {
      comprobarLineaComandos(argumentos);
    } catch (LineaComandosException lce) {
      System.out.println(lce.getMessage());
    }
  }

  private void comprobarLineaComandos(String[] args) throws LineaComandosException {
    if (args.length < 6) {
      throw new LineaComandosException("Linea de comandos incompleta, comprueba que esta todo correcto");
    }

    int contador = 0;
    while (contador < args.length) {
      List<ProcessBuilder> procesos = new ArrayList<>();
      List<Integer> numeros = new ArrayList<>();
      int numeroObjetivo = 0;
      int numeroJugadores = 0;
      File fichero = null;
      boolean usarFichero = false;

      if (!args[contador].equals("-n")) {
        throw new LineaComandosException("Linea de comandos incorrecta, debe empezar con -n");
      }
      contador++;
      if (args[contador].contains(".txt")) {
        fichero = new File(args[contador]);
        if (!fichero.exists()) {
          throw new LineaComandosException("Fichero no existe");
        }
        usarFichero = true;
        contador++;
      } else {
        for (int i = 0; i < 4; i++) {
          try {
            numeros.add(Integer.parseInt(args[contador]));
            contador++;
          } catch (NumberFormatException nfe) {
            throw new LineaComandosException("Despues de -n debe aparecer 4 numeros o un archivo .txt");
          }
        }
      }
      if (!args[contador].equals("-o")) {
        throw new LineaComandosException("Despues de -n y sus caracteristicas debe venir -o");
      }
      contador++;
      try {
        numeroObjetivo = Integer.parseInt(args[contador]);
        contador++;
      } catch (NumberFormatException nfe) {
        throw new LineaComandosException("Despues de -o debe aparecer un numero");
      }
      if (!args[contador].equals("-j")) {
        throw new LineaComandosException("Despues de -o y sus caracteristicas debe venir -j");
      }
      contador++;
      try {
        numeroJugadores = Integer.parseInt(args[contador]);
        contador++;
      } catch (NumberFormatException nfe) {
        throw new LineaComandosException("Despues de -j debe aparecer un numero");
      }

      for (int i = 0; i < numeroJugadores; i++) {
        ProcessBuilder pb = new ProcessBuilder("java", "Jugador", numeroObjetivo + "");
        if (usarFichero) {
          pb.redirectInput(Redirect.from(fichero));
        }
        pb.environment().put("CLASSPATH", ".");

        procesos.add(pb);
      }
      subProcesos.put(numeroObjetivo, procesos);
      if (!usarFichero)
        numerosSubProcesos.put(numeroObjetivo, numeros);

    }

  }

  public void lanzarProcesos() {
    for (int clave : subProcesos.keySet()) {
      List<Integer> numeros = numerosSubProcesos.get(clave);
      List<InputStream> salidaProcesos = new ArrayList<>();
      for (ProcessBuilder proceso : subProcesos.get(clave)) {
        try {
          Process p = proceso.start();
          if (numeros != null) {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            for (int i = 0; i < numeros.size(); i++) {
              bw.write(numeros.get(i) + "");
              bw.newLine();
              bw.flush();
            }
          }
          salidaProcesos.add(p.getInputStream());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      inputProcesos.put(clave, salidaProcesos);
    }
  }

  public void comprobarGanador() {
    List<String> textoGanadores = new ArrayList<>();
    int contador = 1;
    for (int clave : inputProcesos.keySet()) {
      int jugadaMaxProxima = 99999;
      contador = 1;
      List<Integer> ganadores = new ArrayList<>();
      for (InputStream salida : inputProcesos.get(clave)) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(salida));) {
          String linea = br.readLine();
          int jugada = Integer.parseInt(linea);
          if (clave - jugada <= jugadaMaxProxima) {
            jugadaMaxProxima = clave - jugada;
            ganadores.add(contador);
          }

          contador++;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      String ganador = "";
      if (ganadores.size() == 1)
        ganador = "ha ganado el jugador " + ganadores.get(0);
      else {
        ganador = "han empatado los jugadores ";
        for (int jugador : ganadores)
          ganador += jugador + " ";
      }

      textoGanadores.add(ganador);
    }
    contador = 1;
    for (int i = textoGanadores.size() - 1; i >= 0; i--) {
      System.out.println("Partida " + contador + ", " + textoGanadores.get(i));
      contador++;
    }
  }

  public static void main(String[] args) {
    Organizador organizador = new Organizador(args);
    organizador.lanzarProcesos();
    organizador.comprobarGanador();
    //Probado en terminal, no en visual studio
  }
}
