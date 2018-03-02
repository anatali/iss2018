package it.unibo.akka.example;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;

public class SenderActor extends UntypedActor {
private ActorRef receiver        = SystemCreationActor.receiver;
private ActorSelection rasel     = getContext().actorSelection("../receiver");
private ActorSelection rase2     = SystemCreationActor.system.actorSelection("receiver");
private int count = 0;
private LoggingAdapter log = Logging.getLogger( getContext().system(), this);

	  public static enum Msg {
		    INFO, DONE;
		  }

	  @Override
	  public void onReceive(Object msg) {
		  if (msg instanceof String) {
//			    System.out.println("SenderActor RECEIVES " + msg + 
//			    	      " from Thread " + Thread.currentThread().getName());			
			    
			    
			    log.info("SenderActor RECEIVES msg: {}", msg);
 			    if( msg.equals("start")) {
 			    	System.out.println(   " receiver="+receiver  );
 			    	System.out.println(   " rase1=" + rasel  );
 			    	System.out.println(   " rase2=" + rase2  ); 
 			    }
			    if( count < 5){
			    	try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
 					}
			    	receiver.tell(Msg.INFO+"_"+count++, getSelf());
			    	rasel.tell(Msg.INFO+"_"+count++, getSelf());
			    	rase2.tell(Msg.INFO+"_"+count++, getSelf());
			    }
		  }else{
			   unhandled(msg);
		  }
	  }
}