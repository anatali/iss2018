package it.unibo.akka.hello;
 
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
 
public class SystemCreationActor extends UntypedActor{
	public static ActorRef helloActor;
	public static ActorRef greetActor;
	
	@Override
	public void preStart() {
 		greetActor =  getContext().actorOf(Props.create(GreeterActor.class), "greeter");
		System.out.println("   *** SystemCreationActor greetActor=" + greetActor );
	    helloActor = 
//	    		getContext().actorOf(Props.create(HelloWorldActor.class).withDispatcher("my-dispatcher"), "greeter");
				getContext().actorOf(Props.create(HelloWorldActor.class), "hello");
	}
 
	@Override
	public void onReceive(Object arg0) throws Throwable {
 		
	}

}
