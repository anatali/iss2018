package it.unibo.akka.remote;
import akka.actor.UntypedActor;

public class RemoteActor extends UntypedActor{
	private int count = 0;
 	
	  @Override
	  public void preStart() {
		    System.out.println("remote is ready");
		    //Once you create actor, it will be available at akka.tcp://RemoteSystem@127.0.0.1:5150/user/remoteActor#-3777244.
	  }
	
	@Override
	public void onReceive(Object msg) throws Throwable {
		  if (msg instanceof String) {
			   System.out.println(" RemoteActor RECEIVES msg: " + msg + " from " + sender());
  			   sender().tell("RemoteActor answer_"+count++, getSelf());
 		  }else{
			   unhandled(msg);
		  }
	}
}
