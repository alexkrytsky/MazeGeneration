package core;

import java.util.Arrays;

/**
 * Class that keeps track of the sets in the graph and provide functionality to union sets
 *
 * @author Alex Krytskyi
 * @version 1.0
 */
public class DisjointSet
{
    private int[] set;
    private int count;

    /**
     * Constructor that sets the size of the array
     *
     * @param size int number of separate sets and size of the array
     */
    public DisjointSet(int size)
    {
        this.set = new int[size];
        this.count = size;
        Arrays.fill(this.set, -1);
    }

    /**
     * Union method that checks whether 2 elements belong to the same set and if not, joins them together
     *
     * @param first  int first vertex to join
     * @param second int second vertex to join
     * @return true/false was the union performed or not
     */
    public boolean union(int first, int second)
    {
        // Get the root for elements to check are they from the same set
        int firstRoot = find(first);
        int secondRoot = find(second);

        if (firstRoot != secondRoot)
        {
            if (this.set[firstRoot] < this.set[secondRoot])
            {
                this.set[secondRoot] = firstRoot;
            }
            else if (this.set[secondRoot] < this.set[firstRoot])
            {
                this.set[firstRoot] = secondRoot;
            }
            else if (this.set[firstRoot] == this.set[secondRoot])
            {
                this.set[secondRoot] = firstRoot;
                this.set[firstRoot]--;
            }
            count--;
            return true;
        }
        return false;
    }

    /**
     * @param element int vertex to find the root for
     * @return int root or parent of the element passed in.
     */
    public int find(int element)
    {
        if (this.set[element] < 0)
        {
            return element;
        }
        else
        {
            return set[element] = find(set[element]);
        }
    }

    /**
     * Returns number of separate sets in this structure
     *
     * @return int number of disjoint sets
     */
    public int count()
    {
        return this.count;
    }

    @Override
    public String toString()
    {
        return "DisjointSet{" +
                "set=" + Arrays.toString(set) +
                ", count=" + count +
                '}';
    }
}
