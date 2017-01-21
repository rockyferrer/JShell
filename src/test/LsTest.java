package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Exceptions.NullPathException;
import driver.Ls;
import driver.MkDir;
import driver.ShellData;

import static org.junit.Assert.assertEquals;

/**
 * Created by Felipe on 06/03/16.
 * 
 * The LsTest class contains tests for the Ls class.
 */
public class LsTest {

  // the ls and mkdir commands are needed to test the Ls class
  private Ls ls;
  private MkDir mkdir;
  private ShellData shellData;

  /**
   * Initialize the variables.
   */
  @Before
  public void setUp() {
    shellData = new ShellData();
    ls = new Ls(shellData);
    mkdir = new MkDir(shellData);
  }// end method setUp

  /**
   * Tests execute on an empty directory and checks that the output is empty.
   */
  @Test
  public void testExecuteEmptyDirectory() throws Exception {
    String expected = "\n";
    String actual = ls.execute(new String[] {});
    assertEquals(expected, actual);
  }// end method testExecuteEmptyDirectory

  /**
   * Test execute on a directory with sub-objects and checks for the proper
   * output.
   */
  @Test
  public void testExecuteNonEmptyDirectory() throws Exception {
    mkdir.execute(new String[] {"a"});
    String expected = "a \n";
    String actual = ls.execute(new String[] {});
    assertEquals(expected, actual);
  }// end method testExecuteNonEmptyDirectory

  /**
   * Tests execute on a relative path to the current directory.
   */
  @Test
  public void testExecuteRelativePath() throws Exception {
    mkdir.execute(new String[] {"a", "a/b", "a/b/c"});
    String expected = "c \n";
    String actual = ls.execute(new String[] {"a/b"});
    assertEquals(expected, actual);
  }// end method testExecuteRelativePath

  /**
   * Tests execute on an absolute path.
   */
  @Test
  public void testExecuteFullPath() throws Exception {
    mkdir.execute(new String[] {"a", "a/b", "a/b/c"});
    String expected = "c \n";
    String actual = ls.execute(new String[] {"/a/b"});
    assertEquals(expected, actual);
  }// end method testExecuteFullPath

  /**
   * Tests execute with several arguments as input.
   */
  @Test
  public void testExecuteSeveralPaths() throws Exception {
    mkdir.execute(new String[] {"a", "a/b", "a/b/c"});
    String expected = "/a/b:\nc \na:\nb \n";
    String actual = ls.execute(new String[] {"/a/b", "a"});
    assertEquals(expected, actual);
  }// end method testExecuteSeveralPaths

  /**
   * Tests execute on a path that does not exist.
   *
   * Expects a NullPathException to be thrown.
   */
  @Test(expected = NullPathException.class)
  public void testExecuteNonExistentPath() throws Exception {
    ls.execute(new String[] {"a"});
  }// end method testExecuteNonExistentPath

  /**
   * Tests the recursive execute on home.
   */
  @Test
  public void testRecursiveExecuteOnHome() throws Exception {
    String expected = "/:\n\n\n";
    String actual = ls.execute(new String[] {"-R"});
    assertEquals(expected, actual);
  }// end method testRecursiveExecuteOnHome

  /**
   * Tests the recursive execute on existing directories.
   */
  @Test
  public void testRecursiveExecuteOnDirectories() throws Exception {
    mkdir.execute(new String[] {"a", "a/b", "a/b/c", "a/b/d"});
    String expected =
        "/:\na \n\n/a:\nb \n\n/a/b:\nc d \n\n/a/b/c:\n\n\n/a/b/d:\n\n\n";
    String actual = ls.execute(new String[] {"-R"});
    assertEquals(expected, actual);
  }// end method testRecursiveExecuteOnDirectories

  /**
   * Tests the recursive execute on lower case r (ie. ls -r).
   */
  @Test
  public void testRecursiveExecuteWithLowercaseR() throws Exception {
    mkdir.execute(new String[] {"a", "a/b", "a/b/c", "a/b/d"});
    String expected =
        "/:\na \n\n/a:\nb \n\n/a/b:\nc d \n\n/a/b/c:\n\n\n/a/b/d:\n\n\n";
    String actual = ls.execute(new String[] {"-r"});
    assertEquals(expected, actual);
  }// end method testRecursiveExecuteWithLowercaseR

  /**
   * Tests the recursive execute with a path.
   */
  @Test
  public void testRecursiveExecuteWithPath() throws Exception {
    mkdir.execute(new String[] {"a", "a/b", "a/b/c", "a/b/d"});
    String expected = "/a:\nb \n\n/a/b:\nc d \n\n/a/b/c:\n\n\n/a/b/d:\n\n\n";
    String actual = ls.execute(new String[] {"-r", "a"});
    assertEquals(expected, actual);
  }// end method testRecursiveExecuteWithPath

  /**
   * Tests the recursive execute on a non-existent path.
   *
   * Expects a NullPathException to be thrown.
   */
  @Test(expected = NullPathException.class)
  public void testRecursiveExecuteWithInvalidPath() throws Exception {
    ls.execute(new String[] {"-R", "a"});
  }// end method testRecursiveExecuteWithInvalidPath

  /**
   * Tests the correctArgSize method.
   */
  @Test
  public void testCorrectArgSize() {
    // Should work for any arg size
    assertEquals(ls.correctArgSize(new String[] {}), true);
    assertEquals(ls.correctArgSize(new String[] {"a"}), true);
    assertEquals(ls.correctArgSize(new String[] {"a", "b"}), true);
  }// end method testCorrectArgSize

  /**
   * Resets static directory variables.
   */
  @After
  public void tearDown() {
    shellData.resetDirectorySystem();
  }// end method tearDown

}// end class LsTest
