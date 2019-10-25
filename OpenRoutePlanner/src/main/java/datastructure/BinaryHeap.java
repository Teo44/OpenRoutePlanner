package datastructure;

import algorithm.DijkstraNode;

/**
 *  SImple minimum priority queue implemented using a binary heap
 */
public class BinaryHeap {

    DijkstraNode[] tree;
    int index;
    int size;
    
    /**
     *  Create a new binary heap.
     * @param size  Define the starting size of the heap
     */
    public BinaryHeap(int size)   {
        tree = new DijkstraNode[size];
        index = 1;
        this.size = size;
    }
    
    /**
     *  Create the heap with the default size of 10
     */
    public BinaryHeap() {
        tree = new DijkstraNode[10];
        index = 1;
        size = 10;
    }
    
    /**
     * Adds a node to the heap
     * @param n DijkstraNode-object
     */
    public void add(DijkstraNode n)  {
        // if the array is full, copy all nodes to a new array of twice the size
        if (size <= index)   {
            DijkstraNode[] newTree = new DijkstraNode[tree.length * 2];
            for (int i = 1; i < tree.length; i++)   {
                newTree[i] = tree[i];
            }
            tree = newTree;
            size *= 2;
        }
        tree[index] = n;
        int newIndex = index;
        index += 1;
        // if the added node was the first one, skip the sorting
        if (index == 2) {
            return;
        }
        int compIndex = newIndex / 2;
        // move the new node up until the heap is a sorted again
        while (tree[compIndex].compareTo(tree[newIndex]) > 0)   {
            swap(compIndex, newIndex);
            newIndex = compIndex;
            compIndex = newIndex / 2;
            if (compIndex < 1)  {
                break;
            }
        }
    }
    
    /**
     *  Get the smallest node in the heap and remove it.
     * @return DijkstraNode-object
     */
    public DijkstraNode poll()  {
        if (index == 1)  {
            return null;
        }
        // save the first node to be returned
        DijkstraNode n = tree[1];
        // move the last node to the top of the heap
        tree[1] = tree[index - 1];
        index -= 1;
        if (index  == 1)  {
            return n;
        }
        
        // compare the moved node to its children, swap with the smallest of it's children.
        // repeat until it has no smaller nodes as children.
        int compIndex = 1;
        int child1 = 2;
        int child2 = 3;
        
        while (child1 < index)  {
            if (child2 == index)    {
                if (tree[compIndex].compareTo(tree[child1]) > 0)    {
                    swap(child1, compIndex);
                    compIndex = child1;
                    child1 = compIndex * 2;
                    child2 = child1 + 1;
                } else  {
                    break;
                }
            } else  {
                if (tree[compIndex].compareTo(tree[child2]) > 0)    {
                    if (tree[child2].compareTo(tree[child1]) > 0)    {
                        swap(child1, compIndex);
                        compIndex = child1;
                        child1 = compIndex * 2;
                        child2 = child1 + 1;
                    } else  {
                        swap(child2, compIndex);
                        compIndex = child2;
                        child1 = compIndex * 2;
                        child2 = child1 + 1;
                    }
                } else if (tree[compIndex].compareTo(tree[child1]) > 0)    {
                    swap(child1, compIndex);
                    compIndex = child1;
                    child1 = compIndex * 2;
                    child2 = child1 + 1;
                } else  {
                    break;
                }
            }
        }
        
        return n;
    }
    
    private void swap(int i, int j) {
        DijkstraNode x = tree[i];
        tree[i] = tree[j];
        tree[j] = x;
    }
    
    /**
     * Get the smallest node in the heap, don't remove it from the heap.
     * @return 
     */
    public DijkstraNode peek()  {
        if (index == 1)  {
            return null;
        }
        return tree[1];
    }
    
    /**
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty()    {
        return (index == 1);
    }
    
}
