package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Cat;
import driver.Echo;
import driver.Exceptions.InvalidArgsException;
import driver.Exceptions.InvalidNumberOfArgsException;
import driver.Exceptions.InvalidPathObjectNameException;
import driver.MkDir;
import driver.ShellData;

import static org.junit.Assert.assertEquals;

/**
 * Created by Abhishek on 06/03/16.
 * 
 * The EchoTest class contains tests for the Echo class.
 */
public class EchoTest {

  // the cat, mkdir, and echo commands will be used to test the Echo class
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
    mkdir.execute(new String[] {"a"});
    echo = new Echo(shellData);
  }// end method setUp

  /**
   * Test "echo STRING", where STRING is some characters surrounded by double
   * quotes.
   */
  @Test
  public void testExecuteOnlyString() throws Exception {
    assertEquals(echo.execute(new String[] {"\"hello\""}), ("hello\n"));
  }// end method testExecuteOnlyString

  /**
   * Test "echo STRING > OUTFILE".
   */
  @Test
  public void testExecuteStringReplaceFile() throws Exception {
    assertEquals(echo.execute(new String[] {"\"hello\" > b"}), (""));
    assertEquals(cat.execute(new String[] {"b"}), ("hello\n"));
  }// end method testExecuteStringReplaceFile

  /**
   * Test "echo STRING >> OUTFILE".
   */
  @Test
  public void testExecuteStringAppendFile() throws Exception {
    assertEquals(echo.execute(new String[] {"\"hello\" >> b"}), (""));
    assertEquals(cat.execute(new String[] {"b"}), ("hello\n"));
  }// end method testExecuteStringAppendFile

  /**
   * Test "echo STRING >> OUTFILE" and then "echo STRING > OUTFILE".
   */
  @Test
  public void testExecuteStringAppendThenReplaceFile() throws Exception {
    assertEquals(echo.execute(new String[] {"\"hello\" >> b"}), (""));
    assertEquals(echo.execute(new String[] {"\"hello\" > b"}), (""));
    assertEquals(cat.execute(new String[] {"b"}), ("hello\n"));
  }// end method testExecuteStringAppendThenReplaceFile

  /**
   * Test "echo STRING > OUTFILE" and then "echo STRING >> OUTFILE".
   */
  @Test
  public void testExecuteStringReplaceThenAppendFile() throws Exception {
    assertEquals(echo.execute(new String[] {"\"hello\" > b"}), (""));
    assertEquals(echo.execute(new String[] {"\"hello\" >> b"}), (""));
    assertEquals(cat.execute(new String[] {"b"}), ("hello\nhello\n"));
  }// end method testExecuteStringReplaceThenAppendFile

  /**
   * Test "echo STRING > OUTFILE" and then "echo STRING > OUTFILE".
   */
  @Test
  public void testExecuteStringReplaceTwiceFile() throws Exception {
    assertEquals(echo.execute(new String[] {"\"hello\" > b"}), (""));
    assertEquals(echo.execute(new String[] {"\"hello\" > b"}), (""));
    assertEquals(cat.execute(new String[] {"b"}), ("hello\n"));
  }// end method testExecuteStringReplaceTwiceFile

  /**
   * Test "echo STRING >> OUTFILE" and then "echo STRING >> OUTFILE".
   */
  @Test
  public void testExecuteStringAppendTwiceFile() throws Exception {
    assertEquals(echo.execute(new String[] {"\"hello\" >> b"}), (""));
    assertEquals(echo.execute(new String[] {"\"hello\" >> b"}), (""));
    assertEquals(cat.execute(new String[] {"b"}), ("hello\nhello\n"));
  }// end method testExecuteStringAppendTwiceFile

  /**
   * Tests echo with an incorrect amount of arguments.
   */
  @Test(expected = InvalidNumberOfArgsException.class)
  public void testExecuteWithBadArguments() throws Exception {
    echo.execute(new String[] {"\"hello\" >> b b"});
  }// end method testExecuteWithBadArguments

  /**
   * Tests echo with no arguments.
   */
  @Test(expected = InvalidArgsException.class)
  public void testExecuteWithNoArguments() throws Exception {
    echo.execute(new String[] {""});
  }// end method InvalidNumberOfArgsException

  /**
   * Tests echo with invalid file name.
   */
  @Test(expected = InvalidPathObjectNameException.class)
  public void testExecuteWithInvalidFileName() throws Exception {
    echo.execute(new String[] {"\"hello\" >> $./"});
  }// end method testExecuteWithInvalidFileName

  /**
   * Reset static directory variables.
   */
  @After
  public void tearDown() {
    shellData.resetDirectorySystem();
  }// end method tearDown

}// end class EchoTest
