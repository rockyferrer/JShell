package driver;

import driver.Exceptions.InvalidTypeException;

/**
 * Created by Sajjad on 22/02/16.
 *
 * The Cd class handles the "cd" command. It changes the current working
 * directory.
 */
public class Cd extends JCommand {

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public Cd(ShellData sd) {
    super(sd);
  }// end constructor

  /**
   * Executes the "cd" command, which changes to (or stays on) to a certain
   * directory based on the user input.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns a String that will be printed in JShell.
   */
  public String execute(String[] args) throws Exception {
    checkArgSizeValidity(args);
    String[] directoryEntry = Directory.convertStringToPathArray(args[0]);
    // change the directory based on the given path
    PathObject newDirectory =
        Directory.convertPathArrayToObject(directoryEntry, shellData);

    // Make sure the object is a directory and not a file
    if (!(newDirectory instanceof Directory))
      throw new InvalidTypeException("directory");

    // Change the current directory
    shellData.setCurrentDirectory((Directory) Directory
        .convertPathArrayToObject(directoryEntry, shellData));
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

}// end class Cd
