package test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Cat;
import driver.Cp;
import driver.Echo;
import driver.Ls;
import driver.MkDir;
import driver.ShellData;
import driver.Exceptions.PathObjectExistsException;

/**
 * Created by Abhishek on 26/03/16. Finished by Rocky.
 * 
 * The CpTest class contains tests for the Cp class.
 */
public class CpTest {

  // the mkdir, cp, ls, and echo commands are required to test cp
  private ShellData shellData;
  private MkDir mkdir;
  private Cp cp;
  private Ls ls;
  private Echo echo;
  private Cat cat;

  /**
   * Initialize the variables.
   */
  @Before
  public void setUp() {
    shellData = new ShellData();
    mkdir = new MkDir(shellData);
    cp = new Cp(shellData);
    ls = new Ls(shellData);
    echo = new Echo(shellData);
    cat = new Cat(shellData);

  }// end method setUp

  /**
   * Tests execute on a single directory.
   */
  @Test
  public void testExecuteOnSingleDirectory() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    mkdir.execute(new String[] {"dir2"});
    cp.execute(new String[] {"dir1", "dir2/dir1"});
    String expected1 = "dir1 dir2 \n";
    String actual1 = ls.execute(new String[] {});
    assertEquals(expected1, actual1);
    String expected2 = "dir1 \n";
    String actual2 = ls.execute(new String[] {"dir2"});
    assertEquals(expected2, actual2);
  }// end method testExecuteOnSingleDirectory

  /**
   * Tests execute on a single file.
   */
  @Test
  public void testExecuteOnAFile() throws Exception {
    echo.execute(new String[] {"\"hello there\" > file1.txt"});
    cp.execute(new String[] {"file1.txt", "file2.txt"});
    String expectedLs = "file1.txt file2.txt \n";
    String actualLs = ls.execute(new String[] {});
    assertEquals(expectedLs, actualLs);
    String expectedCat = "hello there\n";
    String actualCat = cat.execute(new String[] {"file2.txt"});
    assertEquals(expectedCat, actualCat);
  }// end method testExecuteOnAFile

  /**
   * Tests execute on a directory containing some sub objects.
   */
  @Test
  public void testExecuteOnADirectoryContainingSubObjects() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    mkdir.execute(new String[] {"dir2"});
    mkdir.execute(new String[] {"dir1/dirA"});
    echo.execute(new String[] {"\"hello there\" > dir1/file1.txt"});
    cp.execute(new String[] {"dir1", "dir2/dir1"});
    String expected1 = "dir1 dir2 \n";
    String actual1 = ls.execute(new String[] {});
    assertEquals(expected1, actual1);
    String actual2 = ls.execute(new String[] {"dir2"});
    String expected2 = "dir1 \n";
    assertEquals(expected2, actual2);
    String expected3 = "dirA file1.txt \n";
    String actual3 = ls.execute(new String[] {"dir2/dir1"});
    assertEquals(expected3, actual3);
  }// end method testExecuteOnADirectoryContainingSubObjects

  /**
   * Test execute on an existing directory. Should throw a
   * PathObjectExistsException.
   */
  @Test(expected = PathObjectExistsException.class)
  public void testExecuteOnExistingPathObject() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    mkdir.execute(new String[] {"dir2"});
    cp.execute(new String[] {"dir1", "dir2"});
  }// end method testExecuteOnExistingPathObject

  /**
   * Reset static variables
   */
  @After
  public void tearDown() {
    // reset home directory
    shellData.resetDirectorySystem();
    // reset current directory
    shellData.setCurrentDirectory(shellData.getHomeDirectory());
  }// end method tearDown

}// end class CpTest
