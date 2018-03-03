package it.unibo.akka.primes;

import java.util.List;
import akka.actor.UntypedActor;

public class PrimesActor extends UntypedActor {

	public void onReceive(final Object boundsList) {
		if( boundsList instanceof List<?> ){
			final List<Integer> bounds = (List<Integer>) boundsList;
			final int n1 = bounds.get(0);
			final int n2 = bounds.get(1);
			final int count = PrimeFinder.countPrimesInRange(n1,n2);
			// getContext().replySafe(count);
			/*
			 * ANSWER to the "ask" request
			 */
 			System.out.println("	*** PrimesActor " + getSelf()+ " for " + n1 +":" + n2 +" answers count= " + count );
			getSender().tell(count, getSelf());
		}
	}
	
}
