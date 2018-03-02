package it.unibo.clojure.stm;

import it.unibo.clojure.stm.Annot.AuthorInfo;

public class AnnotReport {
	
	public static void report(Class theClass) {
		if (theClass.isAnnotationPresent(AuthorInfo.class)) {
			AuthorInfo authInfo = (AuthorInfo) theClass.getAnnotation(AuthorInfo.class);
			System.out.println(theClass.getName() + " has @AuthorInfo annotation:");
			System.out.printf("name = %s, email = %s, comment = %s\n", authInfo.name(), authInfo.email(),
					authInfo.comment());
		} else {
			System.out.println(theClass.getName() + " doesn't have @AuthorInfo annotation");
		}
		System.out.println("-----------------");
	}

	public static void main(String[] args) {
		report(Account.class);
		report(Transfer.class);
		report(String.class);
	}
}
