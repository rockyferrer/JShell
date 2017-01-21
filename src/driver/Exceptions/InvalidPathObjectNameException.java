package driver.Exceptions;

/**
 * Created by Rocky on 05/03/16.
 * 
 * The InvalidPathObjectName class in an Exception for an invalid path object
 * name.
 */
public class InvalidPathObjectNameException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with error message.
   */
  public InvalidPathObjectNameException() {
    super("Invalid path name\n");
  }// end constructor

}// end class InvalidPathObjectName
