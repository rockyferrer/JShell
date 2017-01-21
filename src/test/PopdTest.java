package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.DirectoryStack;
import driver.Exceptions.DirectoryStackIsEmptyException;
import driver.Exceptions.InvalidNumberOfArgsException;
import driver.MkDir;
import driver.Popd;
import driver.Pushd;
import driver.ShellData;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rocky on 07/03/16.
 *
 * The PopdTest class contains the tests for the Popd class.
 */
public class PopdTest {

  // the popd, pushd, and mkdir commands are required to test this class
  private Popd popd;
  private Pushd pushd;
  private MkDir mkdir;
  private ShellData shellData;

  /**
   * Initialize the variables.
   */
  @Before
  public void setUp() {
    shellData = new ShellData();
    popd = new Popd(shellData);
    pushd = new Pushd(shellData);
    mkdir = new MkDir(shellData);
  }// end method setUp

  /**
   * Test execute on an empty stack. Should throw a
   * DirectoryStackIsEmptyException.
   */
  @Test(expected = DirectoryStackIsEmptyException.class)
  public void testExecuteOnEmptyStack() throws Exception {
    popd.execute(new String[] {});
  }// end method testExecuteOnEmptyStack

  /**
   * Test execute on one object in the stack by pushing an object onto it, then
   * calling popd.
   */
  @Test
  public void testExecuteOnOneObjectInStack() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    pushd.execute(new String[] {"dir1"});
    popd.execute(new String[] {});
    assertEquals("/", shellData.getCurrentDirectory().getName());
  }// end method testExecuteOnOneObjectInStack

  /**
   * Test execute on multiple objects in the stack, by pushing two objects onto
   * it, then calling popd in succession.
   */
  @Test
  public void testExecuteOnMultipleObjectsInStack() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    mkdir.execute(new String[] {"dir2"});
    pushd.execute(new String[] {"dir1"});
    pushd.execute(new String[] {"/dir2"});
    popd.execute(new String[] {});
    assertEquals("dir1", shellData.getCurrentDirectory().getName());
    popd.execute(new String[] {});
    assertEquals("/", shellData.getCurrentDirectory().getName());
  }// end method testExecuteOnMultipleObjectsInStack

  /**
   * Test execute with arguments, which is invalid. Should throw an
   * InvalidNumberOfArgsException.
   */
  @Test(expected = InvalidNumberOfArgsException.class)
  public void testExecuteWithArguments() throws Exception {
    popd.execute(new String[] {"arg"});
  }// end method testExecuteWithArguments

  /**
   * Test the correctArgSize method, which should return true when there are 0
   * arguments passed in, and false otherwise.
   */
  @Test
  public void testCorrectArgSize() {
    assertEquals(popd.correctArgSize(new String[] {}), true);
    assertEquals(popd.correctArgSize(new String[] {"hi"}), false);
  }// end method testCorrectArgSize

  /**
   * Reset the static variables directory stack and home directory.
   */
  @After
  public void tearDown() {
    shellData.setDirectoryStack(new DirectoryStack());
    shellData.resetDirectorySystem();
  }// end method tearDown

}// end class PopdTest
