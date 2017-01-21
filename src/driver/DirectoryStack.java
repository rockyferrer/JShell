package driver;

/**
 * Created by Abhishek on 26/02/16.
 * 
 * The DirectoryStack class is a Stack that stores Directories using the
 * implementation style of a linked list and nodes.
 */
public class DirectoryStack {

  // the node at the top of the stack
  private Node top;

  /**
   * Returns the top Node of the directory stack.
   *
   * @return the top Node of the directory stack
   */
  public Directory getTop() {
    return top.getDirectory();
  }// end method getTop

  /**
   * Creates a DirectoryStack with no directories, the top is therefore null and
   * the size is 0.
   */
  public DirectoryStack() {
    top = null;
  }// end constructor

  /**
   * Pushes a Directory onto the DirectoryStack.
   *
   * @param dir The directory that needs to be added to the stack.
   */
  public void push(Directory dir) {
    Node node = new Node(dir, null);
    if (top != null) {
      node.setNode(top);
      top = node;
    } else
      top = node;
  }// end method push

  /**
   * Pops a Directory onto the DirectoryStack.
   *
   * @return Directory The popped Directory.
   */
  public Directory pop() {
    if (!this.isEmpty()) {
      Node node = top;
      top = node.getNode();
      return node.getDirectory();
    }
    return null;
  }// end method pop

  /**
   * Checks if the DirectoryStack is empty.
   *
   * @return boolean Whether the DirectoryStack is empty.
   */
  public boolean isEmpty() {
    return top == null;
  }// end method isEmpty

}// end class DirectoryStack


/**
 * Created by Abhishek on 26/02/16.
 * 
 * The Node class represents a node that links one directory to another on the
 * DirectoryStack.
 */
class Node {

  // the directory that is associated with a particular node
  private Directory directory;
  // the linking node
  private Node node;

  /**
   * Creates a Node with no next/linking node and no directory as content.
   */
  public Node() {
    node = null;
    directory = null;
  }// end default constructor

  /**
   * Creates a node with the linking node as n and content as directory.
   *
   * @param dir The reference to the directory (content of this node).
   * @param n The reference to the next/linking node.
   */
  public Node(Directory dir, Node n) {
    directory = dir;
    node = n;
  }// end constructor

  /**
   * Sets the next/linking node to n.
   *
   * @param n The reference to the next/linking node.
   */
  public void setNode(Node n) {
    node = n;
  }// end method setNode

  /**
   * Sets the content of this node to directory.
   *
   * @param dir The reference to the directory (content of this node).
   */
  public void setDirectory(Directory dir) {
    directory = dir;
  }// end method setDirectory

  /**
   * Gets the next/linking node to n.
   * 
   * @return node The reference to the next/linking node.
   */
  public Node getNode() {
    return node;
  }// end method getNode

  /**
   * Sets the content of this node to directory.
   *
   * @return directory The reference to the directory (content of this node).
   */
  public Directory getDirectory() {
    return directory;
  }// end method getDirectory

}// end class Node
