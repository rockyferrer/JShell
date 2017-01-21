package driver;

import java.util.ArrayList;

/**
 * Created by Felipe on 24/03/16.
 * 
 * The ShellData class handles the implementation of a Singleton pattern.
 */
public class ShellData {

  // the directory
  private Directory currentDirectory;
  // the history
  private ArrayList<String> history;
  // the directory stack
  private DirectoryStack directoryStack;

  // the home directory starts off as null
  private static Directory homeDirectory = null;

  /**
   * Makes a new directory with the same reference to implement a Singleton
   * pattern.
   */
  public ShellData() {
    if (homeDirectory == null)
      homeDirectory = new Directory("/");
    currentDirectory = homeDirectory;
    history = new ArrayList<>();
    directoryStack = new DirectoryStack();
  }// end constructor

  /**
   * Returns the directory stack. Used for testing.
   *
   * @return the directory stack
   */
  public DirectoryStack getDirectoryStack() {
    return directoryStack;
  }// end method getDirectoryStack

  /**
   * Sets the directory stack. Used for testing.
   *
   * @param directoryStack the directory stack
   */
  public void setDirectoryStack(DirectoryStack directoryStack) {
    this.directoryStack = directoryStack;
  }// end method setDirectoryStack


  /**
   * Change the current directory.
   *
   * @param dir New directory for the current directory to be set as.
   */
  public void setCurrentDirectory(Directory dir) {
    currentDirectory = dir;
  }// end method setCurrentDirectory

  /**
   * Return the current directory.
   *
   * @return Return the current directory.
   */
  public Directory getCurrentDirectory() {
    return currentDirectory;
  }// end method getCurrentDirectory

  /**
   * Returns the home directory.
   *
   * @return the home directory
   */
  public Directory getHomeDirectory() {
    return homeDirectory;
  }// end getter for home directory

  /**
   * Resets the directory system.
   */
  public void resetDirectorySystem() {
    homeDirectory = new Directory("/");
    currentDirectory = homeDirectory;
  }// end setter for home directory1

  /**
   * Returns the history array list in its current state.
   *
   * @return history The current history.
   */
  public ArrayList<String> getHistory() {
    return history;
  }// end method getHistory

  /**
   * Sets the history array list. Used for tearDown method in testing.
   *
   * @param history The current history.
   */
  public void setHistory(ArrayList<String> history) {
    this.history = history;
  }// end method setHistory

  /**
   * Add a command to the history array list.
   *
   * @param command The command entered by the user.
   */
  public void addCommandToHistory(String command) {
    history.add(command);
  }// end method addCommand

}// end class ShellData
