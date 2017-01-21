package driver;

import driver.Exceptions.InvalidTypeException;
import driver.Exceptions.NullPathException;

/**
 * Created by Abhishek on 22/02/16.
 * 
 * The Cat class handles the "cat" command. It takes in file names or path names
 * as the arguments and returns the content of the file(s).
 */
public class Cat extends JCommand {

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public Cat(ShellData sd) {
    super(sd);
  }// end constructor

  /**
   * Executes the "cat" command, which outputs the content of the file(s) based
   * on the file/path name(s) entered by the user.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns a String that will be printed in JShell.
   */
  public String execute(String[] args) throws Exception {

    // the number of arguments is the number of files
    checkArgSizeValidity(args);
    String content = "";
    String[] pathArray = null;
    for (String e : args) {

      if (args.length > 1)
        content += e + ":\n";

      // for each file, convert the path string
      // to a file object and append its contents
      pathArray = Directory.convertStringToPathArray(e);
      PathObject po = null;
      try {
        po = Directory.convertPathArrayToObject(pathArray, shellData);
        // if path object is not a file, then add "Path not a file" to content
        if (!(po instanceof File))
          content += new InvalidTypeException("file").getMessage();
        // else, add the contents of the file to content
        else {
          File file = (File) po;
          content += file.getContent();
        }
      } catch (NullPathException npe) {
        // if the file does not exists, add "Path does not exists" to content
        content += new NullPathException().getMessage();
      }

      // file contents are separated by three line breaks
      if (!args[args.length - 1].equals(e))
        content += "\n";
    }

    return content;
  }// end method execute

  /**
   * Returns true if the number of arguments is valid for this command.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns true if the arguments size is correct and false otherwise.
   */
  public boolean correctArgSize(String[] args) {
    return (args.length >= 1 || args.length >= 3);
  }// end method correctArgSize

}// end class Cat
