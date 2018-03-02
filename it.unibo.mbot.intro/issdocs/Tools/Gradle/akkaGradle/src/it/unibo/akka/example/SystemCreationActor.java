package it.unibo.akka.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class SystemCreationActor extends UntypedActor{
	public  static ActorRef receiver ;
	public  static  ActorRef sender ;
	public  final  static ActorSystem system = ActorSystem.create();
	
	  @Override
	  public void preStart() {
		//System.out.println(system.settings());	//a lot of output
	    // create the receiver actor
	    receiver = getContext().actorOf(Props.create(ReceiverActor.class), "receiver");
	    sender   = getContext().actorOf(Props.create(SenderActor.class), "sender");
	    /*
	     * AUTO message
	     */
	    getSelf().tell("start", getSelf() );
	  }

	@Override
	public void onReceive(Object message) throws Throwable {
		System.out.println("SystemCreationActor receives " + message);
		sender.tell("start", getSelf() );
 	}

}
