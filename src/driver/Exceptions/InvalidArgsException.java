package driver.Exceptions;

/**
 * Created by Abhishek on 05/03/16.
 * 
 * The InvalidArgsException class in an Exception for invalid arguments.
 */
public class InvalidArgsException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with error message.
   */
  public InvalidArgsException() {
    super("Invalid arguments\n");
  }// end constructor

}// end class InvalidArgsException
