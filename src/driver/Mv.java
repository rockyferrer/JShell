package driver;


import driver.Exceptions.InternalPathException;
import driver.Exceptions.PathObjectExistsException;

/**
 * Created by Sajjad on 19/03/16.
 * 
 * The Mv class handles the "mv" command, which moves an item from OLDPATH to
 * NEWPATH.
 */
public class Mv extends PathObjectCreator {

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public Mv(ShellData sd) {
    super(sd);
  }// end constructor

  /**
   * Executes the "mv" command, which moves an item from OLDPATH to NEWPATH.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns a String that will be printed in JShell.
   */
  public String execute(String[] args) throws Exception {

    checkArgSizeValidity(args);

    String[] oldPOArray = Directory.convertStringToPathArray(args[0]);
    PathObject oldPO =
        Directory.convertPathArrayToObject(oldPOArray, shellData);
    Directory parent = oldPO.getParent();

    String[] newPOArray = Directory.convertStringToPathArray(args[1]);
    Directory newPOParent = getParent(newPOArray);

    if (parentIsInternal(newPOParent, oldPO))
      throw new InternalPathException(args[1], args[0]);

    // The index of the name of the directory to be made
    int n = newPOArray.length - 1;
    checkValidName(newPOArray[n]);

    if (!objectExists(newPOArray[n], newPOParent)) {
      parent.removeSubObject(oldPO);
      oldPO.setName(newPOArray[n]);
      newPOParent.addSubObject(oldPO);
    } else
      throw new PathObjectExistsException();

    return "";
  }// end method execute

  /**
   * Returns true if a given PathObject is inside a given Directory and false
   * otherwise.
   *
   * @param parent The Directory being checked inside.
   * @param po The PathObject being checked with.
   * @return Returns true if po is inside parent and false otherwise.
   */
  private boolean parentIsInternal(Directory parent, PathObject po) {
    while (parent != null) {
      if (po.equals(parent))
        return true;
      parent = parent.getParent();
    }
    return false;
  }// end method parentIsInternal

  /**
   * Returns true if the number of arguments is valid for this command.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns true if the arguments size is correct and false otherwise.
   */
  public boolean correctArgSize(String[] args) {
    return args.length == 2;
  }// end method correctArgSize

}// end class Mv
