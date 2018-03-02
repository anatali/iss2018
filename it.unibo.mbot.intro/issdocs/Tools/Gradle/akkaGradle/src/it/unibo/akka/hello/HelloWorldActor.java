package it.unibo.akka.hello;

import akka.actor.UntypedActor;


/*
 * The HelloWorld actor is the application’s “main” class; 
 * when it terminates the application will shut down—more on that later. 
 * The main business logic happens in the preStart method, 
 * where a Greeter actor is created and instructed to issue that greeting we crave for. 
 * When the greeter is done it will tell us so by sending back a message, 
 * and when that message has been received it will be passed into the behavior 
 * described by the onReceive method where we can conclude 
 * the demonstration by stopping the HelloWorld actor.
 */
public class HelloWorldActor extends UntypedActor {
 
	  @Override
	  public void preStart() {
// 	    // create the greeter actor
//	    final ActorRef greeter = 
//	    		getContext().actorOf(Props.create(GreeterActor.class).withDispatcher("my-dispatcher"), "greeter");
	    // tell it to perform the greeting
		  System.out.println("HelloWorldActor  greetActor="+SystemCreationActor.greetActor);
		 SystemCreationActor.greetActor.tell(GreeterActor.Msg.GREET, getSelf());
	  }

	  @Override
	  public void onReceive(Object msg) {
	    if (msg == GreeterActor.Msg.DONE) {
	      // when the greeter is done, stop this actor and with it the application
	      getContext().stop(getSelf());
	    } else
	      unhandled(msg);
	  }
	}
