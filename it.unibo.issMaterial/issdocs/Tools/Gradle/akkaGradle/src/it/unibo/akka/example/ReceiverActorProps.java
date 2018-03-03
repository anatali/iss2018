package it.unibo.akka.example;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
 
public class ReceiverActorProps extends UntypedActor {
  private LoggingAdapter log = Logging.getLogger( getContext().system(), this);
  private String logo;
  /*
   * Props is a configuration class to specify options for the creation of actors, 
   * think of it as an immutable and thus freely shareable recipe for creating an actor 
   * including associated deployment information
   */
  public static Props props(final String logo) {
	  return Props.create(new Creator<ReceiverActorProps>() {
 		private static final long serialVersionUID = 1L;
 		@Override
		public ReceiverActorProps create() throws Exception {
			  return new ReceiverActorProps(logo);
		  }
	  });
  }
  /*
   * CONSTRUCTOR
   */
  public ReceiverActorProps(String logo){
	  this.logo = logo;
  }
  @Override
  public void onReceive(final Object msg) {
    log.info("ReceiverActor RECEIVES msg: {}", msg);
    getSender().tell(msg+"_"+logo, getSelf());
  }  
}