package it.unibo.akka.fileSize;
import java.io.File;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
 

public class SystemCreationActor extends UntypedActor{
	public static ActorSystem system;
	public static ActorRef myself;
	
	@Override
	public void preStart() {
		System.out.println("	*** SystemCreationActor " + getSelf() + " STARTS "  );
		myself = getSelf();
		String configFile="filesize_application.conf";
	    Config config = ConfigFactory.parseFile(new File(configFile));
	    System.out.println("config="+config);
 		system = ActorSystem.create("AkkaFileSizeSystem" , config );
		ActorRef sizeCollector = system.actorOf( Props.create(SizeColectorActor.class), "sizeCollectorActor");
		
		for(int i = 0; i < 5; i++){
			//System.out.println("SystemCreationActor creates " + "worker"+i);
			// ActorRef worker = 
				 system.actorOf( Props.create(FileProcessorActor.class ), "worker"+i);
		}
 
		sizeCollector.tell( new FileToProcessMsg("C:/mfg"), getSelf() );
	}
	@Override
	public void onReceive(Object message) throws Throwable {
		System.out.println("	*** SystemCreationActor receives " + message);	
		if( message instanceof TerminateMsg){
			System.out.println("SystemCreationActor TERMINATE "   );
			getContext().stop(getSelf());
 			//scala.concurrent.Future<Terminated> f = system.shutdown();
			//System.out.println("SystemCreationActor TERMINATE " + f.isCompleted() );			 
			
		}
	}

}
