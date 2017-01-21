// **********************************************************
// Assignment2:
// Student1:
// CDF user_name: c5ferrei
// UT Student #: 1002116505
// Author: Felipe Ferreira Santos
//
// Student2:
// CDF user_name: c5moturu
// UT Student #: 1001019925
// Author: Abhishek Sai Moturu
//
// Student3:
// CDF user_name: c4ferrer
// UT Student #: 1000511047
// Author: Rocky Ferrer
//
// Student4:
// CDF user_name: c5nezhae
// UT Student #: 1002276404
// Author: Seyed Sajjad Nezhadi
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC 207 and understand the consequences.
// *********************************************************
package driver;

import java.util.Scanner;

/**
 * The JShell class runs the whole program required by Assignment 2. The user
 * can interact with the shell by entering commands and viewing output.
 */
public class JShell {

  /**
   * Runs the entire program.
   *
   * @param args These are the main method arguments.
   */
  public static void main(String[] args) {

    // Scanner to get user input
    Scanner in = new Scanner(System.in);
    String command = "";
    ShellData shellData = new ShellData();
    JInterpreter interpreter = new JInterpreter(shellData);

    do {
      // user prompt
      System.out.print("/#: ");

      // get rid of trailing whitespaces after getting input
      command = in.nextLine().trim();

      // keep track of commands in history
      shellData.getHistory().add(command);

      String execution = interpreter.executeCommand(command);
      execution = execution.replace("\\n", "\n").replace("\\t", "\t");

      if (!command.equals("exit"))
        System.out.print(execution);

    } while (!command.equals("exit"));
    // do-while loop until the exit command is entered by user

    in.close(); // close the Scanner

  }// end main function

}// end class JShell
