package it.unibo.db.h2.utils;
import it.unibo.data.model.DataItem;
import it.unibo.db.h2.H2Db;
import it.unibo.db.h2.IH2Repo;
import java.sql.SQLException;
import org.h2.tools.Server;

public class H2Utils {
private static IH2Repo h2db;
private static Server server;
private static String port = "8082";

	public static IH2Repo createH2Db(){
		if( h2db == null ){
			h2db = new H2Db();
		}
		return h2db;
	}
/*
 * H2 currently supports three server: 
 * - a web server (for the H2 Console), 
 * - a TCP server (for client/server connections) and 
 * - an PG server (for PostgreSQL clients). 
 * Please note that only the web server supports browser connections. 
 *  USE: C:\Program Files (x86)\H2\bin
 *  EXCUTE: h2.bat or java -jar h2-1.4.192.jar
 *  WINDOWS:
 *  Open a file browser, navigate to h2/bin, and double click on h2.bat.
 *  A console window appears. If there is a problem, you will see an error message in this window. 
 *  A browser window will open and point to the login page (URL: http://localhost:8082).
 *  The servers can be started/stopped in different ways, one is using the Server tool. 
 *  java -cp h2-1.4.192.jar org.h2.tools.Server
 *  java -cp h2-1.4.192.jar org.h2.tools.Server -tcpShutdown tcp://localhost:9092
 *  Starting the server doesn't open a database - databases are opened as soon as a client connects.	
 *  Then SELECT id, name, consumed from todo_item
 * See https://en.wikipedia.org/wiki/H2_(DBMS)
 * See http://www.h2database.com/html/tutorial.html
 * 
 * http://ip-address-lookup-v4.com/
 *
 */
	public static void startTheserver(){
		String[] args = new String[]{"-tcp", "-tcpPort", port };
		try {
			server = Server.createTcpServer(args).start();
			System.out.println("SERVER STARTED at " + port + " server=" + server);
		} catch (SQLException e) {
 			e.printStackTrace();
		}
	}
	public static void stopTheserver(){
		if( server != null ){
			server.stop();
			System.out.println("SERVER STOPPED at "  + port  + " server=" + server );
		}
		
	}
	public static DataItem insert(IH2Repo h2db , String item){
        DataItem newDataItem = new DataItem();
        newDataItem.setName(item);
        Long newId = h2db.insert(newDataItem);
        newDataItem.setId(newId);
        return newDataItem;
     }
 
}	

