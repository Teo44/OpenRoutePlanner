package data_structure;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class HashMapTest {
    
    private static HashMap<Integer, Integer> hash;
    private static HashMap<Integer, Integer> hash2;
    
    @Before
    public void setUp() {
        hash = new HashMap<>();
    }
    
    @Test
    public void addingObjects1()    {
        hash.put(123, 321);
        assertEquals(321, hash.get(123), 0);
    }
    
    @Test
    public void addingObjects2()    {
        hash.put(123, 321);
        hash.put(1, 2);
        hash.put(123978321, 18309);
        assertEquals(18309, hash.get(123978321), 0);
    }
    
    @Test
    public void differentStartArraySize()   {
        hash2 = new HashMap<>(100);
        hash2.put(123, 321);
        assertEquals(321, hash2.get(123), 0);
    }
    
    @Test
    public void differentStartArraySizeAndLoadFactor()  {
        hash2 = new HashMap<>(100, 0.5f);
        hash2.put(123, 321);
        assertEquals(321, hash2.get(123), 0);
    }
    
    @Test
    public void addingManyObjects() {
        for (int i = 0; i < 1000000; i++)   {
            hash.put(i, i*2);
        }
        assertEquals(100000, hash.get(50000), 0);
    }
    
}
