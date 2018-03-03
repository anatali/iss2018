package it.unibo.devices.model;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.system.SituatedPlainObject;
import it.unibo.devices.model.BLSSysKb.LedColor;

public class DevLed  extends SituatedPlainObject implements ILed{
protected ILed  ledImpl;

 	public DevLed( IOutputView outView, ILed  ledImpl  ){
		super(ledImpl.getName(),outView);
		this.ledImpl = ledImpl;
 	}
 	@Override
	public void turnOn() {
		ledImpl.turnOn();
 	}
	@Override
	public void turnOff() {
		ledImpl.turnOff();
 	}
	@Override
	public boolean isOn() {
 		return ledImpl.isOn();
	}
	@Override
	public void doSwitch() {
		ledImpl.doSwitch();		
	} 	 
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public String getDefaultRep() {
 		return ledImpl.getDefaultRep();
	}
	@Override
	public LedColor getLedColor() {
 		return ledImpl.getLedColor();
	}
 }
