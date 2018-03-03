package it.unibo.akka.fileSize;

public class MainAkkaFileSize {

	public static void main(final String[] args) {
	    System.out.println("MainAkkaFileSize");
	    akka.Main.main(new String[] { SystemCreationActor.class.getName() });
		
	}
}
