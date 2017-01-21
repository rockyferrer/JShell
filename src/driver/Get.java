package driver;

import driver.Exceptions.InvalidURLException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Rocky on 03/24/16.
 * 
 * The Get class handles the "get" command. It retrieves the file (txt or html)
 * at a certain URL and adds it to the current working dictionary.
 */
public class Get extends JCommand {

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public Get(ShellData sd) {
    super(sd);
  }// end constructor

  /**
   * Executes the "get" command, which copies an item from OLDPATH to NEWPATH.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns a String that will be printed in JShell.
   */
  @Override
  public String execute(String[] args) throws Exception {
    checkArgSizeValidity(args);
    try {


      // the file that has the contents we want
      URL url = new URL(args[0]);
      String link = url.toString();

      // the length of the link
      int linkLength = link.length();

      // checks if the link contains a .txt or a .html file
      if (!link.substring(linkLength - 4, linkLength).equals(".txt")
          && !link.substring(linkLength - 5, linkLength).equals(".html")) {
        throw new InvalidURLException();
      }

      // to read the contents of the file
      BufferedReader br =
          new BufferedReader(new InputStreamReader(url.openStream()));

      // create a variable to hold the contents of the file
      StringBuilder contents = new StringBuilder();

      // add the contents of the file to contents
      String line;
      while ((line = br.readLine()) != null)
        contents.append(line + "\n");

      // close the BufferedReader
      br.close();

      // use echo to add the contents to a file in the current working directory
      Echo echo = new Echo(shellData);

      // arguments for echo
      String[] echoArgs = new String[] {"\"" + contents.toString() + "\""
          + " > " + link.substring(link.lastIndexOf("/") + 1, link.length())};

      return echo.execute(echoArgs);

    } catch (MalformedURLException e) {
      // the MalformedURLException is from the java URL API, but we want to
      // throw our own exception when an invalid URL is passed in
      throw new InvalidURLException();
    }
  }// end method execute

  /**
   * Returns true if the number of arguments is valid for this command.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns true if the arguments size is correct and false otherwise.
   */
  @Override
  public boolean correctArgSize(String[] args) {
    return args.length == 1;
  }// end method correctArgSize

}// end class Get
