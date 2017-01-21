package driver;

/**
 * Created by Abhishek on 26/02/16. Completed by Rocky.
 * 
 * The Pushd class handles the "pushd" command, which pushes a given Directory
 * onto the DirectoryStack.
 */
public class Pushd extends JCommand {

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public Pushd(ShellData sd) {
    super(sd);
  }// end constructor

  /**
   * Executes the "pushd" command, which pushes a Directory onto the
   * DirectoryStack.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns a String that will be printed in JShell.
   */
  public String execute(String[] args) throws Exception {
    checkArgSizeValidity(args);

    // copy current directory to temporary variable
    Directory temp = shellData.getCurrentDirectory();

    // cd into inputted directory
    Cd cd = new Cd(shellData);
    cd.execute(args);

    // previous directory will only be pushed if cd successfully executes
    shellData.getDirectoryStack().push(temp);

    return "";
  }// end method execute

  /**
   * Returns true if the number of arguments is valid for this command.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns true if the arguments size is correct and false otherwise.
   */
  public boolean correctArgSize(String[] args) {
    return args.length == 1;
  }// end method correctArgSize

}// end class Pushd
