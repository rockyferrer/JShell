package test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Cat;
import driver.Echo;
import driver.Ls;
import driver.MkDir;
import driver.Mv;
import driver.ShellData;
import driver.Exceptions.PathObjectExistsException;

/**
 * Created by Abhishek on 26/03/16. Finished by Rocky.
 * 
 * The MvTest class contains tests for the Mv class.
 */
public class MvTest {

  // the mkdir, ls, mv, echo, and cat commands are required to test mv
  private ShellData shellData;
  private MkDir mkdir;
  private Ls ls;
  private Mv mv;
  private Echo echo;
  private Cat cat;

  /**
   * Initialize the variables.
   */
  @Before
  public void setUp() {
    shellData = new ShellData();
    mkdir = new MkDir(shellData);
    ls = new Ls(shellData);
    mv = new Mv(shellData);
    echo = new Echo(shellData);
    cat = new Cat(shellData);

  }// end method setUp

  /**
   * Tests execute on a single directory.
   */
  @Test
  public void testExecuteOnADirectory() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    mkdir.execute(new String[] {"dir2"});
    mv.execute(new String[] {"dir1", "dir2/dir1"});
    String expected1 = "dir2 \n";
    String actual1 = ls.execute(new String[] {});
    assertEquals(expected1, actual1);
    String expected2 = "dir1 \n";
    String actual2 = ls.execute(new String[] {"dir2"});
    assertEquals(expected2, actual2);
  }// end method testExecuteOnADirectory

  /**
   * Test execute on a single file.
   */
  @Test
  public void testExecuteOnAFile() throws Exception {
    echo.execute(new String[] {"\"hello there\" > file1.txt"});
    mv.execute(new String[] {"file1.txt", "file2.txt"});
    String expectedLs = "file2.txt \n";
    String actualLs = ls.execute(new String[] {});
    assertEquals(expectedLs, actualLs);
    String expectedCat = "hello there\n";
    String actualCat = cat.execute(new String[] {"file2.txt"});
    assertEquals(expectedCat, actualCat);
  }// end method testExecuteOnAFile

  /**
   * Test execute on a directory containing some sub objects.
   */
  @Test
  public void testExecuteOnADirectoryContainingSubObjects() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    mkdir.execute(new String[] {"dir2"});
    mkdir.execute(new String[] {"dir1/dirA"});
    echo.execute(new String[] {"\"hello there\" > dir1/file1.txt"});
    mv.execute(new String[] {"dir1", "dir2/dir1"});
    String actual1 = ls.execute(new String[] {"dir2"});
    String expected1 = "dir1 \n";
    assertEquals(expected1, actual1);
    String expected2 = "dirA file1.txt \n";
    String actual2 = ls.execute(new String[] {"dir2/dir1"});
    assertEquals(expected2, actual2);
  }// end method testExecuteOnADirectoryContainingSubObjects

  /**
   * Test execute on an existing directory. Should throw a
   * PathObjectExistsException.
   */
  @Test(expected = PathObjectExistsException.class)
  public void testExecuteOnExistingPathObject() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    mkdir.execute(new String[] {"dir2"});
    mv.execute(new String[] {"dir1", "dir2"});
  }// end method testExecuteOnExistingPathObject

  /**
   * Tests the correctArgSize method.
   */
  @Test
  public void testCorrectArgSize() {
    assertEquals(mv.correctArgSize(new String[] {"dir1", "dir2"}), true);
    assertEquals(mv.correctArgSize(new String[] {"d1", "d2", "d3"}), false);
    assertEquals(mv.correctArgSize(new String[] {"d1", "d2", ">", "file.txt"}),
        false);
  }// end method testCorrectArgSize

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

}// end class MvTest
