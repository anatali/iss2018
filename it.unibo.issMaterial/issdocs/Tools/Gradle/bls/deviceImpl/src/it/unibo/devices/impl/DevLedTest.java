package it.unibo.devices.model.test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import it.unibo.devices.model.ILed;
import it.unibo.devices.impl.ALedMock;
import it.unibo.devices.model.BLSSysKb.LedColor;
import it.unibo.devices.model.DevLed;

public class DevLedTest {
	private ILed led;
	
	@Before
	public void setTheLed(){
		try {
			ALedMock ledImpl = new ALedMock("l1",LedColor.GREEN);
			led = new DevLed( null,ledImpl );
 	    	assertTrue("initial led state",  ! led.isOn() );
		} catch (Exception e) {
			fail("Led creation");
 		}
	}

 	    @Test 
	    public void testTurn() {
  	    	led.turnOn();
 	    	assertTrue("led state after turnOn", led.isOn());
 	    	led.turnOff();
 	    	assertTrue("led state after turnOff", ! led.isOn());
	    }
 	    @Test 
	    public void testSwitch() {
  	    	led.doSwitch();
 	    	assertTrue("led state after turnOn", led.isOn());
  	    	led.doSwitch();
 	    	assertTrue("led state after turnOn", ! led.isOn());
  	    }
 	    @Test 
	    public void testLedname() {
 	    	assertTrue("led rep", led.getName().equals("led(l1)"));
  	    }
 	    @Test 
	    public void testLedRep() {
	    	String ledRep = led.getDefaultRep();
	    	System.out.println("	*** testLedRep " + ledRep );
	    	assertTrue("led rep", ledRep.equals("device(led(l1),GREEN,false)"));
  	    }
	    @Test 
	    public void testGetLedColor() {
	    	LedColor lc = led.getLedColor();
	    	assertTrue("led color", lc == LedColor.GREEN );
	    }

}
