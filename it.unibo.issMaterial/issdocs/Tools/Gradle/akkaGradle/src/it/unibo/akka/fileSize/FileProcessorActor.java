package it.unibo.akka.fileSize;
import java.io.File;
import akka.actor.ActorSelection;
import akka.actor.UntypedActor;

public class FileProcessorActor extends UntypedActor{
	private ActorSelection collector;
 	
	@Override 
	public void preStart() { 
		System.out.println("	*** FileProcessorActor " + this.getSelf()+ " STARTS "  );//+ "rasel=" + rasel
		collector = getContext().actorSelection("/user/sizeCollectorActor");
		registerToGetFile(); 
	}
	
	public void registerToGetFile() {
		collector.tell( new RequestAFileMsg(), getSelf() );
	}
	
	@Override
	public void onReceive(Object message) throws Throwable {
		FileToProcessMsg fileToProcess = (FileToProcessMsg) message;
		final File file = new java.io.File(fileToProcess.fileName);
		long size = 0L;
		if(file.isFile()) {
			size = file.length();
		} else {
			File[] children = file.listFiles();
			System.out.println("	*** FileProcessorActor " + this.getSelf()+ " must process the DIRECTORY " + fileToProcess.fileName);
			if (children != null)
				for(File child : children)
					if (child.isFile()){
//						System.out.println("	*** FOUND file sise=" + child.length() + " for " + child.getName());
						size += child.length();
					}else{
						//Another directory
						collector.tell(new FileToProcessMsg(child.getPath()), getSelf());
					}
		}
		collector.tell(new FileSizeMsg(size), getSelf());
		System.out.println("	*** FileProcessorActor " + this.getSelf()+ " IS IDLE AGAIN "  );
		registerToGetFile(); 		
	}

}
