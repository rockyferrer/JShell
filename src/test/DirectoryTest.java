package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import driver.Directory;
import driver.Exceptions.NullPathException;
import driver.File;
import driver.PathObject;
import driver.ShellData;

import static org.junit.Assert.assertEquals;

/**
 * Created by Abhishek on 06/03/16.
 *
 * The DirectoryTest class contains tests for the Directory class.
 */
public class DirectoryTest {

  // a directory will be used to test the Directory class
  Directory dir1;
  private ShellData shellData;

  /**
   * Initialize the variables.
   */
  @Before
  public void setUp() throws Exception {
    shellData = new ShellData();
    dir1 = new Directory("dir1");
  }// end method setUp

  /**
   * Test the getCurrentDirectory method.
   */
  @Test
  public void testGetCurrentDirectory() throws Exception {
    assertEquals(shellData.getHomeDirectory(), shellData.getCurrentDirectory());
  }// end method testSetCurrentDirectory

  /**
   * Test the setCurrentDirectory method.
   */
  @Test
  public void testSetCurrentDirectory() throws Exception {
    shellData.setCurrentDirectory(dir1);
    assertEquals(dir1, shellData.getCurrentDirectory());
  }// end method testGetCurrentDirectory

  /**
   * Test the getHomeDirectory method.
   */
  @Test
  public void testGetHomeDirectory() throws Exception {
    assertEquals(shellData.getCurrentDirectory(), shellData.getHomeDirectory());
  }// end method testGetHomeDirectory

  /**
   * Test the addSubObject method.
   */
  @Test
  public void testAddSubObject() throws Exception {
    LinkedList<PathObject> list = dir1.getSubObjects();
    File f = new File("file");
    dir1.addSubObject(f);
    assertEquals(list.remove(), f);
  }// end method testAddSubObject

  /**
   * Test the removeSubObject method.
   */
  @Test
  public void testRemoveSubObject() throws Exception {
    LinkedList<PathObject> list = dir1.getSubObjects();
    File f1 = new File("file1");
    File f2 = new File("file2");
    dir1.addSubObject(f1);
    dir1.addSubObject(f2);
    dir1.removeSubObject(f2);
    assertEquals(list.remove(), f1);
  }// end method testRemoveSubObject

  /**
   * Test the toString method.
   */
  @Test
  public void testToString() throws Exception {
    String str = dir1.toString();
    assertEquals(dir1.getName(), str);
  }// end method testToString

  /**
   * Tests several cases for the convertStringToPathArray method.
   */
  @SuppressWarnings("deprecation")
  @Test
  public void testConvertStringToPathArray() throws Exception {
    // test absolute path
    assertEquals(Directory.convertStringToPathArray("/a/b/c"),
        new String[] {"/", "a", "b", "c"});

    // test that several slashes returns a single slash
    assertEquals(Directory.convertStringToPathArray("///////////"),
        new String[] {"/"});

    // test that several slashes at beginning returns single slash at beginning
    assertEquals(Directory.convertStringToPathArray("///////////a"),
        new String[] {"/", "a"});

    // test that several slashes in middle returns no slashes
    assertEquals(Directory.convertStringToPathArray("a//////b"),
        new String[] {"a", "b"});
  }

  /**
   * Test several good cases for the convertPathArrayToObject method
   */
  @Test
  public void testConvertPathArrayToObject() throws Exception {
    Directory home = shellData.getHomeDirectory();
    Directory dA = new Directory("a");
    Directory dB = new Directory("b");
    home.addSubObject(dA);
    dA.addSubObject(dB);

    // test full path to a
    assertEquals(
        Directory.convertPathArrayToObject(new String[] {"/", "a"}, shellData),
        dA);

    // test full path to b
    assertEquals(Directory
        .convertPathArrayToObject(new String[] {"/", "a", "b"}, shellData), dB);

    // test full to home
    assertEquals(
        Directory.convertPathArrayToObject(new String[] {"/"}, shellData),
        home);

    // test relative path to home
    assertEquals(Directory.convertPathArrayToObject(new String[] {}, shellData),
        home);

    // test relative path to a
    assertEquals(
        Directory.convertPathArrayToObject(new String[] {"a"}, shellData), dA);
  }// end method testConvertPathArrayToObject

  /**
   * Tests convertPathArrayToObject using the special strings "." and ".."
   */
  @Test
  public void testConvertPathArrayToObjectWithSpecialStrings()
      throws Exception {
    Directory home = shellData.getHomeDirectory();
    Directory dA = new Directory("a");
    Directory dB = new Directory("b");
    home.addSubObject(dA);
    dA.addSubObject(dB);
    shellData.setCurrentDirectory(dA);

    // Test for case ".."
    assertEquals(
        Directory.convertPathArrayToObject(new String[] {".."}, shellData),
        home);

    // Test for case "."
    assertEquals(
        Directory.convertPathArrayToObject(new String[] {"."}, shellData), dA);

    // Test for case with more than one ".."
    shellData.setCurrentDirectory(dB);
    assertEquals(Directory.convertPathArrayToObject(new String[] {"..", ".."},
        shellData), home);
  }// end method testConvertPathArrayToObjectWithSpecialCharacters

  /**
   * Tests convertPathArrayToObject with {".."} as the parameter with the home
   * directory as the current directory
   *
   * Expects a NullPathException to be thrown
   */
  @Test(expected = NullPathException.class)
  public void testConvertPathArrayToObjectEmptyParent() throws Exception {
    shellData.getHomeDirectory();
    Directory.convertPathArrayToObject(new String[] {".."}, shellData);
  }// end method testConvertPathArrayToObjectEmptyParent

  /**
   * Tests convertPathArrayToObject on a path that does not exist
   *
   * Expects a NullPathException to be thrown
   */
  @Test(expected = NullPathException.class)
  public void testConvertPathArrayToObjectEmptyPath() throws Exception {
    shellData.getHomeDirectory();
    Directory.convertPathArrayToObject(new String[] {"a"}, shellData);
  }// end method testConvertPathArrayToObjectEmptyPath

  /**
   * Reset static directory variables.
   */
  @After
  public void tearDown() {
    shellData.resetDirectorySystem();
  }// end method tearDown

}// end class DirectoryTest
