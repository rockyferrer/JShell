package driver.Exceptions;

/**
 * Created by Felipe on 05/03/16.
 * 
 * The NullPathException class in an Exception for a null path.
 */
public class NullPathException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with error message.
   */
  public NullPathException() {
    super("Path does not exist\n");
  }// end constructor

}// end class NullPathException
