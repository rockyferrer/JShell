package driver;

import java.util.Arrays;

import driver.Exceptions.InvalidPathObjectNameException;
import driver.Exceptions.InvalidTypeException;
import driver.Exceptions.NullPathException;

/**
 * Created by Felipe on 05/03/16.
 * 
 * The PathObjectCreator class handles the creation of PathObjects and checks
 * for valid names, gets parent directories, and validates existence.
 */
public abstract class PathObjectCreator extends JCommand {

  // array of all possible characters that cannot be part of a directory's name
  protected static final char[] INVALID_CHARS =
      {'!', '@', '$', '&', '*', '(', ')', '?', ':', '[', ']', '\"', '<', '>',
          '`', '|', '=', '{', '}', '\\', '/', ',', ';', '\''};

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public PathObjectCreator(ShellData sd) {
    super(sd);
  }// end constructor

  /**
   * Checks if the PathObject has any invalid characters in the name, as found
   * in INVALID_CHARS.
   *
   * @param pathObjectName The name of the PathObject that is checked.
   * @return False if the name of the PathObject contains invalid characters,
   *         and true otherwise.
   */
  private boolean containsValidCharacters(String pathObjectName) {

    for (char invalidChar : INVALID_CHARS) {
      if (pathObjectName.contains(String.valueOf(invalidChar)))
        return false;
    }

    return true;
  }// end method containsValidCharacters

  /**
   * Throws an InvalidPathObjectName Exception if the name contains invalid
   * characters, ., or .., which are reserved.
   *
   * @param s The name of the PathObject that is being checked.
   */
  protected void checkValidName(String s)
      throws InvalidPathObjectNameException {
    if (!containsValidCharacters(s) || s.equals(".") || s.equals(".."))
      throw new InvalidPathObjectNameException();
  }// end method checkValidName

  /**
   * Returns the parent Directory.
   *
   * @param path The path of the parent.
   * @return The parent Directory.
   */
  protected Directory getParent(String[] path)
      throws NullPathException, InvalidTypeException {
    PathObject parent;
    String[] parentPath = Arrays.copyOf(path, path.length - 1);

    if (path.length > 0)
      parent = Directory.convertPathArrayToObject(parentPath, shellData);
    else
      parent = shellData.getCurrentDirectory();

    if (!(parent instanceof Directory))
      throw new InvalidTypeException("directory");
    parent.getName();

    return (Directory) parent;
  }// end method getParent

  /**
   * Returns true if the PathObject p exists in the parent Directory, and false
   * otherwise.
   *
   * @param name The name of the PathObject that we are checking the existence
   *        of.
   * @param parent The parent Directory that we are checking inside.
   * @return True if the object p exists in the parent Directory, and false
   *         otherwise.
   */
  protected boolean objectExists(String name, Directory parent) {
    for (PathObject po : parent.getSubObjects())
      if (po.getName().equals(name))
        return true;
    return false;
  }// end method objectExists

}// end class PathObjectCreator
