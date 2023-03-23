import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ADTPackage.LinkedQueue;
import ADTPackage.LinkedStack;
import ADTPackage.QueueInterface;
import ADTPackage.StackInterface;
import GraphPackage.DirectedGraph;
import GraphPackage.GraphAlgorithmsInterface;
import GraphPackage.UndirectedGraph;

public class Test {

	public static int getRow(File maze) throws FileNotFoundException {
		int row = 0;

		Scanner scanner1 = new Scanner(maze);

		while (scanner1.hasNextLine()) {
			row = row + 1;
			scanner1.nextLine();

		}

		return row;
	}

	public static int getColumn(File maze) throws FileNotFoundException {
		Scanner scanner1 = new Scanner(maze);

		int column = 0;
		char[] ch = null;

		while (scanner1.hasNextLine()) {
			ch = scanner1.nextLine().toCharArray();
		}

		column = ch.length;
		return column;
	}

	public static void displayMaze(char[][] maze) {
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				System.out.print(maze[row][col]);
			}
			System.out.println();
		}
	}

	public static void displayMatrix(String[][] matrix, DirectedGraph<String> graph) {
		String[] vertexArray = graph.getVertexArray(graph);
		System.out.print("    ");
		for (int i = 0; i < vertexArray.length; i++) {
			System.out.print(vertexArray[i] + " ");
		}
		System.out.println();
		int i = 0;
		for (int row = 0; row < matrix.length; row++) {
			System.out.print(vertexArray[i] + " ");
			i++;
			for (int col = 0; col < matrix[row].length; col++) {
				if (matrix[row][col] == null) {
					System.out.print("0   ");
				} else {
					System.out.print(matrix[row][col] + "   ");
				}

			}
			System.out.println();
		}
	}

	public static void displayList(String[] list) {

		for (int i = 0; i < list.length; i++) {
			if (list[i] != null)
				System.out.print(list[i] + " | ");
		}
	}

	public static void addVertex(char[][] maze, DirectedGraph<String> graph) {
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {

				String index1 = Integer.toString(row) + "-" + Integer.toString(col);
				if (maze[row][col] == ' ') {
					graph.addVertex(index1);
				}

			}

		}
	}

	public static void makeGraph(char[][] maze, DirectedGraph<String> graph) {
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {

				String index1 = Integer.toString(row) + "-" + Integer.toString(col);
				int random = (int) (Math.random() * 4 + 1);
				if (maze[row][col] == ' ') {

					if (row != maze.length - 1) {

						if (maze[row + 1][col] == ' ') {
							String index2 = Integer.toString(row + 1) + "-" + Integer.toString(col);

							graph.addEdge(index1, index2, random);
						}
					}
					if (col != maze[row].length - 1) {
						if (maze[row][col + 1] == ' ') {
							String index3 = Integer.toString(row) + "-" + Integer.toString(col + 1);

							graph.addEdge(index1, index3, random);
						}
					}
				}

			}

		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		File mazeFile = new File("maze1.txt");

		int Rows = getRow(mazeFile);
		int Columns = getColumn(mazeFile);
		char maze[][] = new char[Rows][Columns];
		char[][] shortestMatrix = new char[Rows][Columns];
		char[][] cheapestMatrix = new char[Rows][Columns];
		char[][] BFSmatrix = new char[Rows][Columns];
		char[][] DFSmatrix = new char[Rows][Columns];

		Scanner scanner;

		try {
			scanner = new Scanner(mazeFile);

			for (int row = 0; scanner.hasNextLine() && row < Rows; row++) {
				char[] ch = scanner.nextLine().toCharArray();
				for (int i = 0; i < Columns && i < ch.length; i++) {
					maze[row][i] = ch[i];
					shortestMatrix[row][i] = ch[i];
					cheapestMatrix[row][i] = ch[i];
					BFSmatrix[row][i] = ch[i];
					DFSmatrix[row][i] = ch[i];
				}
			}

		} catch (FileNotFoundException e1) {

		}

		UndirectedGraph<String> graph = new UndirectedGraph<String>();

		addVertex(maze, graph);
		makeGraph(maze, graph);

		String beginVertex = "0-1";
		String endVertex = Integer.toString(Rows - 2) + "-" + Integer.toString(Columns - 1);

		StackInterface<String> path1 = new LinkedStack<String>();

		StackInterface<String> shortestPath = new LinkedStack<String>();
		shortestPath = graph.getShortestPathTraversal(beginVertex, endVertex, shortestPath);

		while (!shortestPath.isEmpty()) {
			String vertex = shortestPath.pop();
			int index = vertex.indexOf("-");
			int index1 = Integer.valueOf(vertex.substring(0, index));
			int index2 = Integer.valueOf(vertex.substring(index + 1, vertex.length()));
			shortestMatrix[index1][index2] = '.';
		}

		QueueInterface<String> BFS = new LinkedQueue<>();
		QueueInterface<String> DFS = new LinkedQueue<>();

		BFS = graph.getBreadthFirstTraversal(beginVertex, endVertex);
		DFS = graph.getDepthFirstTraversal(beginVertex, endVertex);

		int bfsCounter = 0;
		while (!BFS.isEmpty()) {
			String vertex = BFS.dequeue();
			int index = vertex.indexOf("-");
			int index1 = Integer.valueOf(vertex.substring(0, index));
			int index2 = Integer.valueOf(vertex.substring(index + 1, vertex.length()));
			BFSmatrix[index1][index2] = '.';
			bfsCounter++;
		}

		int dfsCounter = 0;
		while (!DFS.isEmpty()) {
			String vertex = DFS.dequeue();
			int index = vertex.indexOf("-");
			int index1 = Integer.valueOf(vertex.substring(0, index));
			int index2 = Integer.valueOf(vertex.substring(index + 1, vertex.length()));
			DFSmatrix[index1][index2] = '.';
			dfsCounter++;
		}

		StackInterface<String> path2 = new LinkedStack<String>();

		StackInterface<String> cheapestPath = new LinkedStack<String>();
		cheapestPath = graph.getCheapestPathTraversal(beginVertex, endVertex, cheapestPath);

		int cheapestCounter = 0;
		while (!cheapestPath.isEmpty()) {
			String vertex = cheapestPath.pop();
			int index = vertex.indexOf("-");
			int index1 = Integer.valueOf(vertex.substring(0, index));
			int index2 = Integer.valueOf(vertex.substring(index + 1, vertex.length()));
			cheapestMatrix[index1][index2] = '.';
			cheapestCounter++;
		}

		String[] list = graph.getAdjacencyMatrixList(graph);
		System.out.println("Adjacency Lists of Each Vertex of the Graph After Maze to Graph Operation");
		displayList(list);
		System.out.println();
		System.out.println();
		
		System.out.println("Adjacency Matrix of the Graph After Maze to Graph Operation");
		String[][] matrix = graph.getAdjacencyMatrix(graph);
		System.out.println();
		displayMatrix(matrix, graph);
		System.out.println();

		System.out.println("Number of Edges :" + graph.getNumberOfEdges());
		System.out.println();

		System.out.println("BFS");
		displayMaze(BFSmatrix);
		System.out.println();
		System.out.println("The number of visited vertices for BFS: " + bfsCounter);
		System.out.println();

		System.out.println("DFS");
		displayMaze(DFSmatrix);
		System.out.println();
		System.out.println("The number of visited vertices for DFS: " + dfsCounter);
		System.out.println();

		System.out.println("The number of visited vertices for Shortest Path :"
				+ graph.getShortestPath(beginVertex, endVertex, path1));
		System.out.println();
		System.out.println("Shortest Path");
		displayMaze(shortestMatrix);
		System.out.println();

		System.out.println("Cheapest Path");
		displayMaze(cheapestMatrix);
		System.out.println();
		System.out.println("The number of visited vertices for Weighted Graph: " + cheapestCounter);
		System.out.println();
		System.out.println("The cost of the cheapest path :" + graph.getCheapestPath(beginVertex, endVertex, path2));

	}

}
