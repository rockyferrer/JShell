package driver;

import driver.Exceptions.PathObjectExistsException;

/**
 * Created by Rocky on 20/02/16.
 * 
 * The Mkdir class handles the "mkdir" command. It creates directories, each of
 * which may be relative to the current directory or may be a full path.
 */
public class MkDir extends PathObjectCreator {

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public MkDir(ShellData sd) {
    super(sd);
  }// end constructor

  /**
   * Executes the mkdir command, which creates a new directory with the name
   * passed in by the user. The input can be a single directory, multiple
   * directories, or a relative path.
   *
   * @param args the command passed in by the user
   * @return returns the required string
   */
  public String execute(String[] args) throws Exception {

    checkArgSizeValidity(args);

    for (String name : args) {
      // Since the name inputted is a path, split the name on every slash
      String[] pathArray = Directory.convertStringToPathArray(name);

      // The index of the name of the directory to be made
      int n = pathArray.length - 1;
      checkValidName(pathArray[n]);

      // Get the parent of the directory to be created
      Directory parent = getParent(pathArray);
      // Instantiate the directory to be created
      Directory d = new Directory(pathArray[n]);

      // Check that this directory does not already exist
      if (!objectExists(pathArray[n], parent))
        parent.addSubObject(d);
      else
        throw new PathObjectExistsException();
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
    return args.length >= 1;
  }// end method correctArgSize

}// end class MkDir
