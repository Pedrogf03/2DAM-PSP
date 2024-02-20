package version_d;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class UserDAO {

  File users = new File("users.dat");

  public Boolean getUser(String user, String passwd) {

    try (BufferedReader reader = new BufferedReader(new FileReader(users))) {

      String line;
      while ((line = reader.readLine()) != null) {

        String[] partes = line.split(";");
        String userFile = partes[0];
        String passwdFile = partes[1];
        String conectado = partes[2];

        if (user.equals(userFile) && passwd.equals(passwdFile) && conectado.equals("0")) {
          return true;
        }

      }

      return false;

    } catch (Exception e) {
      return false;
    }
  }

  // public Boolean connectUser(String user, String passwd) {

  //   try (BufferedReader reader = new BufferedReader(new FileReader(users))) {

  //     String line;
  //     while ((line = reader.readLine()) != null) {

  //       String[] partes = line.split(";");
  //       String userFile = partes[0];
  //       String passwdFile = partes[1];
  //       String conectado = partes[2];

  //       // TODO

  //     }

  //     return false;

  //   } catch (Exception e) {
  //     return false;
  //   }
  // }

}
