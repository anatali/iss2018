/***
KEY-POINT : Executors Callable Future
			Concurrency in computationally intensive applications
***/
package it.unibo.java.concur.primes;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ConcurrentPrimeFinder extends AbstractPrimeFinder {
  private final int poolSize;
  private final int numberOfParts;
  private ExecutorService executorPool;
  private List<Callable<Integer>> partitions;
  
  public ConcurrentPrimeFinder(final int thePoolSize,  final int theNumberOfParts) {
    poolSize      = thePoolSize;
    numberOfParts = theNumberOfParts;
    System.out.printf("poolSize %d numberOfParts %d\n", poolSize, numberOfParts);
  }  
  public int countPrimes(final int number) {
	  setPartitions(number);
	  try {
		  int count = setExecutorPool(number);
		  return count;
	  } catch (Exception e) {
		  throw new RuntimeException(e);
	  }         
  }
  
  protected void setPartitions(int number){
	  partitions = new ArrayList<Callable<Integer>>(); 
	  final int nPerPartition = number / numberOfParts;
	  for(int i = 0; i < numberOfParts; i++) {
		        final int lower = (i * nPerPartition) + 1;
		        final int upper = (i == numberOfParts - 1) ? number : lower + nPerPartition - 1;
		        /*
		         * The Callable interface is similar to java.lang.Runnable, 
		         * in that both are designed for classes whose instances are potentially executed by another thread. 
		         * A Runnable, however, does not return a result and cannot throw a checked exception. 
		         */
		        partitions.add(new Callable<Integer>() {
				          public Integer call() {
				        	  //System.out.println("callable with lower=" + lower + " upper=" + upper);
				        	  return countPrimesInRange(lower, upper);
				          }        
			          }
		        );	 
	  }//for
  }
  protected int setExecutorPool(int number) throws Exception{
	  int count = 0;
	  executorPool = Executors.newFixedThreadPool(poolSize); 
	  int tout= 10000;
	  /*
	   * The executor takes the responsibility of concurrently running 
	   * as many of the parts as possible.
	   * invokeAll executes the given tasks, returning a list of Futures holding their status 
	   * and results when all complete or the timeout expires, whichever happens first. 
	   * Future.isDone is true for each element of the returned list. 
	   * Upon return, tasks that have not completed are cancelled. 
	   * Note that a completed task could have terminated either normally or 
	   * by throwing an exception. 
	   * The results of this method are undefined if the given collection is modified
	   * while this operation is in progress.
	   */
	  final List<Future<Integer>> resultFromParts = 
			  executorPool.invokeAll(partitions, tout, TimeUnit.SECONDS);
	  executorPool.shutdown(); 
	  for(final Future<Integer> result : resultFromParts)
		        count += result.get(); 	//waits for completion 
	  return count; 
  }
  /*
   * MAIN
   */
  public static void main(final String[] args) {
	  int num=10000000;	//10000000 -> 664579 prime numbers
	  int poolsize = 4;
	  int numberOfParts = 100;
      new ConcurrentPrimeFinder( poolsize,numberOfParts ).timeAndCompute(num);
  }
}
/*
RESULTS ( for num=10000000)
	1,1   = 9.642573466
	2,2   = 7.250579182
	3,3   = 5.756167314
	4,4   = 5.133613467
	4,10  = 5.15021306
	4,40  = 4.821894238
	4,100 = 4.845353223
	4,256 = 4.768056874

RESULTS ( for num=20000000)
	4,100 = 13.077159374


RESULTS ( for num=50000000)
	4,100 = 47.347761333
*/