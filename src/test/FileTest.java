package test;

import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by Felipe on 06/03/16.
 * 
 * The FileTest class contains tests for the File class.
 */
public class FileTest {

  // a file and a directory will be required to test the File class
  File f;
  Directory d;

  /**
   * Initialize the variables
   */
  @Before
  public void setUp() {
    f = new File("f");
    d = new Directory("d");
  }// end method setUp

  /**
   * Tests getter and setter for content
   */
  @Test
  public void testContent() {
    f.setContent("hi");
    assertEquals(f.getContent(), "hi");
  }// end method testContent

  /**
   * Tests getter for name
   */
  @Test
  public void testGetName() {
    assertEquals(f.getName(), "f");
  }// end method testGetName

  /**
   * Tests getter for parent
   */
  @Test
  public void testParent() {
    assertEquals(f.getParent(), null);
    d.addSubObject(f);
    assertEquals(f.getParent(), d);
  }// end method testParent

  /**
   * Tests setter for name
   */
  @Test
  public void testSetName() {
    f.setName("a");
    assertEquals(f.getName(), "a");
  }// end method testSetName

}// end class FileTest
