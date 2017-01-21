package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Cd;
import driver.JInterpreter;
import driver.Exceptions.InvalidNumberOfArgsException;
import driver.MkDir;
import driver.Pwd;
import driver.ShellData;

import static org.junit.Assert.assertEquals;

/**
 * Created by Felipe on 06/03/16.
 * 
 * The LsTest class contains tests for the Ls class.
 */
public class PwdTest {

  // the pwd, cd, and mkdir commands are required to test the Pwd class
  private Pwd pwd;
  private Cd cd;
  private MkDir mkDir;
  private ShellData shellData;

  /**
   * Initialize the variables
   */
  @Before
  public void setUp() throws Exception {
    shellData = new ShellData();
    pwd = new Pwd(shellData);
    mkDir = new MkDir(shellData);
    cd = new Cd(shellData);
    cd.execute(new String[] {"/"});
  }// end method setUp

  /**
   * Tests execute when the current directory is the home directory
   */
  @Test
  public void testExecuteOnHome() throws Exception {
    assertEquals(pwd.execute(new String[] {}), "/\n");
  }// end method testExecuteOnHome

  /**
   * Tests execute when the current directory is a new directory
   */
  @Test
  public void testExecuteOnNewDirectory() throws Exception {
    mkDir.execute(new String[] {"a"});
    cd.execute(new String[] {"a"});
    assertEquals(pwd.execute(new String[] {}), "/a\n");
  }// end method testExecuteOnNewDirectory

  /**
   * Tests execute twice in a row in the home directory
   */
  @Test
  public void testExecuteTwice() throws Exception {
    assertEquals(pwd.execute(new String[] {}), "/\n");
    assertEquals(pwd.execute(new String[] {}), "/\n");
  }// end method testExecuteTwice

  /**
   * Tests execute with the wrong amount of arguments
   *
   * Expects InvalidNumberOfArgsException to be thrown
   */
  @Test(expected = InvalidNumberOfArgsException.class)
  public void testExecuteWithBadArguments() throws Exception {
    pwd.execute(new String[] {"a"});
  }// end method testExecuteWithBadArguments

  /**
   * Tests execute on a directory with a large amount of depth
   */
  @Test
  public void testExecuteWithLargeDepth() throws Exception {
    mkDir.execute(new String[] {"b", "b/c", "b/c/d", "b/c/d/e"});
    cd.execute(new String[] {"b/c/d/e"});
    assertEquals(pwd.execute(new String[] {}), "/b/c/d/e\n");
  }// end method testExecuteWithLargeDepth

  /**
   * Tests execute with redirection
   */
  @Test
  public void testExecuteWithRedirection() throws Exception {
    JInterpreter ji = new JInterpreter(this.shellData);
    assertEquals(ji.executeCommand("pwd > file"), "");
    assertEquals(ji.executeCommand("cat file"), ("/\n\n"));
  }// end method testExecuteWithRedirection

  /**
   * Tests the correctArgSize method
   */
  @Test
  public void testCorrectArgSize() {
    // true if no args
    assertEquals(pwd.correctArgSize(new String[] {}), true);
    // false if there are any args
    assertEquals(pwd.correctArgSize(new String[] {"a"}), false);
    assertEquals(pwd.correctArgSize(new String[] {"a", "b"}), false);
  }// end method testCorrectArgSize

  /**
   * Resets static directory variables
   */
  @After
  public void tearDown() {
    shellData.resetDirectorySystem();
  }// end method tearDown

}// end class PwdTest
