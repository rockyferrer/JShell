package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Cd;
import driver.Directory;
import driver.Exceptions.InvalidNumberOfArgsException;
import driver.Exceptions.NullPathException;
import driver.MkDir;
import driver.ShellData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sajjad on 06/03/16.
 * 
 * The CdTest class contains tests for the Cd class.
 */
public class CdTest {

  // the mkDir and cd commands are needed to test the Cd class
  MkDir mkDir;
  Cd cd;
  private ShellData shellData;

  /**
   * Initialize the variables
   */
  @Before
  public void setUp() {
    shellData = new ShellData();
    mkDir = new MkDir(shellData);
    cd = new Cd(shellData);
  }// end method setUp

  /**
   * Test "cd ."
   */
  @Test
  public void testRemain() throws Exception {
    Directory expected = shellData.getCurrentDirectory();
    cd.execute(new String[] {"."});
    Directory result = shellData.getCurrentDirectory();
    assertEquals(expected, result);

  }// end method testRemain

  /**
   * Test "cd /"
   */
  @Test
  public void testFoot() throws Exception {
    Directory expected = shellData.getHomeDirectory();
    cd.execute(new String[] {"/"});
    Directory result = shellData.getCurrentDirectory();
    assertEquals(expected, result);
  }// end method testFoot

  /**
   * Test "cd /dir"
   */
  @Test
  public void testDirect() throws Exception {
    Directory expected = new Directory("/dir");
    mkDir.execute(new String[] {"dir"});
    cd.execute(new String[] {"dir"});
    Directory result = shellData.getCurrentDirectory();
    assertTrue(expected.equals(result));
  }// end method testDirect

  /**
   * Test "cd .."
   */
  @Test
  public void testParent() throws Exception {
    mkDir.execute(new String[] {"dir"});
    cd.execute(new String[] {"dir"});
    Directory expected = shellData.getCurrentDirectory().getParent();
    cd.execute(new String[] {".."});
    Directory result = shellData.getCurrentDirectory();
    assertEquals(expected, result);

  }// end method testParent

  /**
   * Tests execute on an absolute path
   */
  @Test
  public void testExecuteFullPath() throws Exception {
    mkDir.execute(new String[] {"a", "a/b", "a/b/c"});
    String expected = "/a/b";
    cd.execute(new String[] {"/a/b"});
    String actual = shellData.getCurrentDirectory().toString();
    assertEquals(expected, actual);
  }// end method testExecuteFullPath

  /**
   * Tests execute on a path that does not exist
   *
   * Expects a NullPathException to be thrown
   */
  @Test(expected = NullPathException.class)
  public void testExecuteNonExistentPath() throws Exception {
    cd.execute(new String[] {"a"});
  }// end method testExecuteNonExistentPath

  /**
   * Tests calling Execute with different inputs several times in a row
   */
  @Test
  public void testSeveralExecutesInARow() throws Exception {
    mkDir.execute(new String[] {"a", "a/b", "a/b/c", "d", "d/e"});

    cd.execute(new String[] {"a"});
    assertEquals("/a", shellData.getCurrentDirectory().toString());

    cd.execute(new String[] {".."});
    assertEquals("/", shellData.getCurrentDirectory().toString());

    cd.execute(new String[] {"a/b"});
    assertEquals("/a/b", shellData.getCurrentDirectory().toString());

    cd.execute(new String[] {"/d/e"});
    assertEquals("/d/e", shellData.getCurrentDirectory().toString());
  }

  /**
   * Tests calling Execute with an incorrect number of arguments
   *
   * Expects an InvalidNumberOfArgsException to be thrown
   */
  @Test(expected = InvalidNumberOfArgsException.class)
  public void testExecuteWithBadArguments() throws Exception {
    mkDir.execute(new String[] {"a", "a/b", "a/b/c", "d", "d/e"});

    cd.execute(new String[] {});
  }

  /**
   * Tests calling Execute after invalid calls have been made
   */
  @Test
  public void testExecuteAfterError() throws Exception {
    mkDir.execute(new String[] {"a", "a/b", "a/b/c", "d", "d/e"});

    try {
      cd.execute(new String[] {});
    } catch (InvalidNumberOfArgsException e) {
    }

    // assert that no changes have been made to the current directory
    assertEquals("/", shellData.getCurrentDirectory().toString());

    try {
      cd.execute(new String[] {"f"});
    } catch (NullPathException e) {
    }

    // assert that no changes have been made to the current directory
    assertEquals("/", shellData.getCurrentDirectory().toString());

    cd.execute(new String[] {"a"});
    assertEquals("/a", shellData.getCurrentDirectory().toString());
  }

  /**
   * Tests the correctArgSize method
   */
  @Test
  public void testCorrectArgSize() {
    // true if no args
    assertEquals(cd.correctArgSize(new String[] {}), false);
    // false if there are any args
    assertEquals(cd.correctArgSize(new String[] {"a"}), true);
    assertEquals(cd.correctArgSize(new String[] {"a", "b"}), false);
  }// end method testCorrectArgSize

  /**
   * Resets static directory variables
   */
  @After
  public void tearDown() {
    shellData.resetDirectorySystem();
  }// end method tearDown

}// end class CdTest
