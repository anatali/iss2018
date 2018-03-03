package it.unibo.akka.primes;
 
public class MainAkkaPrimes {

	public static void main(final String[] args) {
	    System.out.println("MainAkkaPrimes");
	    akka.Main.main(new String[] { SystemCreationActor.class.getName() });
	}

}
