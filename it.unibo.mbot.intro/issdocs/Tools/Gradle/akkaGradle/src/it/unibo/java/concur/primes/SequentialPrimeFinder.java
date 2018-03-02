/***
 * Excerpted from "Programming Concurrency on the JVM",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/vspcon for more book information.
***/
package it.unibo.java.concur.primes;
/*
 * The Server version of JVM loads more slowly, putting more effort into producing 
 * highly optimized JIT compilations, that yield higher performance.
 */
public class SequentialPrimeFinder extends AbstractPrimeFinder {
  public int countPrimes(final int number) {
    return countPrimesInRange(1, number);
  }
  
  public static void main(final String[] args) { 
	  int lastNum = 10000000;
    new SequentialPrimeFinder().timeAndCompute(lastNum);
  }
}
