package driver;

import driver.Exceptions.InvalidArgsException;
import driver.Exceptions.InvalidTypeException;
import driver.Exceptions.NullPathException;

/**
 * Created by Felipe on 18/03/16.
 * 
 * The Grep class handles the "grep" command. It prints the lines in a file or
 * in a directory(recursively) that contain a given regex.
 */
public class Grep extends JCommand {

  boolean recurse = false;
  String regex, pathObject;

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public Grep(ShellData sd) {
    super(sd);
  }// end constructor

  /**
   * Executes the "grep" command, which copies an item from OLDPATH to NEWPATH.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns a String that will be printed in JShell.
   */
  @Override
  public String execute(String[] args) throws Exception {

    String[] actualArgs = findActualArgs(args);

    if (actualArgs[0].toUpperCase().equals("-R"))
      recurse = true;
    else if (actualArgs[0].equals(" "))
      recurse = false;
    else
      throw new InvalidArgsException();

    regex = actualArgs[1];
    pathObject = actualArgs[2];
    String[] pathObjectArray = pathObject.split("\\s+");
    if (pathObjectArray.length == 3 && (pathObjectArray[1].equals(">>")
        || pathObjectArray[1].equals(">"))) {
      pathObject = pathObjectArray[0];
      new Echo(this.shellData).execute(new String[] {"\"" + recurse(pathObject)
          + "\" " + pathObjectArray[1] + " " + pathObjectArray[2]});
    } else
      return recurse(pathObject);
    return "";
  }// end method execute

  public String recurse(String cmd)
      throws NullPathException, InvalidTypeException {
    // Since the name inputted is a path, split the name on every slash
    String[] pathArray = Directory.convertStringToPathArray(cmd);

    // Get the parent of the directory to be created
    PathObject po = Directory.convertPathArrayToObject(pathArray, shellData);
    if (!(po instanceof File)) {
      if (recurse)
        return recursiveExecute((Directory) po, regex);
      else
        throw new InvalidTypeException("file");
    } else
      return grepFile((File) po, regex);
  }

  /**
   * Returns actual arguments [-R], STRING, PathObject based on user input.
   *
   * @param args The String array of arguments entered by the user.
   * @return Return String array split into arguments of the "grep" command.
   */
  private String[] findActualArgs(String[] args) throws InvalidArgsException {
    // the three parts of the arguments (string, carats, name of output file)
    String arg = args[0];
    String[] actualArgs = new String[] {"", "", ""};
    String str = "";
    try {
      str =
          args[0].substring(args[0].indexOf('"') + 1, args[0].lastIndexOf('"'));
    } catch (StringIndexOutOfBoundsException e) {
      throw new InvalidArgsException();
    }
    if (arg.startsWith("\"" + str + "\""))
      actualArgs[0] = " ";
    else
      actualArgs[0] =
          arg.substring(0, arg.indexOf("\"" + str + "\"") - 1).trim();
    actualArgs[1] = str;
    actualArgs[2] =
        arg.substring(arg.indexOf("\"" + str + "\"") + str.length() + 3);
    return actualArgs;

  }// end method findActualArgs

  /**
   * Returns a String containing all the lines in all of the files of a given
   * Directory that contain a given regex.
   *
   * @param d The directory under inspection.
   * @param regex The regex being looked for.
   * @return Returns a string containing all the lines in all the files of d
   *         that contain regex.
   */
  private String recursiveExecute(Directory d, String regex) {
    String output = "";

    for (PathObject subd : d.getSubObjects()) {
      if (subd instanceof Directory)
        output += recursiveExecute((Directory) subd, regex);
      else
        output += subd.toString() + ":\n" + grepFile((File) subd, regex) + "\n";
    }
    return output;

  }// end method recursiveExecute

  /**
   * /#: mkdir dir /#: echo "a\na b by" > dir/a /#: echo "abby\nm" > dir/b /#:
   * grep -R "a" /dir
   */
  /**
   * Returns a String containing all the lines of a given File that contain a
   * given regex.
   *
   * @param f The file under inspection.
   * @param regex The regex being looked for.
   * @return Returns a string containing all the lines in f that contain regex.
   */
  private String grepFile(File f, String regex) {
    String content = f.getContent();
    String[] contentLines = content.replace("\\n", "\n").split("\\n+");
    String output = "";

    for (String s : contentLines)
      if (s.contains(regex))
        output += s + "\n";

    return output;
  }// end method grepFile

  /**
   * Returns true if the number of arguments is valid for this command.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns true if the arguments size is correct and false otherwise.
   */
  @Override
  public boolean correctArgSize(String[] args) {
    return (args.length == 2 || args.length == 3);
  }// end methd correctArgSize

}// end class Grep
