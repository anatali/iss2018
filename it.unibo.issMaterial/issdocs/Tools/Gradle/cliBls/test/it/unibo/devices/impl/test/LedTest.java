package it.unibo.devices.impl.test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import it.unibo.devices.model.ILed;
import it.unibo.devices.impl.LedMock;
import it.unibo.devices.model.BLSSysKb.LedColor;

public class LedTest {
	private ILed led;
	
	@Before
	public void setTheLed(){
		try {
			LedColor ledColor = LedColor.GREEN;
			assertTrue("color code",  ledColor.getValue()==0 );
			led = new LedMock("l1",ledColor);
 	    	assertTrue("initial led state",  ! led.isOn() );
	    	System.out.println("	*** setTheLed state=" + led.isOn() );
		} catch (Exception e) {
			fail("Led creation");
 		}
	}
	@Test
	public void setTheLedWrong(){
		try {
			led = new LedMock("l1",LedColor.BLUE);
			
			fail("setTheLedWrong");
 		} catch (Exception e) {
			assertTrue("setTheLedWrong",  true );			
 		}
	}
	@Test
	public void setTheLedWithRep(){
		try {
			led = new LedMock("device(led(l1),GREEN,false)" );			
			fail("setTheLedWithRep");
 		} catch (Exception e) {
			assertTrue("setTheLedWithRep",  true );			
 		}
	}
	    @Test 
	    public void testTurn() {
 	    	System.out.println("	*** testTurn " );
  	    	led.turnOn();
 	    	assertTrue("led state after turnOn", led.isOn());
 	    	led.turnOff();
 	    	assertTrue("led state after turnOff", ! led.isOn());
	    }
 	    @Test 
	    public void testSwitch() {
 	    	System.out.println("	*** testSwitch "  );
  	    	led.doSwitch();
 	    	assertTrue("led state after turnOn", led.isOn());
  	    	led.doSwitch();
 	    	assertTrue("led state after turnOn", ! led.isOn());
  	    }
 	    @Test 
	    public void testLedname() {
 	    	System.out.println("	*** testLedname " + led.getName() );
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
