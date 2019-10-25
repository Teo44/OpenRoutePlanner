package datastructure;

import java.util.Iterator;

/**
 *  Simple general, iterable arraylist implementation
 * @param <T> The type of object to store in the arraylist
 */
public class ArrayList<T> implements Iterable<T> {
    
    private T[] array;
    int index;
    int size;
    
    public ArrayList(int n) {
        array = (T[]) new Object[n];
        index = 0;
        size = n;
    }
    
    public ArrayList()  {
        array = (T[]) new Object[10];
        index = 0;
        size = 10;
    }
    
    public void add(T a)  {
        // if the array is full, copy all the nodes to a new array of twice the size
        if (size <= index)   {
            T[] newArray = (T[]) new Object[size * 2];
            for (int i = 0; i < array.length; i++)  {
                newArray[i] = array[i];
            }
            array = newArray;
            size *= 2;
        }
        array[index] = a;
        index += 1;
    }
    
    public T get(int i)   {
        if (i > index)  {
            return null;
        }
        return array[i];
    }
    
    public T getLast()  {
        if (index == 0)  {
            return null;
        }
        return array[index - 1];
    }
    
    public void removeLast()    {
        if (index == 0) {
            return;
        }
        index -= 1;
    }
    
    public T pollLast() {
        if (index == 0) {
            return null;
        }
        index -= 1;
        return array[index];
    }
    
    public boolean isEmpty()    {
        return (index == 0);
    }
    
    public int size()   {
        return index;
    }
    
    @Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>()  {
            private int i = 0;
            
            @Override
            public boolean hasNext()   {
                return i < index;
            }
            
            @Override
            public T next()   {
                return array[i++];
            }
            
            @Override
            public void remove()    {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}
