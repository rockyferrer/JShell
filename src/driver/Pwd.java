package driver;

import driver.Exceptions.InvalidNumberOfArgsException;
import driver.Exceptions.NullPathException;

/**
 * Created by Felipe on 02/03/16.
 * 
 * The Pwd class handles the "pwd" command, which prints the current working
 * directory.
 */
public class Pwd extends JCommand {

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public Pwd(ShellData sd) {
    super(sd);
  }// end constructor

  /**
   * Executes the "pwd" command, which prints the current working directory.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns a String that will be printed in JShell.
   */
  public String execute(String[] args)
      throws InvalidNumberOfArgsException, NullPathException {
    checkArgSizeValidity(args);
    return shellData.getCurrentDirectory().toString() + "\n";
  }// end method execute

  /**
   * Returns true if the number of arguments is valid for this command.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns true if the arguments size is correct and false otherwise.
   */
  public boolean correctArgSize(String[] args) {
    return args.length == 0;
  }// end method correctArgSize

}// end class Pwd
