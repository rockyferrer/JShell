package driver;

import java.util.Arrays;
import java.util.Hashtable;

/**
 * Created by Felipe on 27/02/16.
 * 
 * The Interpreter class handles the execution of the commands. It calls the
 * execute method of a command and also handles errors.
 */
public class JInterpreter {

  // the hashtable of commands and their respective classes
  private Hashtable<String, String> commands = new Hashtable<>();
  private ShellData shellData;

  /**
   * Place the commands into a hashtable with their respective classes.
   */
  public JInterpreter(ShellData sd) {
    commands.put("mkdir", "driver.MkDir");
    commands.put("cd", "driver.Cd");
    commands.put("ls", "driver.Ls");
    commands.put("pwd", "driver.Pwd");
    commands.put("cat", "driver.Cat");
    commands.put("popd", "driver.Popd");
    commands.put("pushd", "driver.Pushd");
    commands.put("echo", "driver.Echo");
    commands.put("man", "driver.Man");
    commands.put("pwd", "driver.Pwd");
    commands.put("history", "driver.History");
    commands.put("grep", "driver.Grep");
    commands.put("mv", "driver.Mv");
    commands.put("cp", "driver.Cp");
    commands.put("get", "driver.Get");
    commands.put("!number", "driver.!number");
    shellData = sd;
  }// end constructor

  /**
   * Executes a certain command and returns a string to print in JShell.
   *
   * @param cmdString The command that was entered in JShell.
   * @return str The String that will be printed in JShell.
   */
  public String executeCommand(String cmdString) {

    if (cmdString.length() == 0)
      return "";

    String[] command = null;
    if (cmdString.substring(0, 1).equals("!")){
      if (cmdString.split(" +").length == 1)
        command = new String[] {"history", cmdString.trim()};
      else if (cmdString.split(" +").length == 3) {
        command = new String[] {"history", cmdString.split(" +")[0].trim(),
            cmdString.split(" +")[1].trim(), cmdString.split(" +")[2].trim()};
      }
    }
    else if (cmdString.length() > 4 && cmdString.substring(0, 4).equals("echo"))
      command = new String[] {cmdString.substring(0, 4),
          cmdString.substring(4).trim()};
    else if (cmdString.length() > 4 && cmdString.substring(0, 4).equals("grep"))
      command = new String[] {cmdString.substring(0, 4),
          cmdString.substring(4).trim()};
    else
      command = cmdString.split(" +");

    String[] args = Arrays.copyOfRange(command, 1, command.length);

    // call the execute method in the class of the entered command
    try {
      @SuppressWarnings("rawtypes")
      Class c = Class.forName(commands.get(command[0]));
      @SuppressWarnings("unchecked")
      JCommand cmd = (JCommand) c.getDeclaredConstructor(ShellData.class)
          .newInstance(shellData);
      // consider the case when the user enters > OUTFILE or
      // >> OUTFILE with some command (other than exit)
      String[] echoArgs = null;
      Echo echo = null;
      // if the command has > or >> at the second
      // last position and it is not echo, then
      // execute the echo command with the outfile
      // if any output is returned
      if (args.length >= 2
          && (args[args.length - 2].equals(">")
              || args[args.length - 2].equals(">>"))
          && !command[0].equals("echo")) {
        echo = new Echo(shellData);
        // the last two arguments (> or >> and outfile)
        // are not needed for the execution of cmd
        String[] cmdArgs = Arrays.copyOfRange(args, 0, args.length - 2);
        String output = cmd.execute(cmdArgs);
        if (output != null && !output.equals(""))
          echoArgs = new String[] {"\"" + output + "\" " + args[args.length - 2]
              + " " + args[args.length - 1]};
      }
      // execute echo if the output is not empty, otherwise return the output
      if (echoArgs != null)
        echo.execute(echoArgs);
      else
        return cmd.execute(args);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      return "Invalid number entered\n";
    } catch (NullPointerException e) {
      return "Command does not exist\n";
    } catch (ArrayIndexOutOfBoundsException e) {
      return "Invalid command, please try again\n";
    } catch (StringIndexOutOfBoundsException e) {
      return "Invalid command, please try again\n";
    } catch (Exception e) {
      return e.getMessage();
    }
    return "";
  }// end executeCommand

}// end class JInterpreter
