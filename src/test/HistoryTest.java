package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.History;
import driver.JInterpreter;
import driver.ShellData;

/**
 * Created by Rocky on 06/03/16.
 *
 * The HistoryTest class contains the tests for the History class.
 */
public class HistoryTest {

  // instance variable for history so that we can test the commands
  private History history;
  private ShellData shellData;

  /**
   * Initialize the variables.
   */
  @Before
  public void setUp() {
    shellData = new ShellData();
    history = new History(shellData);
  }// end method setUp

  /**
   * Test the getter for the history ArrayList.
   */
  @Test
  public void testGetHistory() {
    ArrayList<String> expected = shellData.getHistory();
    assertEquals(expected, shellData.getHistory());
  }// end method testGetHistory

  /**
   * Test the setter for the histoy ArrayList.
   */
  @Test
  public void testSetHistory() {
    shellData.setHistory(new ArrayList<String>());
    ArrayList<String> expected = new ArrayList<String>();
    assertEquals(expected, shellData.getHistory());
  }// end method testSetHistory

  /**
   * Test the addCommand method, which adds commands to the history ArrayList
   * whether they are valid commands or not.
   */
  @Test
  public void testAddCommand() {
    shellData.addCommandToHistory("mkdir dir1");
    shellData.addCommandToHistory("hello");
    String expected1 = shellData.getHistory().get(0);
    String actual1 = "mkdir dir1";
    String expected2 = shellData.getHistory().get(1);
    String actual2 = "hello";
    assertEquals(expected1, actual1);
    assertEquals(expected2, actual2);
  }// end method testAddCommand

  /**
   * Test execute on home when the history command is inputted and no other
   * commands have been inputted yet.
   */
  @Test
  public void testExecuteOnHome() throws Exception {
    shellData.addCommandToHistory("history");
    assertEquals(history.execute(new String[] {}), "1. history\n");
  }// end method testExecuteOnHome

  /**
   * Test execute on several commands that have been inputted by the user.
   */
  @Test
  public void testExecuteOnCommands() throws Exception {
    shellData.addCommandToHistory("mkdir dir1");
    shellData.addCommandToHistory("pwd");
    shellData.addCommandToHistory("cd dir1");
    shellData.addCommandToHistory("ls");
    shellData.addCommandToHistory("InvalidCommandIDK");
    shellData.addCommandToHistory("mkdir dir2");
    shellData.addCommandToHistory("history");
    String expected = "1. mkdir dir1\n2. pwd\n3. cd dir1\n4. ls\n"
        + "5. InvalidCommandIDK\n6. mkdir dir2\n7. history\n";
    String actual = history.execute(new String[] {});
    assertEquals(expected, actual);
  }// end method testExecuteOnCommands

  /**
   * Test execute when a number is passed in as an argument. It should truncate
   * the output to have the amount of previous commands corresponding to the
   * number inputted by the user.
   */
  @Test
  public void testExecuteOnGivenNumber() throws Exception {
    shellData.addCommandToHistory("mkdir dir1");
    shellData.addCommandToHistory("pwd");
    shellData.addCommandToHistory("cd dir1");
    shellData.addCommandToHistory("ls");
    shellData.addCommandToHistory("InvalidCommandIDK");
    shellData.addCommandToHistory("mkdir dir2");
    shellData.addCommandToHistory("history 3");
    String expected = "5. InvalidCommandIDK\n6. mkdir dir2\n7. history 3\n";
    String actual = history.execute(new String[] {"3"});
    assertEquals(expected, actual);
  }// end method testExecuteOnGivenNumber

  /**
   * Test execute when a number that is larger than the amount of previous
   * commands is entered. Should throw an ArrayIndexOutOfBoundsException.
   */
  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testExecuteOnTooLargeNumber() throws Exception {
    history.execute(new String[] {"5"});
  }// end method testExecuteOnTooLargeNumber

  /**
   * Test execute when something other than a number is passed in as an
   * argument. Should throw a NumberFormatException.
   */
  @Test(expected = NumberFormatException.class)
  public void testExecuteOnNotANumber() throws Exception {
    history.execute(new String[] {"awfawd"});
  }// end method testExecuteOnNotANumber

  /**
   * Test the !number command.
   */
  @Test
  public void testExecuteWithExclamation() throws Exception {
    shellData.addCommandToHistory("history");
    shellData.addCommandToHistory("!1");
    assertEquals(history.execute(new String[] {"!1"}), "1. history\n2. !1\n");
  }// end method testExecuteWithExclamation

  /**
   * Test execute with redirection
   */
  @Test
  public void testExecuteWithRedirection() throws Exception {
    shellData.addCommandToHistory("history");
    assertEquals(history.execute(new String[] {}), "1. history\n");
    JInterpreter ji = new JInterpreter(this.shellData);
    assertEquals(ji.executeCommand("history >> file"), "");
    assertEquals(ji.executeCommand("cat file"), ("1. history\n\n"));
  }// end method testExecuteWithRedirection

  /**
   * Test the correctArgSize method. The number of arguments should be exactly
   * equal to 0 or 1.
   */
  @Test
  public void testCorrectArgSize() {
    assertEquals(history.correctArgSize(new String[] {}), true);
    assertEquals(history.correctArgSize(new String[] {"2"}), true);
  }// end method testCorrectArgSize

  /**
   * Reset the static variable history.
   */
  @After
  public void tearDown() {
    shellData.setHistory(new ArrayList<String>());
  }// end method tearDown

}// end class HistoryTest
