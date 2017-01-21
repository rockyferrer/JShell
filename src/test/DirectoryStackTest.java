package test;

import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.DirectoryStack;

import static org.junit.Assert.assertEquals;

/**
 * Created by Abhishek on 06/03/16.
 * 
 * The DirectoryStackTest class contains tests for the DirectoryStack class.
 */
public class DirectoryStackTest {

  // a directory stack and many directories will
  // be used to test the DirectoryStack class
  private DirectoryStack dirStack;
  Directory dir1, dir2, dir3, dir4, dir5;

  /**
   * Initialize the variables and set up the directory and directory stack.
   */
  @Before
  public void setUp() throws Exception {
    dirStack = new DirectoryStack();
    dir1 = new Directory("dir1");
    dir2 = new Directory("dir2");
    dir3 = new Directory("dir3");
    dir4 = new Directory("dir4");
    dir5 = new Directory("dir5");
  }// end method setUp

  /**
   * Test the isEmpty method.
   */
  @Test
  public void testIsEmpty() throws Exception {
    assertEquals(true, dirStack.isEmpty());
    dirStack.push(dir1);
    assertEquals(false, dirStack.isEmpty());
  }// end method testIsEmpty

  /**
   * Test the push and pop methods.
   */
  @Test
  public void testPushAndPop() throws Exception {
    dirStack.push(dir1);
    assertEquals(dir1, dirStack.pop());
  }// end method testPushAndPop

  /**
   * Test the push and pop methods twice.
   */
  @Test
  public void testCouplePushAndPop() throws Exception {
    dirStack.push(dir1);
    dirStack.push(dir2);
    assertEquals(dir2, dirStack.pop());
    assertEquals(dir1, dirStack.pop());
  }// end method testCouplePushAndPop

  /**
   * Test the push and pop methods many times.
   */
  @Test
  public void testMultiplePushAndPop() throws Exception {
    dirStack.push(dir1);
    dirStack.push(dir2);
    dirStack.push(dir3);
    dirStack.push(dir4);
    dirStack.push(dir5);
    assertEquals(dir5, dirStack.pop());
    assertEquals(dir4, dirStack.pop());
    assertEquals(dir3, dirStack.pop());
    assertEquals(dir2, dirStack.pop());
    assertEquals(dir1, dirStack.pop());
  }// end method testMultiplePushAndPop

  /**
   * Test the pop method without a push.
   */
  @Test
  public void testNullPop() throws Exception {
    assertEquals(null, dirStack.pop());
  }// end method testNullPop


}// end class DirectoryStackTest
