package it.unibo.qatest;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import it.unibo.baseEnv.basicFrame.EnvFrame;
import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.ActorContext;

public class CtxOfHello extends ActorContext{
	public CtxOfHello(String name, IOutputEnvView outEnvView, 
			InputStream inputStream, InputStream sysRulesStream ) throws Exception {
		super(name, outEnvView, inputStream, sysRulesStream );
  	}
  	@Override 	
	public void configure(){ 
//		QActorHello qahello = 
			new QActorHello("qahello", this, outEnvView);
	}	
  	
 	public static void main(String[] args) throws Exception {
		IBasicEnvAwt env = new EnvFrame("ctxOfHello", null, Color.yellow, Color.blue);
		env.init();
		env.writeOnStatusBar("ctxOfHello" + " | working ... ", 14);
		/*
		 * Context definition
		 */
		String ctxDef = "context( ctxofhello, 'localhost',  'TCP', '8010' ).\n";
		ctxDef = ctxDef + "qactor( qahello, ctxofhello ).\n";
		byte[] buf = ctxDef.getBytes();
		new ByteArrayInputStream( buf );
		/*
		 * Context activation
		 */
		InputStream sysKbStream    = new ByteArrayInputStream( buf );
		InputStream sysRulesStream = new FileInputStream("sysRules.pl");
		new CtxOfHello("ctxofhello", 
				env.getOutputEnvView(), sysKbStream, sysRulesStream ).configure();
//		new CtxOfHello("ctxofhello", 
//				it.unibo.system.SituatedSysKb.standardOutEnvView, sysKbStream, sysRulesStream ).configure();
	}
}
