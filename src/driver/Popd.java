package driver;

import driver.Exceptions.DirectoryStackIsEmptyException;

/**
 * Created by Abhishek on 26/02/16.
 * 
 * The Popd class handles the "popd" command, which pops a Directory off the
 * DirectoryStack.
 */
public class Popd extends JCommand {

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public Popd(ShellData sd) {
    super(sd);
  }// end constructor

  /**
   * Executes the "popd" command, which pops a Directory off the DirectoryStack.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns a String that will be printed in JShell.
   */
  public String execute(String[] args) throws Exception {
    checkArgSizeValidity(args);

    if (!shellData.getDirectoryStack().isEmpty()) {
      // pop directory from stack
      Directory d = shellData.getDirectoryStack().pop();

      // cd to popped directory
      Cd cd = new Cd(shellData);
      cd.execute(new String[] {d.toString()});
    } else {// throw exception if stack is empty
      throw new DirectoryStackIsEmptyException();
    }
    return "";
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

}// end class Popd
