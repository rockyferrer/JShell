package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import driver.Man;
import driver.ShellData;
import driver.Exceptions.InvalidNumberOfArgsException;

/**
 * Created by Sajjad on 06/03/16.
 * 
 * The ManTest class contains tests for the Man class.
 */
public class ManTest {

  // the man command is required to test the Man class
  Man man;
  private ShellData shellData;

  /**
   * Initialize the variables.
   */
  @Before
  public void setUp() {
    man = new Man(shellData);
  }// end method setUp

  /**
   * Test one command.
   */
  @Test
  public void testOneCommand() throws InvalidNumberOfArgsException {
    String result = man.execute(new String[] {"exit"});
    String expected = "\n" + "exit\n\n" + "Quit the program." + "\n\n";
    assertEquals(result, expected);
  }// end method testOneCommand

  /**
   * Test a non-existent command.
   */
  @Test
  public void testFalseCommand() throws InvalidNumberOfArgsException {
    String result = man.execute(new String[] {"london"});
    String expected = "Invalid command, please try again\n";
    assertEquals(result, expected);
  }// end method testFalseCommand

  /**
   * Test no commands (throw InvalidNumberOfArgsException).
   */
  @Test
  public void testNoCommands() throws InvalidNumberOfArgsException {
    String result = man.execute(new String[] {""});
    String expected = "Invalid command, please try again\n";
    assertEquals(result, expected);
  }// end method testNoCommands

  /**
   * Test two commands (throw InvalidNumberOfArgsException).
   */
  @Test(expected = InvalidNumberOfArgsException.class)
  public void testMoreThanOneCommand() throws InvalidNumberOfArgsException {
    man.execute(new String[] {"exit", "cd"});
  }// end method testMoreThanOneCommand

  /**
   * Test for the correctArgSize() method.
   */
  @Test
  public void testCorrectArgSize() throws InvalidNumberOfArgsException {
    boolean result1 = man.correctArgSize(new String[] {"london"});
    boolean expected1 = true;
    assertEquals(result1, expected1);
    boolean result2 = man.correctArgSize(new String[] {"london", "paris"});
    boolean expected2 = false;
    assertEquals(result2, expected2);
  }// end method testCorrectArgSize

}// end class Man
