package it.unibo.akka.remote;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MainLocal {

	  public static void main(String[] args) {
		    System.out.println("MainLocal");
//		    akka.Main.main(new String[] { LocalActor.class.getName() });
		    
			    String configFile="local_application.conf";
			    Config config = ConfigFactory.parseFile(new File(configFile));
			    System.out.println("local config="+config);
			    //create an actor system with that config
			    ActorSystem system = ActorSystem.create("LocalSystem" , config);
			    System.out.println("local system="+system);
			    ActorRef remote = system.actorOf(Props.create(LocalActor.class), "localActor");
	  }

}
