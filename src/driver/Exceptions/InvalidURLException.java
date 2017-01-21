package driver.Exceptions;

/**
 * Created by Rocky on 03/26/16.
 * 
 * The InvalidURLException class in an Exception thrown when the get command is
 * called with an invalid URL.
 */
public class InvalidURLException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with error message.
   */
  public InvalidURLException() {
    super("URL does not contain a .txt or .html file\n");
  }// end method InvalidURLException
}// end class InvalidURLException
