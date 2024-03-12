import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Subasta {

  private String nombreGanador;
  private int mayorOferta;
  private LocalTime inicio;
  private LocalTime fin;
  private String articulo;
  private List<HiloSubasta> participantes;
  private int currentTurno;
  private String resultadoAnterior;

  public Subasta(LocalTime inicio, LocalTime fin, String articulo, int precioInicial) {

    this.inicio = inicio;
    this.fin = fin;
    this.mayorOferta = precioInicial;
    this.articulo = articulo;
    this.participantes = new ArrayList<>();
    this.currentTurno = 1;

  }

  public int getCurrentTurno() {
    return currentTurno;
  }

  public int getParticipantes() {
    return participantes.size();
  }

  public boolean enCurso() {
    return !LocalTime.now().isBefore(inicio);
  }

  public boolean haAcabado() {
    return LocalTime.now().isAfter(fin);
  }

  public String getResultadoAnterior() {
    return resultadoAnterior;
  }

  // Sincronizado para que varios no puedan actualizar el precio a la vez
  public synchronized String actualizarPrecio(int oferta, String nombre) throws IOException {

    currentTurno++;
    if (currentTurno > participantes.size()) {
      currentTurno = 1;
    }

    String msg = "";

    if (oferta < mayorOferta) {
      msg = "menor";
    } else if (oferta == mayorOferta) {
      msg = "igual";
    } else {
      msg = "cambio";
      mayorOferta = oferta;
      nombreGanador = nombre;
    }

    msg += ";" + mayorOferta;

    resultadoAnterior = msg;

    notifyAll();

    return msg;

  }

  public String getGanador() {
    return nombreGanador;
  }

  public String getArticulo() {
    return articulo;
  }

  public boolean addParticipante(HiloSubasta hs) {
    return participantes.add(hs);
  }

  public LocalTime getInicio() {
    return inicio;
  }

  public synchronized int getPrecio() {
    return mayorOferta;
  }

  public synchronized void comenzarSubasta() {
    notifyAll();
  }

  public synchronized void esperarComienzo() throws InterruptedException {
    while (!enCurso()) {
      wait();
    }
  }

  public synchronized void esperarTurno(int turno) throws InterruptedException {
    if (turno != currentTurno) {
      wait();
    }
  }

}
