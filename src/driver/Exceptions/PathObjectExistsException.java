package driver.Exceptions;

/**
 * Created by Rocky on 05/03/2016.
 *
 * The PathObjectExistsException class in an Exception for a file/directory that
 * already exists.
 */
public class PathObjectExistsException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with error message.
   */
  public PathObjectExistsException() {

    super("Directory already exists\n");
  }// end constructor

}// end class PathObjectExistsException
