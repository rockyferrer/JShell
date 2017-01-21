package driver;

import java.util.Arrays;

import driver.Exceptions.InvalidTypeException;

/**
 * Created by Felipe on 21/02/16.
 * 
 * The Ls class handles the "ls" command. It prints the names of the
 * sub-directories and files in the current working directory.
 */
public class Ls extends JCommand {

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public Ls(ShellData sd) {
    super(sd);
  }// end constructor

  /**
   * Takes the PATH of a directory and returns a string containing the names of
   * all sub-directories and sub-files of the directory.
   *
   * @param args String representation of the PATH of the directory.
   * @return Returns a string containing the names of all sub-directories and
   *         sub-files of the directory.
   */
  public String execute(String[] args) throws Exception {
    checkArgSizeValidity(args);
    boolean recurse = false;
    String[] pathArgs;
    String ls = "";

    if (args.length != 0 && args[0].toUpperCase().equals("-R")) {
      recurse = true;
      pathArgs = Arrays.copyOfRange(args, 1, args.length);
    }

    else
      pathArgs = args;

    if (pathArgs.length == 0)
      if (recurse)
        ls = recursiveExecute(shellData.getCurrentDirectory());
      else
        ls = getSubObjectNames(shellData.getCurrentDirectory()) + "\n";

    else {
      // Loop through all PATHs inputted as arguments
      for (String s : pathArgs) {
        if (pathArgs.length > 1)
          ls += s + ":\n";

        String[] pathArray = Directory.convertStringToPathArray(s);

        // Convert the PATH string to the object represented by the PATH
        PathObject p = Directory.convertPathArrayToObject(pathArray, shellData);

        // Assert that this PathObject is a Directory and not a File
        if (!(p instanceof Directory))
          throw new InvalidTypeException("directory");

        // After assertion, cast the PathObject to a Directory.
        Directory d = (Directory) p;

        if (recurse) {
          ls += recursiveExecute(d);
        } else {
          // create a string containing the names of all the sub-objects of d
          ls += getSubObjectNames(d) + "\n";
        }
      }
    }
    return ls;
  }// end method execute

  /**
   * Takes a Directory and returns a string containing the names of all
   * sub-directories and sub-files of the directory recursively.
   *
   * @param d The Directory on which to recursively execute the command.
   * @return Returns a string containing the names of all sub-directories and
   *         sub-files of the directory recursively found and listed.
   */
  public String recursiveExecute(Directory d) {
    String output = "";

    output += d.toString() + ":" + "\n" + getSubObjectNames(d) + "\n\n";

    for (PathObject subd : d.getSubObjects())
      if (subd instanceof Directory)
        output += recursiveExecute((Directory) subd);

    return output;
  }// end method recursiveExecute

  /**
   * Takes the PATH of a directory and returns a string containing the names of
   * all sub-directories and sub-files of the directory.
   *
   * @param d String representation of the PATH of the directory.
   * @return Returns a string containing the names of all sub-directories and
   *         sub-files of the directory.
   */
  private String getSubObjectNames(Directory d) {
    String ls = "";
    for (PathObject e : d.getSubObjects())
      ls += e.getName() + " ";
    return ls;
  }// end method getSubObjectNames

  /**
   * Returns true if the number of arguments is valid for this command.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns true if the arguments size is correct and false otherwise.
   */
  public boolean correctArgSize(String[] args) {
    return args.length >= 0;
  }// end method correctArgSize

}// end class Ls
