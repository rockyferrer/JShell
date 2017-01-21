package test;

import org.junit.Before;
import org.junit.Test;

import driver.ShellData;

import static org.junit.Assert.assertTrue;

import org.junit.After;

public class ShellDataTest {

  // the mkDir and cd commands are needed to test the Cd class
  private ShellData shellData1, shellData2;

  /**
   * Initialize the variables
   */
  @Before
  public void setUp() {
    shellData1 = new ShellData();
    shellData2 = new ShellData();
  }// end method setUp

  /**
   * Test if the ShellData is of the Singleton pattern
   */
  @Test
  public void testSingleton() {
    assertTrue(shellData1.getHomeDirectory() == shellData2.getHomeDirectory());
  }// end method testSingleton

  /**
   * Resets static directory variables
   */
  @After
  public void tearDown() {
    shellData1.resetDirectorySystem();
    shellData2.resetDirectorySystem();
  }// end method tearDown

}// end ShellDataTest
