package driver;

import driver.Exceptions.InvalidArgsException;
import driver.Exceptions.InvalidNumberOfArgsException;
import driver.Exceptions.InvalidTypeException;

/**
 * Created by Abhishek on 01/03/16.
 * 
 * The Echo class handles the "echo" command. It takes in a string and,
 * optionally, a output file name, to replace (>) or to append (>>) the string
 * to.
 */
public class Echo extends PathObjectCreator {

  // true if ">>" and false if ">" or ""
  boolean append;
  // true if there file argument exists
  String strToWrite;
  // the name of the output file
  String outfileName;

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public Echo(ShellData sd) {
    super(sd);
  }// end constructor

  /**
   * Initialize the variables.
   */
  private void initializeVariables() {
    append = false;
    strToWrite = "";
    outfileName = "";
  }// end method initializeVariables

  /**
   * Executes the "echo" command, which outputs a string if that is the only
   * argument, or appends or replaces the content of an output file, based on
   * the args.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns a String that will be printed in JShell.
   */
  public String execute(String[] args) throws Exception {

    // every time execute is called, reset the variables
    initializeVariables();

    // set the variables based on the arguments
    String[] actualArgs = findActualArgs(args);
    setVars(actualArgs);

    // if the output file exists, write to the file
    if (!outfileName.equals("") && !strToWrite.equals(""))
      writeToFile(append);
    // otherwise, print in JShell
    else
      return strToWrite;

    return "";
  }// end method execute

  /**
   * Returns actual arguments STRING, >> or >, and OUTFILE based on user input.
   *
   * @param args The String array of arguments entered by the user.
   * @return Return String array split into arguments of the "echo" command.
   */
  private String[] findActualArgs(String[] args)
      throws InvalidArgsException, InvalidNumberOfArgsException {
    // the three parts of the arguments (string, carats, name of output file)
    String[] actualArgs = new String[] {"", "", ""};
    String str = "";
    try {
      str =
          args[0].substring(args[0].indexOf('"') + 1, args[0].lastIndexOf('"'));
    } catch (StringIndexOutOfBoundsException e) {
      throw new InvalidArgsException();
    }
    actualArgs[0] = "\"" + str + "\"";
    String[] rest = args[0].trim()
        .substring(args[0].indexOf(actualArgs[0]) + actualArgs[0].length())
        .trim().split(" +");

    if (rest.length == 0 || args[0].equals("\"\""))
      rest = new String[] {"", ""};

    actualArgs[1] = rest[0];
    if (rest.length == 2)
      actualArgs[2] = rest[1];
    else if (rest.length > 2)
      throw new InvalidNumberOfArgsException();

    return actualArgs;

  }// end method findActualArgs

  /**
   * Sets the global variables so that the "echo" command can be processed
   * accordingly in the execute method.
   *
   * @param actualArgs String array split into arguments of the "echo" command.
   */
  private void setVars(String[] actualArgs) throws InvalidArgsException {

    // set append
    if (actualArgs[1].equals(">>"))
      append = true;
    else if (actualArgs[1].equals(">"))
      append = false;
    else if (!actualArgs[1].equals(""))
      throw new InvalidArgsException();

    // set outfileExists or outfileName
    outfileName = actualArgs[2];
    // set strToWrite (remove the quotation marks)
    strToWrite = actualArgs[0];

    strToWrite = strToWrite.substring(1, strToWrite.length() - 1);

    String temp = strToWrite.replace("\\n", "").replace("\\r", "");

    if (!temp.equals(""))
      strToWrite += "\n";

  }// end method setVars

  /**
   * Append/Replace contents of the file with name, outfileName, to strToWrite
   * based on whether the boolean append is true or false.
   *
   * @param append The boolean that decides whether the contents of the file
   *        will be replaced or appended to.
   * @return The empty string that will be displayed in the shell.
   */
  private void writeToFile(boolean append) throws Exception {
    // The index of the name of the directory to be made

    // Since the name inputted is a path, split the name on every slash
    String[] pathArray = Directory.convertStringToPathArray(outfileName);
    int n = pathArray.length - 1;
    checkValidName(pathArray[n]);

    // Convert the path to a PathObject
    Directory parent = getParent(pathArray);

    // create a new file with the specified name
    File f = new File(pathArray[n]);

    // check if the file already exists
    if (!objectExists(pathArray[n], parent)) {
      checkValidName(f.getName());

      // Set the content of the file
      f.setContent(strToWrite);

      // add file as a sub-object of parent
      parent.addSubObject(f);
    } else {
      writeToExistingFile(parent, f);
    }
  }// end method writeToFile

  // writes new content to existing file
  private void writeToExistingFile(Directory parent, File f)
      throws InvalidTypeException {
    // iterate through sub-objects of parent
    for (PathObject po : parent.getSubObjects()) {

      // find file in the subobjects
      if (f.getName().equals(po.getName())) {
        // check if file and not directory
        if (!(po instanceof File))
          throw new InvalidTypeException("file");
        f = (File) po;

        // modify content of file based on whether to append or not
        if (append)
          f.setContent(f.getContent() + strToWrite);
        else
          f.setContent(strToWrite);
      }
    }
  }// end method writeToExistingFile

  /**
   * Returns true by default. Argument size checked in other methods.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns true if the arguments size is correct and false otherwise.
   */
  public boolean correctArgSize(String[] args) {
    return true;
  }// end method correctArgSize

}// end class Echo
