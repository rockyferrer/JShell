package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Cd;
import driver.Exceptions.InvalidPathObjectNameException;
import driver.Exceptions.NullPathException;
import driver.Ls;
import driver.MkDir;
import driver.ShellData;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rocky on 06/03/16.
 *
 * The MkDirTest class contains the tests for the MkDir class.
 */
public class MkDirTest {

  // the mkdir, ls, and cd commands are needed to test this class
  private MkDir mkdir;
  private Ls ls;
  private Cd cd;
  private ShellData shellData;

  /**
   * Initialize the variables.
   */
  @Before
  public void setUp() {
    shellData = new ShellData();
    mkdir = new MkDir(shellData);
    ls = new Ls(shellData);
    cd = new Cd(shellData);
  }// end method setUp

  /**
   * Test execute on a single directory. Checks that the directory has been made
   * by using ls.
   */
  @Test
  public void testExecuteOnSingleDirectory() throws Exception {
    String[] args = {"directory"};
    mkdir.execute(args);
    String expected = "directory \n";
    String actual = ls.execute(new String[] {});
    assertEquals(expected, actual);
  }// end method testExecuteOnSingleDirectory

  /**
   * Test execute on multiple directories. Checks that the directory has been
   * made by using ls.
   */
  @Test
  public void testExecuteOnMultipleDirectories() throws Exception {
    String[] args = {"dir1", "dir2", "dir3"};
    mkdir.execute(args);
    String expected = "dir1 dir2 dir3 \n";
    String actual = ls.execute(new String[] {});
    assertEquals(expected, actual);
  }// end method testExecuteOnMultipleDirectories

  /**
   * Test execute on an absolute path. Checks that the directory has been made
   * by cd-ing into its parent and by using ls.
   */
  @Test
  public void testExecuteOnAbsolutePath() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    mkdir.execute(new String[] {"/dir1/dir2"});
    cd.execute(new String[] {"/dir1"});
    String expected = "dir2 \n";
    String actual = ls.execute(new String[] {});
    assertEquals(expected, actual);
  }// end method testExecuteOnAbsolutePath

  /**
   * Test execute on a relative path. Checks that the directory has been made by
   * cd-ing into a directory, using mkdir on a relative path, and then cd-ing
   * into its parent and using ls.
   */
  @Test
  public void testExecuteOnRelativePath() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    mkdir.execute(new String[] {"/dir1/dir2"});
    cd.execute(new String[] {"dir1"});
    mkdir.execute(new String[] {"dir2/dir3"});
    String expected = "dir3 \n";
    String actual = ls.execute(new String[] {"dir2"});
    assertEquals(expected, actual);
  }// end method testExecuteOnRelativePath

  /**
   * Test execute on an invalid PathObject name. Should throw an
   * InvalidPathObjectNameException
   */
  @Test(expected = InvalidPathObjectNameException.class)
  public void testExecuteOnDirectoryWithInvalidChars() throws Exception {
    mkdir.execute(new String[] {".."});
  }// end method testExecuteOnDirectoryWithInvalidChars

  /**
   * Test execute on an absolute path that does not exist. Should throw a
   * NullPathException.
   **/
  @Test(expected = NullPathException.class)
  public void testExecuteOnInvalidPath() throws Exception {
    mkdir.execute(new String[] {"/dir1/dir2/dir3"});
  }// end method NullPathException

  /**
   * Test correctArgSize method. Should return false only when no arguments are
   * passed in, and true otherwise.
   */
  @Test
  public void testCorrectArgSize() throws Exception {
    assertEquals(mkdir.correctArgSize(new String[] {"dir1"}), true);
    assertEquals(mkdir.correctArgSize(new String[] {"dir1", "dir2"}), true);
    String[] lotsOfArgsPathsAndDirectories = new String[5];
    lotsOfArgsPathsAndDirectories[0] = "dir1";
    lotsOfArgsPathsAndDirectories[1] = "/dir1/dir2";
    lotsOfArgsPathsAndDirectories[2] = "/dir1/dir2/dir3";
    lotsOfArgsPathsAndDirectories[3] = "dir1/dir4";
    lotsOfArgsPathsAndDirectories[4] = "dir1/dir2/dir3/dir5";
    assertEquals(mkdir.correctArgSize(lotsOfArgsPathsAndDirectories), true);
    assertEquals(mkdir.correctArgSize(new String[] {}), false);
  }// end method testCorrectArgSize

  /**
   * Reset the static variable home directory.
   */
  @After
  public void tearDown() {
    shellData.resetDirectorySystem();
  }// end method tearDown

}// end class MkDirTest
