package it.unibo.db.unit.test;
import org.junit.Before;
import org.junit.Test;
import it.unibo.data.model.DataItem;
import it.unibo.db.IRepository;
import it.unibo.db.InMemoryDb;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;

public class InMemoryDbTest {
    private IRepository db;

    @Before
    public void setUp() {
        db = new InMemoryDb();
    }  

    @Test
    public void testInsertDataItem() {
    	System.out.println("	*** testInsertDataItem "   );
        DataItem newDataItem = new DataItem();
        newDataItem.setName("data0");
        Long newId = db.insert(newDataItem);
        assertNotNull(newId);

        DataItem persistedDataItem = db.findById(newId);
        assertNotNull(persistedDataItem);
        assertEquals(newDataItem, persistedDataItem);
    }
    
    @Test
    public void testAllDataItem() {    	
        System.out.println("	*** testAllDataItem "   );
        DataItem newDataItem = new DataItem();
        newDataItem.setName("data1");
        Long newId = db.insert(newDataItem);
        newDataItem.setId(newId);
        assertNotNull(newId);
        
        newDataItem = new DataItem();
        newDataItem.setName("data2");
        newId = db.insert(newDataItem);
        newDataItem.setId(newId);
        assertNotNull(newId);
         
        List<DataItem> l = db.findAll();
        Iterator<DataItem>	iter = l.iterator();
        while( iter.hasNext() ){
        	DataItem dd = iter.next();
        	System.out.println("	*** FOUND " + dd.toString() );
        }
         
     }
    
}