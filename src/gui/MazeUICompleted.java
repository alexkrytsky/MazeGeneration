package gui;

import core.DisjointSet;
import core.Graph;
import generation.AlgorithmType;
import javafx.scene.paint.Color;

import java.util.Random;
import java.util.Stack;

/**
 * @author Alex
 * @version 1.0
 * Class that generates and solves the maze
 */
public class MazeUICompleted extends MazeUI
{
    private Graph graph;
    private DisjointSet disjointSet;
    private Random rand = new Random();

    @Override
    public void runAlgorithm(AlgorithmType type)
    {
        switch (type)
        {
            case GENERATE_MAZE:
                generateMaze();
                break;
            case BFS:
                // Solve BFS
                solveBFS();
                break;
            case DFS:
                // Solve DFS
                solveDFS();
                break;
        }
    }

    // Solves the maze with DFS algorithm and draws the solution
    private void solveDFS()
    {
        // LinkedList<Integer> vertices = graph.dfs2(15);
        Stack<Integer> vertices = (Stack<Integer>) graph.runDFS();

        this.setFillColor(Color.YELLOW);
        for (int vertex : vertices)
        {
            this.fillCell(vertex);
        }
    }

    // solves the maze with BFS algorithm and draws the solution
    private void solveBFS()
    {
        Stack<Integer> vertices = (Stack<Integer>) graph.runBFS();

        this.setFillColor(Color.YELLOW);
        for (int vertex : vertices)
        {
            this.fillCell(vertex);
        }
    }

    // generates graph and draws maze on the GUI
    private void generateMaze()
    {
        this.clearScreen();
        graph = new Graph(this.getCols());
        disjointSet = new DisjointSet(this.getRows() * this.getCols());
        // draw the background
        this.drawBackgroundGrid();
        this.setStrokeColor(new Color(0.0, 0.0, 0.0, 1));
        buildMaze();
        // draw walls for the maze
        for (int i = 0; i < this.getCols() * this.getRows(); i++)
        {
            boolean[] walls = getWalls(i);
            this.drawCell(i, walls);
        }
    }

    // Adds vertices to the graph and unions graph until 1 set left
    private void buildMaze()
    {
        // Add vertices
        for (int i = 0; i < this.getRows() * this.getCols(); i++)
        {
            graph.addVertex(i);
        }
        // Join sets/graph until only 1 graph left
        while (disjointSet.count() != 1)
        {
            int sourceRand = rand.nextInt(this.getRows() * getCols());
            int destRand = getRandomNeighbor(sourceRand);
            // if union was successful, add edge to the undirected graph
            if (disjointSet.union(sourceRand, destRand))
            {
                graph.addEdge(sourceRand, destRand);
            }
        }
    }

    /**
     * Returns walls north, east, south, west
     *
     * @param vertex int vertex to get walls for
     * @return bool[] array of walls
     */
    private boolean[] getWalls(int vertex)
    {
        boolean[] walls = {true, true, true, true};
        // get north
        walls[0] = !graph.hasEdge(vertex, vertex - this.getCols());
        // get east
        walls[1] = !graph.hasEdge(vertex, vertex + 1);
        // get south
        walls[2] = !graph.hasEdge(vertex, vertex + this.getCols());
        // get west
        walls[3] = !graph.hasEdge(vertex, vertex - 1);

        return walls;
    }

    // returns 1 random neighbor
    private int getRandomNeighbor(int index)
    {
        int randNeigh = rand.nextInt(4);
        int[] wallsIndex = new int[4];
        // get random
        wallsIndex[0] = index / this.getCols() == (index - 1) / this.getCols() ? index - 1 : -1;
        wallsIndex[1] = index / this.getCols() == (index + 1) / this.getCols() ? index + 1 : -1;
        wallsIndex[2] = index - this.getCols();
        wallsIndex[3] = (index + this.getCols()) < this.getCols() * this.getRows() ? index + this.getCols() : -1;

        while (wallsIndex[randNeigh] < 0)
        {
            randNeigh = rand.nextInt(4);
        }

        return wallsIndex[randNeigh];
    }

    @Override
    public String toString()
    {
        return "MazeUICompleted{" +
                "graph=" + graph +
                ", disjointSet=" + disjointSet +
                ", rand=" + rand +
                '}';
    }
}
