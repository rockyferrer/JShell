package driver;

import java.util.LinkedList;

import driver.Exceptions.InvalidTypeException;
import driver.Exceptions.NullPathException;

/**
 * Created by Felipe on 20/02/16.
 *
 * The Directory class is a Path Object that handles changes to directories, and
 * is used by the JCommand classes to change directories or access the
 * properties of a certain directory.
 */
public class Directory extends driver.PathObject {

  // Linked list used to store the subObjects (subDirectories and subFiles)
  private LinkedList<PathObject> subObjects;

  /**
   * Creates a Directory with a specified name, no parent and no sub objects.
   *
   * @param name Name of the directory to be created.
   */
  public Directory(String name) {
    super(name);
    this.subObjects = new LinkedList<>();
  }// end constructor

  /**
   * Return a LinkedList of PathObjects that corresponds to the sub objects of
   * the Directory.
   *
   * @return Return the LinkedList of PathObjects that corresponds to the sub
   *         objects of the Directory.
   */
  public LinkedList<PathObject> getSubObjects() {
    return subObjects;
  }// end method getSubObjects

  /**
   * Add a sub object to the Directory.
   *
   * @param o sub object to be added to the Directory.
   */
  public void addSubObject(PathObject o) {
    o.parent = this;
    subObjects.add(o);
  }// end method addSubObject

  /**
   * Remove a sub object from the Directory.
   *
   * @param o sub object to be removed from the Directory.
   */
  public void removeSubObject(PathObject o) {
    o.parent = null;
    subObjects.remove(o);
  }// end method removeSubObject

  public boolean equals(Directory other) {
    return this.toString().equals(other.toString());
  }

  /**
   * Take a path in the form a string and convert it to an array of strings.
   *
   * @param s String to be converted into an array of strings
   * @return returns a Path in the form of an array of strings.
   */
  public static String[] convertStringToPathArray(String s) {
    String[] pathArray;
    String[] splitS = s.split("/+");

    // check if it is a full path
    if (s.charAt(0) == '/') {

      // check if path is only slashes, in this case the path is the home
      // directory
      if (splitS.length == 0)
        return new String[] {"/"};

      // if path is not only slashes, prepend "/" to splitS and return that
      // array
      pathArray = new String[splitS.length];
      pathArray[0] = "/";

      // skip splitS[0], because it will always be null if strings starts with
      // '/'
      for (int i = 1; i < splitS.length; i++)
        pathArray[i] = splitS[i];
    }

    // if not a full path just return the split string
    else
      pathArray = splitS;

    return pathArray;
  }// end method convertStringToPathArray

  /**
   * Take a Path in the form of an array of Strings and convert it into an
   * instance of a PathObject.
   *
   * @param pathArray Array of strings to be converted to a PathObject.
   * @return Return the PathObject represented by pathArray.
   */
  public static PathObject convertPathArrayToObject(String[] pathArray,
      ShellData sd) throws NullPathException, InvalidTypeException {

    if (pathArray.length == 0)
      return sd.getCurrentDirectory();
    else if (pathArray[0].equals(sd.getHomeDirectory().name))
      return getDirectoryFromPath(pathArray, 1, sd.getHomeDirectory(), sd);
    else
      return getDirectoryFromPath(pathArray, 0, sd.getCurrentDirectory(), sd);

  }// end method convertPathStringToObject

  /**
   * Take a path in the form of an array of Strings, an integer (recursive
   * counter), and a Directory and recursively return the Directory at the path.
   *
   * Throws a NullPathException if path does not exist.
   *
   * @param pathArray Array of strings to be converted to a PathObject.
   * @param n The depth of the directory in the path.
   * @param d The directory at depth n-1.
   * @return Return the PathObject (Directory) represented by pathArray.
   */
  private static PathObject getDirectoryFromPath(String[] pathArray, int n,
      Directory d, ShellData sd)
          throws NullPathException, InvalidTypeException {

    if (n >= pathArray.length) // base case
      return d;

    String objectName = pathArray[n];
    // in this case, go to parent
    if (objectName.equals("..")) {
      if (d.equals(sd.getHomeDirectory()))// check if parent exists
        throw new NullPathException();

      // recurse using parent as new directory and n+1 as new index
      return getDirectoryFromPath(pathArray, n + 1, d.parent, sd);
    }

    // in this case stay in current directory
    else if (objectName.equals("."))
      return getDirectoryFromPath(pathArray, n + 1, d, sd);

    else
      return checkSubObjects(pathArray, n, d, sd);

  }// end method getDirectoryFromPath

  /**
   * A helper method of the getDirectoryFromPath method. This method checks the
   * sub-objects of the directory at depth n in the pathArray and recursively
   * returns the Directory at the path described by pathArray.
   *
   * @param pathArray Array of strings to be converted to a PathObject.
   * @param n The depth of the directory in the path.
   * @param d The directory at depth n-1.
   * @return Return the PathObject (Directory) represented by pathArray.
   */
  private static PathObject checkSubObjects(String[] pathArray, int n,
      Directory d, ShellData sd)
          throws NullPathException, InvalidTypeException {
    String objectName = pathArray[n];
    // loop through d's sub-objects to see if there exists an object with name
    // objectName
    for (int i = 0; i < d.subObjects.size(); i++) {
      if (objectName.equals(d.subObjects.get(i).name)) {
        if (n < pathArray.length - 1) {
          // check if object is directory so it can be recursed on
          if (!(d.subObjects.get(i) instanceof Directory))
            throw new InvalidTypeException("directory");

          // recurse on the directory found
          return getDirectoryFromPath(pathArray, n + 1,
              (Directory) d.subObjects.get(i), sd);
        } else
          // Second base case where it is at the final object in the PATH.
          return d.subObjects.get(i);
      }
    }
    // if no object is found in path throw a null path exception
    throw new NullPathException();
  }// end method checkSubObjects

}// end class Directory
