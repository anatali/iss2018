package it.unibo.db.h2.appl;
import java.util.Iterator;
import java.util.List;
import it.unibo.data.model.DataItem;
import it.unibo.db.h2.H2Db;
import it.unibo.db.h2.IH2Repo;
import it.unibo.db.h2.utils.H2Utils;
import org.h2.tools.Server;

public class H2DbAppl {
	public static void main(String args[]) {
		IH2Repo h2db = new H2Db();
//  		H2Utils.startTheserver();
		
		DataItem d;
		d = H2Utils.insert(h2db,"a1");
		d = H2Utils.insert(h2db,"a2");
		d = H2Utils.insert(h2db,"a3");
		
        List<DataItem> l = h2db.findAll();
        Iterator<DataItem>	iter = l.iterator();
        while( iter.hasNext() ){
        	DataItem dd = iter.next();
        	System.out.println("	*** FOUND " + dd.toString() );
        }
        
//        H2Utils.stopTheserver();
	}
}
