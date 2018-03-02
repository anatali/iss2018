import 	alice.tuprolog.Term;
public class GreetingInJava{
	private Term ttt = Term.createTerm("a(1.0)");
	public void greet() {
 		System.out.println( "		*** hello from GreetingInJava " + ttt.toString() );
    }
	public String getName() {
		 System.out.println( "		*** getName from GreetingInJava " + ttt.toString() );
         return "buildScript" ;
    }
}