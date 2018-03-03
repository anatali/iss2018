package it.unibo.akka.fileSize;
import java.util.ArrayList;
import java.util.List;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.UntypedActor;

public class SizeColectorActor extends UntypedActor {
	private final List<String> toProcessFileNames   = new ArrayList<String>();	//directories to visit
	private final List<ActorRef> idleFileProcessors = new ArrayList<ActorRef>();
	private long pendingNumberOfFilesToVisit = 0L;	
	private long totalSize = 0L;
	private long start = System.nanoTime();
	
 
	public void sendAFileToProcess() {
		System.out.println("SizeColectorActor must process " + toProcessFileNames.size() + " files" );
		if(!toProcessFileNames.isEmpty() && !idleFileProcessors.isEmpty())
			idleFileProcessors.remove(0).tell(
					new FileToProcessMsg(toProcessFileNames.remove(0)), getSelf());
	}	
	
	@Override
	public void onReceive(Object message) throws Throwable {
 		
		if (message instanceof RequestAFileMsg) {	//A FileProcessors starts or becomes idle
			System.out.println("SizeColectorActor receives RequestAFileMsg from " + getSender() );
			idleFileProcessors.add( getSender() );
			if(pendingNumberOfFilesToVisit > 0) sendAFileToProcess();
		}
		
		if (message instanceof FileToProcessMsg) {	//A FileProcessors discovered subdirectories
			System.out.println("SizeColectorActor receives FileToProcessMsg:" + ((FileToProcessMsg)message).fileName );
			toProcessFileNames.add(((FileToProcessMsg)(message)).fileName);
			pendingNumberOfFilesToVisit += 1;
			sendAFileToProcess();
		}		
		if (message instanceof FileSizeMsg) {	//A FileProcessors sends the size of files in a directory explored
//			System.out.println("SizeColectorActor receives FileSizeMsg:" +((FileSizeMsg)(message)).size  );
			totalSize += ((FileSizeMsg)(message)).size;
			pendingNumberOfFilesToVisit -= 1;
			if(pendingNumberOfFilesToVisit == 0) {	//isolated mutable counter = 0 means end of the process
				long end = System.nanoTime();
				System.out.println("Total size is " + totalSize);
				System.out.println("Time taken is " + (end - start)/1.0e9);
				//Terminate the main actor
				ActorSelection as = getContext().actorSelection("/Main/user/app");
				System.out.println("	+++ as= " + as );
				//as.tell( new TerminateMsg(), getSelf() );
				SystemCreationActor.myself.tell( new TerminateMsg(), getSelf() );
				 
			}
		}
	}//onReceive

}
