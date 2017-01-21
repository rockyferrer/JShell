package test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Cat;
import driver.Echo;
import driver.Grep;
import driver.MkDir;
import driver.ShellData;
import driver.Exceptions.InvalidTypeException;

/**
 * Created by Abhishek on 26/03/16.
 * 
 * The GrepTest class contains tests for the Grep class.
 */
public class GrepTest {

  // the mkdir command and the echo command will be required to test the Grep
  // class
  MkDir mkdir;
  Echo echo;
  Grep grep;
  ShellData sd;

  /**
   * Initialize the variables
   */
  @Before
  public void setUp() {
    sd = new ShellData();
    mkdir = new MkDir(sd);
    echo = new Echo(sd);
    grep = new Grep(sd);
  }// end method setUp

  /**
   * Tests the execute method with -R and one file in a dir
   */
  @Test
  public void testExecuteWithRAndDirWithOneFile() throws Exception {
    assertEquals(mkdir.execute(new String[] {"dir1"}), "");
    assertEquals(echo.execute(new String[] {"\"a\na b by\" > dir1/a"}), "");
    assertEquals(grep.execute(new String[] {"-R \"a\" /dir1"}),
        "/dir1/a:\na\na b by\n\n");
  }// end method testExecuteWithRAndDirWithOneFile

  /**
   * Tests the execute method with -R and two files in a dir
   */
  @Test
  public void testExecuteWithRAndDirWithTwoFiles() throws Exception {
    assertEquals(mkdir.execute(new String[] {"dir2"}), "");
    assertEquals(echo.execute(new String[] {"\"a\na b by\" > dir2/a"}), "");
    assertEquals(echo.execute(new String[] {"\"abby\nm\" > dir2/b"}), "");
    assertEquals(grep.execute(new String[] {"-R \"a\" /dir2"}),
        "/dir2/a:\na\na b by\n\n/dir2/b:\nabby\n\n");
  }// end method testExecuteWithRAndDirWithTwoFiles

  /**
   * Tests the execute method without -R and a file in a dir
   */
  @Test(expected = InvalidTypeException.class)
  public void testExecuteWithoutRAndDirWithAFile() throws Exception {
    assertEquals(mkdir.execute(new String[] {"dir3"}), "");
    assertEquals(echo.execute(new String[] {"\"a\na b by\" > dir3/a"}), "");
    grep.execute(new String[] {"\"a\" /dir3"});
  }// end method testExecuteWithoutRAndOneFile

  /**
   * Tests the execute method without -R and a file
   */
  @Test
  public void testExecuteWithoutRAndTwoFile() throws Exception {
    assertEquals(mkdir.execute(new String[] {"dir4"}), "");
    assertEquals(echo.execute(new String[] {"\"a\na b by\" > dir4/a"}), "");
    assertEquals(grep.execute(new String[] {"\"a\" /dir4/a"}), "a\na b by\n");
  }// end method testExecuteWithoutRAndOneFile

  /**
   * Tests the execute method with redirection
   */
  @Test
  public void testExecuteWithRedirection() throws Exception {
    assertEquals(mkdir.execute(new String[] {"dir5"}), "");
    assertEquals(echo.execute(new String[] {"\"a\na b by\" > dir5/a"}), "");
    Cat cat = new Cat(sd);
    assertEquals(grep.execute(new String[] {"\"a\" /dir5/a >> file"}), "");
    assertEquals(cat.execute(new String[] {"file"}), "a\na b by\n\n");
  }// end method testExecuteWithoutRAndOneFile

  /**
   * Reset static variables
   */
  @After
  public void tearDown() {
    // reset home directory
    sd.resetDirectorySystem();
    // reset current directory
    sd.setCurrentDirectory(sd.getHomeDirectory());
  }// end method tearDown

}// end class GrepTest
