package it.unibo.akka.ask;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import static akka.pattern.Patterns.ask;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
/*
 here are generally two ways of getting a reply from an UntypedActor.
 
The first is by a sent message (actorRef.tell(msg, sender)), which only works if the original sender 
was an UntypedActor) and the second is through a Future.
 */

public class AskingActor extends UntypedActor {
	public static ActorSystem system;
	public static ActorRef myself;

	@Override
	public void preStart() {
		askModeWithFuture();
	}
	
	protected void askModeWithFuture(){
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Object askmsg   = new AskNameMessage();
		System.out.println("AskingActor asks" );		
		Future<Object> future = ask( SystemCreationActor.called, askmsg, timeout);
		//Future<Object> future = Patterns.ask(SystemCreationActor.called, askmsg, timeout);
		try {
			String result = (String) Await.result(future, timeout.duration());
			/*
This will cause the current thread to block and wait for the UntypedActor to ‘complete’ the Future with it’s
reply. Blocking is discouraged though as it can cause performance problem. The blocking operations are located
in Await.result and Await.ready to make it easy to spot where blocking occurs. Alternatives to blocking
are discussed further within this documentation. Also note that the Future returned by an UntypedActor is a
Future<Object> since an UntypedActor is dynamic. That is why the cast to String is used in the above sample.

To send the result of a Future to an Actor, you can use the pipe construct:
akka.pattern.Patterns.pipe(future, system.dispatcher()).to(actor);
			 */
			System.out.println("AskingActor result="+result);
		} catch (Exception e) {
 			e.printStackTrace();
		}				
	}
//	protected void askMode1(){
//		Object askmsg   = new AskNameMessage();
//		SystemCreationActor.called.tell(askmsg, SystemCreationActor.called);
//	}
	@Override
	public void onReceive(Object arg0) throws Throwable {
//		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
//		Object askmsg = new AskNameMessage();
//		Future<Object> future = Patterns.ask(myself, askmsg, timeout);
//		String result = (String) Await.result(future, timeout.duration());
		
	}

}
