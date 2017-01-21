package test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Cat;
import driver.Echo;
import driver.JInterpreter;
import driver.MkDir;
import driver.ShellData;
import driver.Exceptions.InvalidNumberOfArgsException;

/**
 * Created by Felipe on 06/03/16.
 * 
 * The CatTest class contains tests for the Cat class.
 */
public class CatTest {

  // the cat, mkdir, and echo commands are required to test the Cat class
  private Cat cat;
  private MkDir mkdir;
  private Echo echo;

  private ShellData shellData;

  /**
   * Initialize the variables.
   */
  @Before
  public void setUp() throws Exception {
    shellData = new ShellData();
    cat = new Cat(shellData);
    mkdir = new MkDir(shellData);

    // create PathObjects
    mkdir.execute(new String[] {"a"});
    echo = new Echo(shellData);
    echo.execute(new String[] {"\"hello\" > b"});
    echo.execute(new String[] {"\"hi\" > a/c"});
  }// end method setUp

  /**
   * Tests execute on a file in the current directory
   */
  @Test
  public void testExecuteOnFile() throws Exception {
    assertEquals(cat.execute(new String[] {"b"}), ("hello\n"));
  }// end method testExecuteOnFile

  /**
   * Tests execute on a directory in the file systems.
   */
  @Test
  public void testExecuteOnDirectory() throws Exception {
    assertEquals(cat.execute(new String[] {"a"}), "Path not a file\n");
  }// end method testExecuteOnDirectory

  /**
   * Tests execute on a file using an absolute path as input
   */
  @Test
  public void testExecuteOnFullPath() throws Exception {
    assertEquals(cat.execute(new String[] {"/a/c"}), ("hi\n"));
  }// end method testExecuteOnFullPath

  /**
   * Tests execute on several files in a row
   */
  @Test
  public void testExecuteOnSeveralFiles() throws Exception {
    assertEquals(cat.execute(new String[] {"b"}), ("hello\n"));
    assertEquals(cat.execute(new String[] {"/a/c"}), ("hi\n"));
    assertEquals(cat.execute(new String[] {"a/c"}), ("hi\n"));
  }// end method testExecuteOnSeveralFiles

  /**
   * Tests execute on a file and then on a directory
   */
  @Test
  public void testExecuteOnFileAndDirectory() throws Exception {
    assertEquals(cat.execute(new String[] {"b"}), ("hello\n"));
    assertEquals(cat.execute(new String[] {"a"}), ("Path not a file\n"));
  }// end method testExecuteOnFileAndDirectory

  /**
   * Tests execute with several arguments as input
   */
  @Test
  public void testExecuteWithSeveralArguments() throws Exception {
    assertEquals(cat.execute(new String[] {"b", "a"}),
        ("b:\nhello\n\na:\nPath not a file\n"));
  }// end method testExecuteWithSeveralArguments

  /**
   * Tests execute with redirection
   */
  @Test
  public void testExecuteWithRedirection() throws Exception {
    JInterpreter ji = new JInterpreter(this.shellData);
    assertEquals(ji.executeCommand("cat a >> file"), "");
    assertEquals(ji.executeCommand("cat file"), ("Path not a file\n\n"));
  }// end method testExecuteWithRedirection

  /**
   * Tests execute with incorrect amount of arguments
   */
  @Test(expected = InvalidNumberOfArgsException.class)
  public void testExecuteWithBadArguments() throws Exception {
    cat.execute(new String[] {});
  }// end method testExecuteWithBadArguments

  /**
   * Tests the correctArgSize method.
   */
  @Test
  public void testCorrectArgSize() throws Exception {
    // test with one argument
    assertEquals(cat.correctArgSize(new String[] {"a"}), true);

    // test with two arguments
    assertEquals(cat.correctArgSize(new String[] {"a", "b"}), true);

    // test with no arguments
    assertEquals(cat.correctArgSize(new String[] {}), false);
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

}// end class CatTest
