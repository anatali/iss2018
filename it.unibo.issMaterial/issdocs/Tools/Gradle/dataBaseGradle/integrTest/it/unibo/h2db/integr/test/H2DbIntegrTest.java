package it.unibo.h2db.integr.test;
import org.junit.Before;
import org.junit.Test;
import it.unibo.data.model.DataItem;
import it.unibo.db.h2.H2Db;
import it.unibo.db.h2.IH2Repo;
import it.unibo.db.h2.utils.H2Utils;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;

public class H2DbIntegrTest {
    private IH2Repo h2db;

    @Before
    public void setUp() {
        h2db = new H2Db(); 
    }

    @Test
    public void testIntegrInsertDataItem() {
    	System.out.println("	*** testIntegrInsertDataItem "   );
        DataItem d = H2Utils.insert(h2db,"item0");
        assertNotNull(d.getId());
        //Find
        DataItem storeData = h2db.findById( d.getId() );
        System.out.println("	*** FOUND " + storeData.toString() );
        assertNotNull(storeData);
        assertEquals(d, storeData);
    }
    
    @Test
    public void testIntegrAllDataItem() {    	
        System.out.println("	*** testIntegrAllDataItem "   );
    	DataItem d = H2Utils.insert(h2db,"item1");
        assertNotNull(d.getId());
    	d = H2Utils.insert(h2db,"item2");
        assertNotNull(d.getId());
        List<DataItem> l = h2db.findAll();
        Iterator<DataItem>	iter = l.iterator();
        while( iter.hasNext() ){
        	DataItem dd = iter.next();
        	System.out.println("	*** FOUND " + dd.toString() );
        }
         
     }
    
 }