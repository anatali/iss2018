package it.unibo.akka.ask;
import akka.actor.UntypedActor;

public class CalledActor extends UntypedActor {
	

	@Override
	public void onReceive(Object message) throws Throwable {
		if( message instanceof  AskNameMessage){
		//respond to the "ask" request
			getSender().tell("Fred", getSelf());
		}else{
			unhandled(message);
		}
		
	}
	
}
