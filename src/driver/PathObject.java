package driver;

/**
 * Created by Felipe on 20/02/16.
 * 
 * The PathObject is an abstract class that has the subclasses File and
 * Directory. One can create it, set/get its name, and get its parent.
 */
public class PathObject {

  protected String name;
  protected Directory parent;

  /**
   * Create a PathObject with a specified name and no parent.
   *
   * @param name Name of the PathObject to be created
   */
  public PathObject(String name) {
    this.name = name;
    this.parent = null;
  }// end constructor

  /**
   * Change the name of the PathObject.
   *
   * @param n New name of the PathObject
   */
  public void setName(String n) {
    this.name = n;
  }// end method setName

  /**
   * Return the parent of the PathObject.
   *
   * @return Return the parent of the PathObject
   */
  public Directory getParent() {
    return this.parent;
  }// end method getParent

  /**
   * Return the name of the PathObject
   *
   * @return Return the name of the PathObject
   */
  public String getName() {
    return name;
  }// end method getName

  /**
   * Returns a String representation of a PathObject.
   *
   * @return Return a string that represents the object path.
   */
  @Override
  public String toString() {
    if (parent == null) {
      return name;
    } else if (parent.parent == null) {
      return parent.getName() + name;
    } else {
      return parent.toString() + "/" + name;
    }
  }// end method toString

}// end class PathObject
