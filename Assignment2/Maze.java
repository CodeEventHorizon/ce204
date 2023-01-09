import java.util.*;

public class Maze {

    private final Random rand = new Random();
    private final int width;
    private final int height;
    private final Graph graph;
    private List<List<Integer>> components = new ArrayList<>();
    /**
     * 1. Getting started
     * */
    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        graph = new MatrixGraph(width*height, Graph.UNDIRECTED_GRAPH);
    }
    /**
     * 2. Printing
     *
     * The formula used (y*width)+x = {any vertex in the maze}
     * */
    public void print() {
        int entrance = rand.nextInt(height);
        int exit = rand.nextInt(height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (graph.isEdge((y*width)+x,(y*width)+x+1)) {
                    if (y == entrance && x == 0) {
                        System.out.print("*-");
                    } else {
                        System.out.print("+-");
                    }
                } else {
                    if (((y*width)+x) % width == width-1) {
                        if (y == exit) {
                            System.out.print("*\n");
                        } else {
                            System.out.print("+\n");
                        }
                    } else {
                        if (y == entrance && x == 0) {
                            System.out.print("* ");
                        } else {
                            System.out.print("+ ");
                        }
                    }
                }
            }
            for (int x = 0; x < width; x++) {
                if (graph.isEdge(((y+1)*width)+x, ((y+1)*width)+x - width)) {
                    System.out.print("| ");
                    if ((((y+1)*width)+x) % width == width-1) {
                        System.out.print("\n");
                    }
                } else {
                    if (!graph.isEdge(((y+1)*width)+x, ((y+1)*width)+x+1) && (((y+1)*width)+x) % width == width-1) {
                        System.out.print("\n");
                    } else {
                        System.out.print("  ");
                    }
                }
            }
        }
        //System.out.println(entrance + " " + exit);
    }
    /**
     * 3. Tracking graph components
     * */
    public void initializeComponents() {
        for (int i = 0; i < graph.numVertices; i++) {
            components.add(Collections.singletonList(i));
        }
    }

    public void mergeComponents(int x, int y) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).get(0) == y) {
                components.remove(i);
            }
        }
        components.add(Arrays.asList(x,y));
    }
    /**
     * 4. A spanning tree algorithm
     *
     * Eventually i was not able to implement the MST, as not all the edges connect, but they don't make cycles
     * */
    public void spanningTree() {
        initializeComponents();
        Graph t = new MatrixGraph(width*height, Graph.UNDIRECTED_GRAPH);

        List<Integer> visited = new ArrayList<>();
        List<Integer> toVisit = new ArrayList<>();
        List<List<Integer>> weights = new ArrayList<>();

        visited.add(0);
        t.addEdge(0,1);
        t.addEdge(0, width);
        weights.add(Arrays.asList(0,1));
        weights.add(Arrays.asList(0,width));

        for (int i = 0; i < t.numVertices; i ++) {
            toVisit.add(i);
        }
        while (toVisit.size() > 0) {
            int rndm = rand.nextInt(toVisit.size());
            int next = toVisit.remove(rndm);

            if (visited.contains(next)) {
                continue;
            }

            visited.add(next);
            int up = next - width;
            int left = next - 1;
            int right = next + 1;
            int dow = next + width;

            if (up > 0 && !visited.contains(up)) {
                toVisit.add(up);
                t.addEdge(next, up);
                weights.add(Arrays.asList(next, up));
            }
            if (left % width != width - 1 && !visited.contains(left)) {
                toVisit.add(left);
                t.addEdge(next, left);
                weights.add(Arrays.asList(next,left));
            }
            if (right % width != 0 && !visited.contains(right)) {
                toVisit.add(right);
                t.addEdge(next, right);
                weights.add(Arrays.asList(next, right));
            }
            if (dow < width * height && !visited.contains(dow)) {
                toVisit.add(dow);
                t.addEdge(next, dow);
                weights.add(Arrays.asList(next,dow));
            }
        }
        while (weights.size() > 0) {
            for (int i = 0; i < weights.size(); i++) {
                int current = weights.get(i).get(0);
                int next = weights.get(i).get(1);
                if (graph.degree(next) > 0) {
                    weights.remove(i);
                    continue;
                }
                graph.addEdge(current,next);
                weights.remove(i);
            }
        }

    }
    /**
     * 5. Putting it all together
     * */
    public static void main(String[] args) {
        Maze maze = new Maze(50,20); //change width and height here
        maze.spanningTree();
        /*for (int i = 0; i < maze.graph.numVertices(); i++) {
            System.out.println("Degree of " + i + ": " + maze.graph.degree(i));
            System.out.println("Neighbours of " + i + ": " + Arrays.toString(maze.graph.neighbours(i)));
        }*/
        maze.print();
    }
}
