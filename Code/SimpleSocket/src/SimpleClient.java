import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.*;
import javax.swing.*;

/**
 * A simple client used to connect to the server
 */
public class SimpleClient extends JFrame implements ActionListener {
  Socket client = null;
  String serverAddr = "localhost";
  int serverPort = 8888;
  PrintWriter out;
  JTextField tf;

  public SimpleClient() {
    try {
      client = new Socket(serverAddr, serverPort);
      System.out.println("Client: " + client);
      out = new PrintWriter(client.getOutputStream());
      // out.println("Hello");
      out.flush(); // need to flush a short message
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // Set up the UI
    Container cp = this.getContentPane();
    cp.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
    cp.add(new JLabel("Enter your message or \"quit\""));
    tf = new JTextField(40);
    tf.addActionListener(this);
    cp.add(tf);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.pack();
    this.setTitle("Simple Client");
    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String message = tf.getText();
    if (message.equals("quit")) {
      // Need to close the socket to orderly disconnect from the server
      try {
        out.close();
        client.close();
        System.exit(0);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else {
      // Send the message entered to the network socket
      out.println(message);
      out.flush();
      tf.setText("");
    }
  }

  public static void main(String[] args) {
    new SimpleClient();
  }
}
