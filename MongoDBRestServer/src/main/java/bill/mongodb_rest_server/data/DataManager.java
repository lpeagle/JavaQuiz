package bill.mongodb_rest_server.data;

import bill.mongodb_rest_server.model.Entry;

/**
 * Created by bill on 1/5/16.
 */
public interface DataManager {
    public void connectDB(String host, int port);
    public void addEntry(Entry entry);
    public void removeEntry(Entry entry);
    public Entry getEntry(long id);
    public void updateEntry(Entry entry);
}
