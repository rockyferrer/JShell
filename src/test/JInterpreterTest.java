package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Cd;
import driver.Directory;
import driver.JInterpreter;
import driver.MkDir;
import driver.ShellData;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sajjad on 06/03/16.
 * 
 * The JInterpreterTest class contains tests for the JInterpreter class.
 */
public class JInterpreterTest {

  // the cd command and an interpreter are required
  // to test the JInterpreter class
  private JInterpreter interpreter;
  private Cd cd;
  private MkDir mkdir;
  private ShellData shellData;

  /**
   * Initialize the variables.
   */
  @Before
  public void setUp() {
    shellData = new ShellData();
    interpreter = new JInterpreter(shellData);
    cd = new Cd(shellData);
    mkdir = new MkDir(shellData);
  }// end method setUp

  /**
   * Test cd command.
   */
  @Test
  public void testCd() {
    Directory expected = shellData.getCurrentDirectory();
    interpreter.executeCommand("cd .");
    Directory result = shellData.getCurrentDirectory();
    assertEquals(result, expected);
  }// end method testCd

  /**
   * Test pwd command.
   */
  @Test
  public void testPwd() throws Exception {
    cd.execute(new String[] {"/"});
    String result = interpreter.executeCommand("pwd");
    assertEquals(result, "/\n");
  }// end method testPwd

  /**
   * Test echo command.
   */
  @Test
  public void testEcho() {
    String result1 = interpreter.executeCommand("echo \"hello\"");
    assertEquals(result1, "hello\n");
    String result2 = interpreter.executeCommand("echo \"hello\" >> file1");
    assertEquals(result2, "");
    String result3 = interpreter.executeCommand("echo \"hello\" > file2");
    assertEquals(result3, "");
    String result4 = interpreter.executeCommand("cat file1");
    assertEquals(result4, "hello\n");
    String result5 = interpreter.executeCommand("cat file2");
    assertEquals(result5, "hello\n");
    String result6 = interpreter.executeCommand("echo \"hello\" > file1");
    assertEquals(result6, "");
    String result7 = interpreter.executeCommand("echo \"hello\" >> file2");
    assertEquals(result7, "");
    String result8 = interpreter.executeCommand("cat file1");
    assertEquals(result8, "hello\n");
    String result9 = interpreter.executeCommand("cat file2");
    assertEquals(result9, "hello\nhello\n");
  }// end method testPwd

  /**
   * Test mkdir and ls commands.
   */
  @Test
  public void testMkDirAndLs() {
    String actual1 = interpreter.executeCommand("mkdir dir1");
    String actual2 = interpreter.executeCommand("ls");
    String expected1 = "";
    String expected2 = "dir1 \n";
    assertEquals(actual1, expected1);
    assertEquals(actual2, expected2);
  }// end method testMkDirAndLs

  /**
   * Test the pushd and popd commands.
   */
  @Test
  public void testPushdAndPopd() throws Exception {
    mkdir.execute(new String[] {"dir1"});
    String actual1 = interpreter.executeCommand("pushd dir1");
    String expected1 = "";
    String actual2 = interpreter.executeCommand("popd");
    String expected2 = "";
    assertEquals(actual1, expected1);
    assertEquals(actual2, expected2);
  }// end method testPushdAndPopd

  /**
   * Test history command.
   */
  @Test
  public void testHistory() {
    shellData.getHistory().add("history");
    String result = interpreter.executeCommand("history");
    assertEquals(result, "1. history\n");
  }// end method testPwd

  /**
   * Test many commands in incorrect fashion.
   */
  @Test
  public void testManyCommands() {
    String result = interpreter.executeCommand("pwd man");
    String expected = "Invalid amount of arguments\n";
    assertEquals(result, expected);
  }// end method testManyCommands

  /**
   * Test a non-existent command.
   */
  @Test
  public void testFalseCommand() {
    String result = interpreter.executeCommand("toronto");
    String expected = "Command does not exist\n";
    assertEquals(result, expected);
  }// end method testFalseCommand

  /**
   * Test many non-existent commands.
   */
  @Test
  public void testManyFalseCommands() {
    String result = interpreter.executeCommand("toronto cupertino");
    String expected = "Command does not exist\n";
    assertEquals(result, expected);
  }// end method testManyFalseCommands

  /**
   * Reset static variables.
   */
  @After
  public void tearDown() {
    shellData.resetDirectorySystem();
  }// end method tearDown

}// end class JInterpreterTest
