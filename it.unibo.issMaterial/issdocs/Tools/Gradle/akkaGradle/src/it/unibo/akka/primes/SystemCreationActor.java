package it.unibo.akka.primes;

import static akka.pattern.Patterns.ask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class SystemCreationActor extends UntypedActor{
	public static ActorSystem system;
	public static ActorRef myself;
	
	@Override
	public void preStart() {
		final long start = System.nanoTime();
		final int lastNumber    = 10000000;
		final int numberOfParts = 100;		//For each part countPrimes creates an actor
		System.out.println("	*** SystemCreationActor " + this.getSelf()+ " STARTS for " + lastNumber+" parts="+ numberOfParts); 		
		/*
		 * This is a computation-intensive problem, and setting the pool size 
		 * for the ExecutorService version above the number of cores made little difference.
		 */
		final int count  = countPrimes(lastNumber,numberOfParts);
		
		final long end   = System.nanoTime();
		System.out.println("	*** SystemCreationActor Number of primes is " + count);
		System.out.println("	*** SystemCreationActor Time taken " + (end - start)/1.0e9);
		
	}
	/*
	 * Concurrent execution within the actor
	 */
	public int countPrimes(final int number, final int numberOfParts) {
		final int numPerPartition     = number / numberOfParts;
		final List<Future<?>> results = new ArrayList<Future<?>>();
		/*
		 * WORKER CREATIO PHASE
		 */
		for (int index = 0; index < numberOfParts; index++) {
			final int lower = index * numPerPartition + 1;
			final int upper = (index == numberOfParts - 1) ? number : lower + numPerPartition - 1;
			List<Integer> ll = Arrays.asList(lower, upper);  //unmodifiable message
 			final List<Integer> bounds = ll; //Collections.unmodifiableList( ll );
 			/*
 			 * CREATE A WORKER ACTOR
 			 */
 			final ActorRef primeFinder = 
 					getContext().actorOf(Props.create(PrimesActor.class), "primesActor"+index ) ;
 			/*
 			 * ASK the worker (returns a future)
 			 */
 			Future<Object> future = ask( primeFinder, bounds, 10000);
 			results.add( future );
		}
		/*
		 * ANSWER COLLECT PHASE
		 */
		int count = 0;
//		System.out.println("	*** SystemCreationActor Get the answers "  );
 		for (Future<?> f : results){
 			Timeout timeout = new Timeout(Duration.create(5, "seconds"));
 			try {
 				Integer res = (Integer) Await.result(f, timeout.duration() );
// 				System.out.println("	*** SystemCreationActor get result from future count= " + count );
 				count += res;
			} catch (Exception e) {
 				e.printStackTrace();
			}
 		}
//		Actors.registry().shutdownAll();
 		System.out.println("	*** SystemCreationActor ALL ANSWERS acquired "  );
		return count;
	}

	@Override
	public void onReceive(Object arg0) throws Throwable {
 		
	}

}
