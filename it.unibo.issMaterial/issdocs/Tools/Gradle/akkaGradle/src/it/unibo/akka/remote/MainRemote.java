package it.unibo.akka.remote;

import java.io.File;
import java.net.URL;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MainRemote {

	  public static void main(String[] args) {
		    System.out.println("MainRemote");
			  String configFile="remote_application.conf";
 			    Config config = ConfigFactory.parseFile(new File(configFile));
			    System.out.println("remote config="+config);
			    //create an actor system with that config
			    ActorSystem system = ActorSystem.create("RemoteSystem" , config);
			    System.out.println("remote system="+system);
		    
 			    ActorRef remote = system.actorOf(Props.create(RemoteActor.class), "remoteActor");
			    //akka.Main.main(new String[] { RemoteActor.class.getName() });
	  }

}
