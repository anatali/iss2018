package it.unibo.akka.hello;

import akka.actor.UntypedActor;
import scala.concurrent.ExecutionContext;
/*
 *  This actor will not do anything until someone sends it a message
 */
public class GreeterActor extends UntypedActor {

	  public static enum Msg {
	    GREET, DONE;
	  }

	  @Override
	  public void preStart() {
 		 inspect();
	  }
	  protected void inspect(){
//		  final ExecutionContext execCtx = MainHello.system.dispatchers().lookup("akka.actor.default-dispatcher");
		  final ExecutionContext execCtx = MainHello.system.dispatchers().lookup("akka.actor.my-dispatcher");
		  System.out.println("   *** GreeterActor execCtx=" + execCtx );
	  }
	  
	  @Override
	  public void onReceive(Object msg) {
	    if (msg == Msg.GREET) {
	      System.out.println("Hello World!");
	      getSender().tell(Msg.DONE, getSelf());
	    } else
	      unhandled(msg);
	  }

	}
