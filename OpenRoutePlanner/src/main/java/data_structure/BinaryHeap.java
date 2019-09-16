package data_structure;

import algorithm.DijkstraNode;

public class BinaryHeap {

    DijkstraNode[] tree;
    int index;
    int size;
    
    public BinaryHeap(int size)   {
        tree = new DijkstraNode[size];
        index = 1;
        this.size = size;
    }
    
    public BinaryHeap() {
        tree = new DijkstraNode[10];
        index = 1;
        size = 10;
    }
    
    public void add(DijkstraNode n)  {
        // if the array is full, copy all nodes to new array of twice the size
        //debug
//        System.out.println("Added node " + n.getID() + " with dist of " + n.getDist() + " to index " + index);
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
            //swap
            DijkstraNode x = tree[compIndex];
            tree[compIndex] = tree[newIndex];
            tree[newIndex] = x;
            newIndex = compIndex;
            compIndex = newIndex / 2;
            if (compIndex < 1)  {
                break;
            }
        }
    }
    
    public DijkstraNode poll()  {
        if (index == 1)  {
            return null;
        }
        DijkstraNode n = tree[1];
        tree[1] = tree[index - 1];
        index -= 1;
        if (index  == 1)  {
            return n;
        }
        
        // compare the moved node to its children, swap with the smaller children, if exists
        
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
        //debug
//        System.out.println("swapped nodes at index " + i + " and " + j);
//        System.out.println("with values " + tree[i].getDist() + " and " + tree[j].getDist());
        DijkstraNode x = tree[i];
        tree[i] = tree[j];
        tree[j] = x;
    }
    
    public DijkstraNode peek()  {
        if (size == 0)  {
            return null;
        }
        return tree[1];
    }
    
    public boolean isEmpty()    {
        return (index == 1);
    }
    
}
