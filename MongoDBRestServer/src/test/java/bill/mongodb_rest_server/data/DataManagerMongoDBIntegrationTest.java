package bill.mongodb_rest_server.data;
import bill.mongodb_rest_server.model.Entry;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * Created by bill on 1/5/16.
 */
public class DataManagerMongoDBIntegrationTest {
    DataManagerMongoDBImpl dataManager=new DataManagerMongoDBImpl();

    @Before
    public void setUp(){
        dataManager.connectDB("localhost", 27017);
    }

    @Test
    public void basicCrudTest(){
        Entry entry=new Entry(2,"book");
        dataManager.addEntry(entry);
        entry=null;
        entry=dataManager.getEntry(2);
        assertEquals("book",entry.getName());
        entry.setName("car");
        dataManager.updateEntry(entry);
        entry=dataManager.getEntry(2);
        assertEquals("car", entry.getName());
        dataManager.removeEntry(entry);
        entry=dataManager.getEntry(2);
        assertNull(entry);
    }
}
