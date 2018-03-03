package it.unibo.akka.hello;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;

public class MainHello {
public static ActorSystem system;
		  public static void main(String[] args) {
			    String configFile="hello_application.conf";
			    Config config = ConfigFactory.parseFile(new File(configFile));
			    System.out.println("hello config="+config);
			    //create an actor system with that config
			    system = ActorSystem.create("HelloSystem" , config);
			    
			    
//			    customConf = ConfigFactory.parseString("""
//			    		akka.actor.deployment {
//			    		/my-service {
//			    		router = round-robin-pool
//			    		nr-of-instances = 3
//			    		}
//			    		}
//			    		""")
			    System.out.println("hello system="+system);

			    /*
			     * A small wrapper around the generic launcher class akka.Main, 
			     * which expects only one argument: the class name of the application’s main actor. 
			     * This main method will then create the infrastructure needed for running the actors, 
			     * start the given main actor and arrange for the whole application
			     * to shut down once the main actor terminates.
			     */
			    akka.Main.main(new String[] { SystemCreationActor.class.getName() });
		  }
		}
