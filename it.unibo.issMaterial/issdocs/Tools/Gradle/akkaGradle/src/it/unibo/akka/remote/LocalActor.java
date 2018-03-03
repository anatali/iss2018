package it.unibo.akka.remote;
import akka.actor.ActorSelection;
import akka.actor.UntypedActor;

public class LocalActor extends UntypedActor{
	private ActorSelection rasel;
	private int count = 0;
	  @Override
	  public void preStart() {
		    rasel  = getContext().actorSelection("akka.tcp://RemoteSystem@127.0.0.1:5150/user/remoteActor");
		    System.out.println("LocalActor " + rasel);
		    rasel.tell("hello from LocalActor_"+count++, getSelf() );
	  }

	@Override
	public void onReceive(Object msg) throws Throwable {
		System.out.println("LocalActor receives: " + msg + " from " + sender() );
		if( count < 5 ){
			rasel.tell("hello from LocalActor_"+count++, getSelf() );
		}
	}

}
