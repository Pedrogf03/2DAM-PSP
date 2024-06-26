import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClienteChat extends JFrame implements ActionListener, Runnable {

  private static final long serialVersionUID = 1L;
  private Socket socket = null;
  // streams
  private DataInputStream fentrada;
  private DataOutputStream fsalida;
  private String nombre;
  private static JTextField mensaje = new JTextField();

  private JScrollPane scrollpane1;
  private static JTextArea textarea1;
  private JButton botonEnviar = new JButton("Enviar");
  private JButton botonSalir = new JButton("Salir");
  private boolean repetir = true;

  // constructor
  public ClienteChat(Socket s, String nombre) {

    super(" CONEXIÓN DEL CLIENTE CHAT: " + nombre);
    setLayout(null);

    mensaje.setBounds(10, 10, 400, 30);
    add(mensaje);

    textarea1 = new JTextArea();
    scrollpane1 = new JScrollPane(textarea1);
    scrollpane1.setBounds(10, 50, 400, 300);
    add(scrollpane1);

    botonEnviar.setBounds(420, 10, 100, 30);
    add(botonEnviar);
    botonSalir.setBounds(420, 50, 100, 30);
    add(botonSalir);

    textarea1.setEditable(false);
    botonEnviar.addActionListener(this);
    botonSalir.addActionListener(this);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    socket = s;
    this.nombre = nombre;

    try {

      fentrada = new DataInputStream(socket.getInputStream());
      fsalida = new DataOutputStream(socket.getOutputStream());
      String texto = " > Entra en el Chat ... " + nombre;
      fsalida.writeUTF(texto);

    } catch (IOException e) {

      System.out.println("ERROR DE E/S");
      e.printStackTrace();
      System.exit(0);

    }

  }// fin constructor

  // accion cuando pulsamos botones
  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == botonEnviar) { // SE PULSA EL ENVIAR

      if (mensaje.getText().trim().length() == 0)
        return;
      String texto = nombre + "> " + mensaje.getText();

      try {
        mensaje.setText("");
        fsalida.writeUTF(texto);
      } catch (IOException e1) {
        e1.printStackTrace();
      }

    }

    if (e.getSource() == botonSalir) { // SE PULSA BOTON SALIR
      String texto = " > Abandona el Chat ... " + nombre;

      try {
        fsalida.writeUTF(texto);
        fsalida.writeUTF("*");
        repetir = false;
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }

  }// accion botones

  public void run() {

    String texto = "";
    while (repetir) {

      try {
        texto = fentrada.readUTF();
        textarea1.setText(texto);
      } catch (IOException e) {
        // este error sale cuando el servidor se cierra
        JOptionPane.showMessageDialog(null, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage(),
            "<<MENSAJE DE ERROR:2>>", JOptionPane.ERROR_MESSAGE);
        repetir = false;
      }

    } // while

    try {
      fentrada.close();
      fsalida.close();
      socket.close();
      System.exit(0);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }// run

  public static void main(String args[]) {
    int puerto = 44444;
    Socket s = null;

    String nombre = JOptionPane.showInputDialog("Introduce tu nombre o nick:");

    if (nombre.trim().length() == 0) {
      System.out.println("El nombre está vacío....");
      return;
    }

    try {
      s = new Socket("localhost", puerto);
      //s = new Socket("192.168.9.90", puerto);
      ClienteChat cliente = new ClienteChat(s, nombre);
      cliente.setBounds(0, 0, 540, 400);
      cliente.setVisible(true);
      new Thread(cliente).start();

    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage(),
          "<<MENSAJE DE ERROR:1>>", JOptionPane.ERROR_MESSAGE);
    }

  }// main

}// ..ClienteChat
