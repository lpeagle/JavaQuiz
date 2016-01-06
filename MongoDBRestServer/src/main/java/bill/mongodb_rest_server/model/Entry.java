package bill.mongodb_rest_server.model;

import java.io.Serializable;

/**
 * Created by bill on 1/5/16.
 */
public class Entry implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;

    public Entry(){

    }

    public Entry(long id, String name){
        this.id=id;
        this.name=name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (id != entry.id) return false;
        return !(name != null ? !name.equals(entry.name) : entry.name != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
