package bill.mongodb_rest_server.data;

import bill.mongodb_rest_server.model.Entry;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by bill on 1/5/16.
 */
@Repository
public class DataManagerMongoDBImpl implements DataManager {
    Logger logger = LoggerFactory.getLogger(DataManagerMongoDBImpl.class);
    final static String DBCollectionName = "entries";
    protected MongoClient mongoClient;
    DBCollection collection;
    protected DB db;


    public void connectDB(String host, int port) {
        try {
            mongoClient = new MongoClient(host, port);   //"localhost", 27017);

            // Now connect to your databases
            db = mongoClient.getDB("EricssonTest");
            collection = db.getCollection(DBCollectionName);
            logger.info("Connect to database successfully");
        } catch (Exception e) {
            String errMsg = String.format("Error connecting to db, host:%s, port:%d", host, port);
            logger.error(errMsg);
            throw new RuntimeException(errMsg, e);
        }
    }

    public void addEntry(Entry entry) {

        BasicDBObject doc = new BasicDBObject("id", entry.getId()).
                append("name", entry.getName());

        collection.insert(doc);
        logger.debug("Entry: {} inserted successfully", entry);

    }


    public void removeEntry(Entry entry) {
        collection.remove(new BasicDBObject("id", entry.getId()));
    }

    public Entry getEntry(long id) {
        DBCursor cursor = collection.find(new BasicDBObject("id", id));
        if (cursor.hasNext()) {
            DBObject document = cursor.next();
            logger.debug("Got document:{}",document);
            return new Entry((Long) document.get("id"), (String) document.get("name"));
        } else
            return null;
    }

    //update based on id, if id the same, we see it as same entry
    public void updateEntry(Entry entry) {

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject().append("name", entry.getName()));

        BasicDBObject searchQuery = new BasicDBObject().append("id", entry.getId());

        collection.update(searchQuery, newDocument);
    }

}
