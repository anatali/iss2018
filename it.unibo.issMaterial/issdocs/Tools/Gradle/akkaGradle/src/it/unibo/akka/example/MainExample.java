package it.unibo.akka.example;

public class MainExample {
/*
The generic launcher class akka.Main expects only one command line argument:
the class name of the application’s main actor. This main method will then create the infrastructure needed for
running the actors, start the given main actor and arrange for the whole application to shut down once the main
actor terminates.
 */
	  public static void main(String[] args) {
		    System.out.println("MainExample");
		    akka.Main.main(new String[] { SystemCreationActor.class.getName() });
	  }

}
