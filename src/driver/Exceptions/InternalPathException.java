package driver.Exceptions;

/**
 * Created by Felipe on 25/03/16.
 * 
 * The InternalPathException is an exception class for errors with internal
 * paths.
 */
public class InternalPathException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with error message.
   */
  public InternalPathException(String path, String parent) {
    super(path + " is an internal path of " + parent + "\n");
  }// end constructor

}// end class InternalPathException
