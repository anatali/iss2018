package it.unibo.devices.appl.test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import it.unibo.devices.model.ILed;
import it.unibo.devices.appl.MainLed;
 

public class ApplTest {
	private MainLed appl;
	private ILed led;
	
	@Before
	public void setTheAppl(){
		try {
			appl = new MainLed();
			led  = appl.getTheLed();
  	    	assertTrue("initial led state",  ! led.isOn() );
 		} catch (Exception e) {
			fail("appl creation");
 		}
	}
 	    @Test 
	    public void testDoJob() {
 	    	System.out.println("	*** testDoJob " );
 	    	appl.doJob();
 	    	assertTrue("led state after doJob", led.isOn());
 	    }
  
}
