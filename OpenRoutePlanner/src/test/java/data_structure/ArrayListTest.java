package data_structure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ArrayListTest {

    private static ArrayList<Integer> array;
    
    @Before
    public void setUp()  {
        array = new ArrayList<>();
    }
    
    @Test
    public void addingObjectsWorks1()  {
        array.add(1);
        assertTrue(1 == array.get(0));
    }
    
    @Test
    public void addingObjectsWorks2()   {
        array.add(1);
        array.add(2);
        assertTrue(2 == array.get(1));
    }
    
    @Test
    public void outOfBoundsGetReturnsNull() {
        array.add(1);
        assertNull(array.get(1));
    }
    
    @Test
    public void arraySize1() {
        array.add(1);
        array.add(2);
        assertTrue(array.size() == 2);
    }
    
    @Test
    public void arraySize2()    {
        for (int i = 0; i < 5867; i++)  {
            array.add(i);
        }
        assertTrue(array.size() == 5867);
    }
    
    @Test
    public void arrayListIterable() {
        for (int i = 0; i < 100; i++) {
        array.add(123);
        }
        boolean works = true;
        for (int i : array) {
            if (i != 123)   {
                works = false;
            }
        }
        assertTrue(works);
    }
    
    @Test
    public void isEmptyWorks1() {
        array.add(1);
        assertFalse(array.isEmpty());
    }
    
    @Test
    public void isEmptyWorks2() {
        assertTrue(array.isEmpty());
    }
    
    
    @Test
    public void addingManyObject()  {
        for (int i = 0; i < 1000000; i++)   {
            array.add(i);
        }
        assertEquals(12345, array.get(12345), 0);
    }
    
}
