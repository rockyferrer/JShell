package driver;

import driver.Exceptions.*;

/**
 * Created by Abhishek on 21/02/16.
 * 
 * This the superclass JCommand that has many subclasses (one for each of the
 * commands). Handles the commands. It has the execute method and an empty
 * constructor and a arguments string array.
 */
public abstract class JCommand {

  // the ShellData used for singleton purposes
  protected ShellData shellData;

  // the arguments entered along with the command
  protected String[] args;

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public JCommand(ShellData sd) {
    shellData = sd;
  }// end constructor

  /**
   * Executes a certain command and returns a string to print in JShell.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns a String that will be printed in JShell.
   */
  public abstract String execute(String[] args) throws Exception;
  // end method execute

  /**
   * Returns true if the number of arguments is valid for this command.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns true if the arguments size is correct and false otherwise.
   */
  public abstract boolean correctArgSize(String[] args);
  // end method correctArgSize

  /**
   * Throws an Exception if the the arguments size if wrong.
   *
   * @param args The String array of arguments passed in by the user.
   */
  protected void checkArgSizeValidity(String[] args)
      throws InvalidNumberOfArgsException {
    if (!correctArgSize(args))
      throw new InvalidNumberOfArgsException();
  }// end method checkArgSizeValidity

}// end class JCommand
