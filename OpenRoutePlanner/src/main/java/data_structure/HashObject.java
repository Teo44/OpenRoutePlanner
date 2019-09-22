package data_structure;

/**
 * Object for storing the values in a hashmap and their keys
 * @param <K>  Type of the value
 */
public class HashObject<K> {
    
    private long key;
    private K value;
    
    public HashObject(long key, K value)    {
        this.key = key;
        this.value = value;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public K getValue() {
        return value;
    }

    public void setValue(K value) {
        this.value = value;
    }

}
