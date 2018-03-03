package it.unibo.devices.appl;
import it.unibo.devices.model.BLSSysKb.LedColor;
import it.unibo.devices.model.DevLed;
import it.unibo.devices.model.ILed;
import it.unibo.devices.impl.LedMock;

public class MainLed  {
private ILed led ;

	public MainLed() throws Exception {
		ILed ledImpl = new LedMock("l1",LedColor.GREEN);
		led = new DevLed( null,ledImpl );
	} 
	public ILed getTheLed(){
		return led;
	}
	public void doJob(){
 		System.out.println("START doJob:" + led.getDefaultRep());
		led.doSwitch();
		System.out.println("END doJob:  " + led.getDefaultRep());
 	}	 
	public static void main(String[] args){
		try {
			new MainLed().doJob();
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
 
}
