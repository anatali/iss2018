package it.unibo.akka.ask;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
 

public class SystemCreationActor extends UntypedActor {
	public static ActorSystem system;
	public static ActorRef caller;
	public static ActorRef called;

	@Override
	public void preStart() {
		System.out.println("	*** AskingActor " + getSelf() + " STARTS "  );
//		String configFile="filesize_application.conf";
//	    Config config = ConfigFactory.parseFile(new File(configFile));
//	    System.out.println("config="+config);
 		system = ActorSystem.create("AskActorSystem"   );
 		called = system.actorOf( Props.create(CalledActor.class), "calledActor");
 		caller = system.actorOf( Props.create(AskingActor.class), "askingActor");
 		System.out.println("	*** AskingActor " + getSelf() + " ENDS "  );
	}

	@Override
	public void onReceive(Object arg0) throws Throwable {
 		
	}

}
