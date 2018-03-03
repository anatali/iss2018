package it.unibo.akka.ask;

public class MainAkkaAsk {

	public static void main(final String[] args) {
	    System.out.println("MainAkkaAsk");
	    akka.Main.main(new String[] { SystemCreationActor.class.getName() });
	}

}

/*
A common use case within Akka is to have some computation performed concurrently without needing the extra
utility of an UntypedActor. If you find yourself creating a pool of UntypedActors for the sole reason of
performing a calculation in parallel, there is an easier (and faster) way:
*/