package com.openlobby.test.launcher

import com.openlobby.launcher.Launcher
import java.io.File
import org.junit._
import Assert._

@Test
class AppTest {
  
  @Test
  def loadBundle() {
    Launcher.installBundle("listener", new File("./").getParentFile)
  }
    
  @Test
  def testGetService() {
   
    //val fail = launcher.getService(new Object)
    //Assert.assertNull(fail)
  }

}


