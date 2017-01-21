package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.PathObject;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Felipe on 07/03/16.
 * 
 * The PathObjectTest class contains tests for the PathObject class.
 */
public class PathObjectTest {


  // a path object and a directory are required to test the PathObject class
  PathObject po;
  Directory d;

  /**
   * Initialize the variables
   */
  @Before
  public void setUp() {
    po = new PathObject("po");
    d = new Directory("d");
  }// end method setUp

  /**
   * Tests setter for name
   */
  @Test
  public void testSetName() throws Exception {
    po.setName("a");
    Assert.assertEquals(po.getName(), "a");
  }// end method testSetName

  /**
   * Tests getter for parent
   */
  @Test
  public void testGetParent() throws Exception {
    Assert.assertEquals(po.getParent(), null);
    d.addSubObject(po);
    Assert.assertEquals(po.getParent(), d);
  }// end method testGetParent

  /**
   * Tests getter for name
   */
  @Test
  public void testGetName() throws Exception {
    assertEquals(po.getName(), "po");
  }// end method testGetName

}// end class PathObjectTest
