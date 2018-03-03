package it.unibo.akka.example;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
 
public class ReceiverActor extends UntypedActor {
  private LoggingAdapter log = Logging.getLogger( getContext().system(), this);
  
  public void onReceive(final Object msg) {
    log.info("ReceiverActor RECEIVES msg: {}", msg);
    getSender().tell(msg+"_ack", getSelf());
  }  
}