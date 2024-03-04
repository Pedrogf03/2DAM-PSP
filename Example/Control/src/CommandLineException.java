public class CommandLineException extends Exception{

    String msg;
  
    public CommandLineException(String msg){
        this.msg = msg;
    }

    public void printMsg(){
      System.out.println(this.msg);
    }

}
