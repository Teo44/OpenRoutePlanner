package data_structure;

import algorithm.DijkstraNode;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class BinaryHeapTest {

    private BinaryHeap heap;
    
    @Before
    public void setUp() {
        heap = new BinaryHeap();
    }
    
    @Test
    public void heapPoll1()    {
        heap.add(new DijkstraNode(1, 100));
        heap.add(new DijkstraNode(2, 50));
        assertEquals(2, heap.poll().getID());
    }
    
    @Test
    public void heapPeek1()    {
        heap.add(new DijkstraNode(1, 100));
        heap.add(new DijkstraNode(2, 50));
        assertEquals(2, heap.peek().getID());
    }
    
    @Test
    public void heapPoll2() {
        heap.add(new DijkstraNode(1, 1));
        heap.add(new DijkstraNode(2, 2));
        assertEquals(1, heap.poll().getID());
    }
    
    @Test
    public void heapPeek2() {
        heap.add(new DijkstraNode(1, 1));
        heap.add(new DijkstraNode(2, 2));
        assertEquals(1, heap.peek().getID());
    }
    
    @Test
    public void heapPollRemovesNode()   {
        heap.add(new DijkstraNode(1, 100));
        heap.add(new DijkstraNode(2, 50));
        heap.poll();
        assertEquals(1, heap.poll().getID());
    }
    
    @Test
    public void returnNullWhenHeapEmpty1()   {
        heap.add(new DijkstraNode(1, 123));
        heap.poll();
        assertNull(heap.poll());
    }
    
    @Test
    public void returnNullWhenHeapEmpty2()  {
        heap.add(new DijkstraNode(1, 123));
        heap.poll();
        assertNull(heap.peek());
    }
    
    @Test
    public void isEmptyWhenHeapNotEmpty()   {
        heap.add(new DijkstraNode(1, 123));
        assertFalse(heap.isEmpty());
    }
    
    @Test
    public void isEmptyWhenHeapEmpty1() {
        assertTrue(heap.isEmpty());
    }
    
    @Test
    public void isEmptyWhenHeapEmpty2() {
        heap.add(new DijkstraNode(1, 123));
        heap.poll();
        assertTrue(heap.isEmpty());
    }
    
    @Test
    public void randomNodes1()   {
        Random r = new Random();
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 10000; i++) {
            int n = r.nextInt(1000000) + 1;
            if (n < min)    {
                min = n;
            }
            heap.add(new DijkstraNode(n, n));
        }
        assertEquals(min, heap.poll().getID());
    }
    
    @Test
    public void randomNodes2()  {
        Random r = new Random();
        int max = 0;
        for (int i = 0; i < 10000; i++) {
            int n = r.nextInt(1000000) + 1;
            if (n > max)    {
                max = n;
            }
            heap.add(new DijkstraNode(n, n));
        }
        for (int i = 0; i < 9999; i++)  {
            heap.poll();
        }
        assertEquals(max, heap.poll().getID());
    }
    
}
