package it.unibo.devices.appl;
import it.unibo.devices.model.BLSSysKb;
import it.unibo.devices.model.DevLed;
import it.unibo.devices.model.ILed;
import it.unibo.devices.impl.ALedMock;

public class MainLed  {
private ILed led ;

	public MainLed() throws Exception {
		BLSSysKb.LedColor ledColor = BLSSysKb.LedColor.GREEN;
 		ILed ledImpl = new ALedMock("l1", ledColor);
 		led = new DevLed( null,ledImpl );
	} 
	public ILed getTheLed(){
		return led;
	}
	
	public void doJob(){
		 try {
			System.out.println("START doJob:" + led.getDefaultRep());
			led.doSwitch();
			System.out.println("END doJob:  " + led.getDefaultRep());
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		try {
			new MainLed().doJob();
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
 
}
