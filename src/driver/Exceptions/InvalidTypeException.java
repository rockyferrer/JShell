package driver.Exceptions;

/**
 * Created by Felipe on 05/03/16.
 * 
 * The InvalidTypeException class is an Exception thrown when the path type is
 * invalid.
 */
public class InvalidTypeException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with error message.
   */
  public InvalidTypeException(String type) {
    super("Path not a " + type + "\n");
  }// end constructor

}// end class InvalidTypeException
