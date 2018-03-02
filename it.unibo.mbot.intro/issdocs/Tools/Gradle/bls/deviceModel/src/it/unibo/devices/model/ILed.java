package it.unibo.devices.model;
import it.unibo.devices.model.BLSSysKb;
/*
* -----------------------------------------------------------
* This is a first model of the Led:
* a Led is a Device with a LedColor and a internal binary state
* -----------------------------------------------------------
*/
public interface ILed  extends IDevice{	
  	public void doSwitch(); //non-primitive
  	public void turnOn(); // modifier
	public void turnOff(); // modifier
 	public BLSSysKb.LedColor getLedColor(); //property
	public boolean isOn(); //property	
}