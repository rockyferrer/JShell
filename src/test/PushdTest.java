package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Cd;
import driver.DirectoryStack;
import driver.Exceptions.InvalidNumberOfArgsException;
import driver.Exceptions.NullPathException;
import driver.MkDir;
import driver.Pushd;
import driver.ShellData;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rocky on 06/03/16.
 *
 * The PushdTest class contains the tests for the Pushd class.
 */
public class PushdTest {

  // the pushd, mkdir, and cd commands are needed to test this class.
  private Pushd pushd;
  private MkDir mkdir;
  private Cd cd;
  private ShellData shellData;

  /**
   * Initialize the variables.
   */
  @Before
  public void setUp() {
    shellData = new ShellData();
    pushd = new Pushd(shellData);
    mkdir = new MkDir(shellData);
    cd = new Cd(shellData);
  }// end method setUp

  /**
   * Test execute on a directory that does not exist. Should throw a
   * NullPathException.
   */
  @Test(expected = NullPathException.class)
  public void testExecuteOnNoneExistentDirectory() throws Exception {
    pushd.execute(new String[] {"dir1"});
  }// end method testExecuteOnNoneExistentDirectory

  /**
   * Test execute on a single PathObject by making a directory and using pushd
   * on that directory so it pushes the home directory onto the directory stack
   * and cd's into the new directory made.
   */
  @Test
  public void testExecuteOnStackWithOnePathObject() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    pushd.execute(new String[] {"dir1"});
    assertEquals(shellData.getCurrentDirectory().getName() + " \n", "dir1 \n");
    assertEquals(shellData.getDirectoryStack().getTop() + " \n", "/ \n");
  }// end method testExecuteOnStackWithOnePathObject

  /**
   * Test execute on a single PathObject in the stack, but this time passing in
   * a path as the directory to cd into afterwards.
   */
  @Test
  public void testExecuteUsingAPath() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    mkdir.execute(new String[] {"dir1/dir2"});
    pushd.execute(new String[] {"dir1/dir2"});
    assertEquals(shellData.getCurrentDirectory().getName() + " \n", "dir2 \n");
    assertEquals(shellData.getDirectoryStack().getTop() + " \n", "/ \n");
  }

  /**
   * Test execute on multiple PathObjects in succession. After each call of
   * pushd, checks that the top object on the directory stack is the object that
   * was pushed on.
   */
  @Test
  public void testExecuteOnStackWithMultiplePathObjects() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    mkdir.execute(new String[] {"/dir1/dir2"});
    mkdir.execute(new String[] {"/dir1/dir3"});
    mkdir.execute(new String[] {"/dir1/dir4"});
    cd.execute(new String[] {"/dir1"});
    pushd.execute(new String[] {"/dir1/dir2"});
    pushd.execute(new String[] {"/dir1/dir3"});
    assertEquals(shellData.getDirectoryStack().getTop() + " \n",
        "/dir1/dir2 \n");
    pushd.execute(new String[] {"/dir1/dir4"});
    assertEquals(shellData.getCurrentDirectory().getName() + " \n", "dir4 \n");
    assertEquals(shellData.getDirectoryStack().getTop() + " \n",
        "/dir1/dir3 \n");
  }// end method testExecuteOnStackWithMultiplePathObjects

  /**
   * Test execute on an invalid number of arguments, which is any amount not
   * equal to 1.
   */
  @Test(expected = InvalidNumberOfArgsException.class)
  public void testExecuteOnInvalidNumberOfArguments() throws Exception {
    pushd.execute(new String[] {"dir1", "dir2"});
  }// end method testExecuteOnInvalidNumberOfArguments

  /**
   * Test the correctArgsSize method. It should return true when the number of
   * arguments passed in is exactly equal to 1.
   */
  @Test
  public void testCorrectArgSize() throws Exception {
    // one argument passed in, so should return true
    assertEquals(pushd.correctArgSize(new String[] {"a"}), true);
    // no arguments passed in, so should return false
    assertEquals(pushd.correctArgSize(new String[] {}), false);
    // multiple arguments passed in, so should return false
    assertEquals(pushd.correctArgSize(new String[] {"a", "b"}), false);
  }// end method testCorrectArgSize

  /**
   * Reset the static variable directory stack.
   */
  @After
  public void tearDown() {
    shellData.setDirectoryStack(new DirectoryStack());
    shellData.resetDirectorySystem();
  }// end method tearDown

}// end class PushdTest
