package it.unibo.qatest;
import alice.tuprolog.SolveInfo;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.ActorContext;
import it.unibo.qactors.akka.QActor;
import it.unibo.qactors.planned.QActorPlanned;
import it.unibo.qactors.action.AsynchActionResult;

public class QActorHello extends QActorPlanned{
	public QActorHello(String actorId, ActorContext actx, IOutputEnvView outEnvView) {
		super(actorId, actx, null,"./WorldTheory.pl",outEnvView,null);
 	}

	@Override
	protected void doJob() throws Exception {
		explain();
		helloNaive();
		helloSound();
		helloMessageFifo();
		helloUsingTheory();
		helloMessageSelect();
  	}
	
  	protected void explain( ){
		println(" **************************************************************************************************** %"  );
		println(" print a sentence   ");
		println(" play a sound in (a)synchronous way    ");
		println(" print a message sent to itself by receiving the first message in the queue with receiveMsg/0 ");
		println(" print a message previously stored in the actor theory");
		println(" print a message sent to itself by receiving with a message-selection receiveMsg/7 "); 
		println(" **************************************************************************************************** %"  );
 		
  	}

	/*
	 * Just print a sentence
	 */
	protected void helloNaive(){
		println("Hello ");
	}
	/*
	 * Play a sound in (a)synchronous way
	 */
	protected void helloSound() throws Exception{
		println("helloSound");
		playSound("./audio/computer_complex3.wav", 3000, "", "", "");
	}
	/*
	 * Print a message sent to itself by receiving the first message in the queue with receiveMsg/0 
	 */
	protected void helloMessageFifo() throws Exception{
		sendMsg("info", getName(), ActorContext.dispatch, "'hello from myself'");
		String msg = receiveMsg();  //blocking
		println("helloMessageFifo:"+msg);
	}
	/*
	 *  Print a message previously stored in the actor theory
	 */
	protected void helloUsingTheory() throws Exception{
		addRule("helloMessage('hello stored in the actor theory')");
		SolveInfo sol = this.pengine.solve("helloMessage(X).");
		if( sol.isSuccess() ){ println("helloUsingTheory: " + sol.getVarValue("X"));
		}else{ println("Goal failed");}		
	}
	/*
	 * Print a message sent to itself by receiving with a message-selection receiveMsg/7  
	 */
	protected void helloMessageSelect() throws Exception{
		println("helloMessageSelect sends a request");
		sendMsg("info", getName(), ActorContext.request,  "'hello request 1'");
		println("helloMessageSelect sends a dispatch");
		sendMsg("info", getName(), ActorContext.dispatch, "'hello dispatch 2'");
		println("helloMessageSelect gets the dispatch a dispatch");
		AsynchActionResult aar = 
			receiveMsg("MSGID", ActorContext.dispatch, "SENDER", "RECEIVER", "X", "N", 3000, "","");
		println("helloMessageSelect dispatch received " + aar.getResult() +" time remained=" + aar.getTimeRemained() );
		 aar = 
				receiveMsg("MSGID", ActorContext.request, "SENDER", "RECEIVER", "X", "N", 3000, "","");
			println("helloMessageSelect request received " + aar.getResult() +" time remained=" + aar.getTimeRemained() );
	}
}
