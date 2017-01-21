package driver.Exceptions;

/**
 * Created by Felipe on 05/03/16.
 * 
 * The InvalidNumberOfArgsException class in an Exception for invalid number of
 * arguments.
 */
public class InvalidNumberOfArgsException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with error message.
   */
  public InvalidNumberOfArgsException() {
    super("Invalid amount of arguments\n");
  }// end constructor

}// end class InvalidNumberOfArgsException
