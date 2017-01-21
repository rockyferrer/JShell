package driver.Exceptions;

/**
 * Created by Rocky on 05/03/16.
 *
 * The DirectoryStackIsEmptyException class in an Exception for an empty
 * directory stack.
 */
public class DirectoryStackIsEmptyException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with error message.
   */
  public DirectoryStackIsEmptyException() {
    super("The directory stack is empty\n");
  }// end constructor

}// end class DirectoryStackIsEmptyException
