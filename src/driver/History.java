package driver;

import java.util.ArrayList;

import driver.Exceptions.InvalidArgsException;
import driver.Exceptions.InvalidNumberOfArgsException;

/**
 * Created by Rocky on 03/04/16, based on code by Abhishek written in JShell.
 * 
 * The History class handles the "history" command. It prints out all or the
 * [number] most recent commands with numbering.
 */
public class History extends JCommand {

  // the array list that contains all the history
  private static ArrayList<String> history = new ArrayList<>();
  private static JInterpreter ji;

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public History(ShellData sd) {
    super(sd);
    history = sd.getHistory();
    ji = new JInterpreter(sd);
  }// end constructor

  /**
   * Executes the "history" command, which outputs history of the commands that
   * the user has tried so far. With a number argument, it will output a
   * specific amount of recent history, or otherwise, will output the entire
   * history.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns a String that will be printed in JShell.
   */
  public String execute(String[] args)
      throws InvalidNumberOfArgsException, InvalidArgsException {
    checkArgSizeValidity(args);

    int number = 0;
    String content = "";
    // set number to input or length of the history array list
    if (args.length == 1) {
      if (args[0].length() > 0 && args[0].charAt(0) == '!') {
        int index = Integer.parseInt(args[0].substring(1)) - 1;
        if (index < history.size())
          return ji.executeCommand(history.get(index));
        else
          throw new InvalidArgsException();
      } else if (args[0].length() > 0 && args[0].charAt(0) != '!')
        number = Integer.parseInt(args[0]);
    } else
      number = history.size();
    // append content with the numbered history lines
    for (int i = history.size() - number; i < history.size(); i++)
      content += (i + 1) + ". " + history.get(i) + "\n";
    return content;
  }// end method execute

  /**
   * Returns true if the number of arguments is valid for this command.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns true if the arguments size is correct and false otherwise.
   */
  public boolean correctArgSize(String[] args) {
    return (args.length >= 0 && args.length <= 1);
  }// end method correctArgSize

}// end class History
