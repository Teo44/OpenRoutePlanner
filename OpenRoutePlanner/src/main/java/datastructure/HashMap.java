package datastructure;

/**
 *  Simple hash map implementation with put and get operations.
 * @param <T> Type of the key
 * @param <K> Type of the value
 */
public class HashMap<T, K> {
    
    private ArrayList<HashObject<K>>[] array;
    private int size;
    private int objects;
    final private float loadFactor;
    
    public HashMap(int n)   {
        array = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            array[i] = new ArrayList<>();
        }
        size = n;
        objects = 0;
        loadFactor = 4f;
    }
    
    public HashMap(int n, float f)   {
        array = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            array[i] = new ArrayList<>();
        }
        size = n;
        objects = 0;
        loadFactor = f;
    }
    
    public HashMap()    {
        array = new ArrayList[128];
        for (int i = 0; i < 128; i++)   {
            array[i] = new ArrayList<>();
        }
        size = 128;
        objects = 0;
        loadFactor = 4f;
    }
    
    public void put(long key, K value)  {
        objects += 1;
        if (objects >= size * loadFactor)   {
            reHash();
        }
        array[hash(key)].add(new HashObject<>(key, (K) value));
    }
    
    public K get(long key)  {
        for (HashObject o : array[hash(key)])   {
            if (o.getKey() == key)  {
                return (K) o.getValue();
            }
        }
        return null;
    }
    
    public boolean contains(long key)  {
        for (HashObject o : array[hash(key)])   {
            if (o.getKey() == key)  {
                return true;
            }
        }
        return false;
    }
    
    private void reHash()   {
        size *= 2;
        ArrayList<HashObject<K>>[] newArray = new ArrayList[size];
        for (int i = 0; i < size; i++)  {
            newArray[i] = new ArrayList<>();
        }
        for (int i = 0; i < size / 2; i++)  {
            for (HashObject o : array[i])   {
                newArray[hash(o.getKey())].add(o);
            }
        }
        array = newArray;
    }
    
    private int hash(long key)  {
        return (int) (key % size);
    }
    
}
