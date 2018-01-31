//Vertex.java
//CS231
//Project9
//ThomasDeng

import java.util.*;

public class Graph {
	
	private ArrayList<Vertex> vertices;
	private Comparator<Vertex> comparator;
	
	
	//constructor
	public Graph(Comparator<Vertex> comp) {
		vertices = new ArrayList<Vertex>();
		comparator = comp; 
		}
	
	//return number of vertices in it	
	public int vertexCount() {
		return vertices.size();
		}
	
	//connects two vertices in the graph
	public void addEdge(Vertex v1, Vertex.Direction dir, Vertex v2) {
		if (!vertices.contains(v1) && !vertices.contains(v2) ) {
			vertices.add(v1);
			vertices.add(v2);			
			}
		else if (!vertices.contains(v1) && vertices.contains(v2)) {
			vertices.add(v1);
			}
		else if (vertices.contains(v1) && !vertices.contains(v2)) {
			vertices.add(v2);
			}
		v2.connect(v1, dir);
		v1.connect(v2, Vertex.opposite(dir));
		}  		
	
	//set every vertex to be unmarked
	private void unmarkAll() {
		for (Vertex v1: vertices) {
			v1.setMarked(false);
			}
		}
	
	//set the cost for every vertex to be high
	private void setCostHighAll() {
		for (Vertex v1: vertices) {
			v1.setCost(Integer.MAX_VALUE);
			}
		}
	
	//return vertices
	public ArrayList<Vertex> getVertices() {
		return vertices;
		}
	
	//returns a random Vertex in the graph
	public Vertex randVertex() {
		Random gen = new Random();
		int size = vertices.size();
		int rand = gen.nextInt(size);
		Vertex v1 = vertices.get(rand);
		return v1;
		}	
	
	//test method for Vertex comparison
	public void testPriority() {
		PriorityQueue<Vertex> q = new PriorityQueue<Vertex>(10, comparator);
		Vertex v1 = new Vertex(3,5, "v1");
		Vertex v2 = new Vertex (4,4,"v2");
		Vertex v3 = new Vertex (10,0,"v3");
		v1.setCost(2);
		v2.setCost(5);
		v3.setCost(3);
		q.add(v1);
		q.add(v3);
		q.add(v2);
		System.out.println(q.poll().toString());
		System.out.println(q.poll().toString());
		System.out.println(q.poll().toString());
		}	
	
	//sets the cost of every vertices in the graph to be their distance
	//to v0.
	//if a vertex is not connected with v0, then the cost will be 2147483647	
	public void shortestPath(Vertex v0) {
		this.unmarkAll();
		this.setCostHighAll();
		PriorityQueue<Vertex> q = new PriorityQueue<Vertex>(10, comparator);
		v0.setCost(0);
		q.add(v0);
		while (!q.isEmpty()) {
			Vertex v = q.poll();
// 			System.out.println("Start:******the vertex*******");
// 			System.out.println(v.toString());
			v.setMarked(true);
// 			System.out.println("****below are neighbors***");
			for (Vertex w: v.getNeighbors()) {
				if (!w.getMarked() && v.compareTo(w)>1) {
					w.setCost(v.getCost()+1);
					q.remove(w);
					q.add(w);
// 					System.out.println("*******");
// 					System.out.println(w.toString());
					}
				}
// 			System.out.println("How the queue looks like: ");
// 			for (Vertex v1: q) {
// 				System.out.println(v1.toString());
// 				}	
// 			System.out.println("!!!!end!!!!");	
			}
		}
	
	//test method
	public static void main(String Args[]) {
		Comparator<Vertex> comp = new VertexComparator();
		Graph g1 = new Graph(comp);
		g1.testPriority();
// 		Vertex v1 = new Vertex(3, 4, "hey");
// 		Vertex v2 = new Vertex(1, 1, "hi");
// 		Vertex v3 = new Vertex(30, 40, "oi");
// 		Vertex v4 = new Vertex(30, 4, "ole"); //not connected to anything
// 		g1.addEdge(v1, Vertex.Direction.NORTH, v2);
// 		System.out.println("* * * * * *");
// 		for (Vertex v: g1.vertices) {
// 			System.out.println("-----------");
// 			System.out.println(v.toString());
// 			}
// 		g1.addEdge(v2, Vertex.Direction.WEST, v3);
// 		System.out.println("* * * * * *");
// 		for (Vertex vv: g1.vertices) {
// 			System.out.println("-----------");
// 			System.out.println(vv.toString());
// 			}	
// 		System.out.println("#vertices: " + g1.vertexCount());
// 		g1.shortestPath(v1);
// 		System.out.println("");	
// 		System.out.println("from v1 to v2: " + v2.getCost());
// 		System.out.println("from v1 to v3: " + v3.getCost());	
// 		g1.shortestPath(v2);
// 		System.out.println("");	
// 		System.out.println("from v2 to v1: " + v1.getCost());
// 		System.out.println("from v2 to v3: " + v3.getCost());
// 		System.out.println("from v2 to v4: " + v4.getCost()); 	
		}				
	}
	
	//comparator for vertices
	class VertexComparator implements Comparator<Vertex> {
	    public int compare( Vertex v1, Vertex v2 ) {
	        // returns negative number if v2 has greater cost
	        return -v1.compareTo(v2);
	    	}
		}	
	