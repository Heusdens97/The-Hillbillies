package hillbillies.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Astar {
	
	public static void main(String[] args) {
		
		// Make a algorithm tester class.
		int[][][] terrain = new int[100][100][100];

		Astar shortestPathTest = new Astar(terrain);
		List<int[]> path;
		
		// Attempt to run.
		try {
			path = shortestPathTest.searchPath(new int[]{5, 0, 1}, new int[]{14, 90, 1});
			System.out.println("\nSolution: ");
			for(int[] i : path){
				System.out.println(Arrays.toString(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	// Give the class some test variables in the constructor.
	public Astar(World world){
		this.terrain = world.getTerrain();
		terrainX = world.getX();
		terrainY = world.getY();
		terrainZ = world.getZ();
		this.world = world;
	}
	
	private World world;
	
	// Variables for terrain and terrain size.
	private int[][][] terrain;
	private int terrainX, terrainY, terrainZ;
	
	// Actual algorithm
	public List<int[]> searchPath(int[] start, int[] end) throws IOException{
		
		// Arraylist storing nodes which have already been checked.
		ArrayList<Node> hasBeenChecked = new ArrayList<Node>();
		// Priority queue used to store next possible nodes to be checked.
		PriorityQueue<Node> positionQueue = new PriorityQueue<Node>(new Comparator<Node>(){
			@Override
			public int compare(Node o1, Node o2) {
				int cX = o1.getPosition()[0], cY = o1.getPosition()[1], cZ = o1.getPosition()[2];
				int dX = o2.getPosition()[0], dY = o2.getPosition()[1], dZ = o2.getPosition()[2];
				
				double distance1 = Math.sqrt(Math.pow((cX-end[0]),2) + Math.pow((cY-end[1]),2) + Math.pow((cZ-end[2]),2));
				double distance2 = Math.sqrt(Math.pow((dX-end[0]),2) + Math.pow((dY-end[1]),2) + Math.pow((dZ-end[2]),2));
				
				return Double.compare(distance1, distance2);
				
			}			
		});
		
		// Define a lastNode object.
		Node lastNode = null;
		// Add starting position in a node to the PQ.
		positionQueue.add(new Node(start, null));
		// Loop through PQ till empty.
		while (!positionQueue.isEmpty()){
			// Get top of PQ (smallest element).
			Node p = positionQueue.remove();
			// Get position represented by node.
			int[] pos = p.getPosition();
			// Check if position is target position.
			if (pos[0] == end[0] && pos[1] == end[1] && pos[2] == end[2]){
				lastNode = p;
				break;
			}
			
			// Get set of possible neighbours.
			Set<int[]> neighbours = getNeighbours(p.getPosition());
			// Loop through the set.
			for (int[] i : neighbours){
				// Make node for each neighbour.
				Node n = new Node(i, p);
				// Check if this node is already contained in the PW or hasBeenChecked. (Save memory).
				if (!hasBeenChecked.contains(n) && !positionQueue.contains(n)){
					// If node is valid add it to PQ.
					positionQueue.add(n);
				}
			}
			
			// Add the recently removed node (from PQ) to hasBeenChecked.
			hasBeenChecked.add(p);
		}
		
		// Check if a lastNode was found.
		if (lastNode != null){
			// Make arrayList to store path.
			ArrayList<int[]> path = new ArrayList<int[]>();
			// Set current node to last node.
			Node currentNode = lastNode;
			// While current node is not null.
			while(currentNode != null){
				// Add position at node to path.
				path.add(currentNode.getPosition());
				// Get previous node and set it as current.
				currentNode = currentNode.getPrevious();
			}
			// Reverse the path to get the true path.
			Collections.reverse(path);
			// Return path.
			return path;
		}
		else
			return null;
		
		
	}

	// Gets valid neighbours for a given position.
	private Set<int[]> getNeighbours(int[] currentPosition){
		
		Set<int[]> neighbours = new HashSet<int[]>();
		int x = currentPosition[0], y = currentPosition[1], z = currentPosition[2]; 
		int[][] possibleNeighbours = new int[28][3];
		
		int index = 0;
		for(int i = -1; i <= 1; i++){
			for (int j = -1; j <= 1; j++){
				for (int q = -1; q <= 1; q++){
					possibleNeighbours[index++] = new int[]{x+q,y+j,z+i};	
				}
			}
		}
		
		// Loop through all neighbours and check if they are pass-able and contained within game borders.
		for (int[] i : possibleNeighbours)
			if ( 0 <= i[0] && i[0] < terrainX 
					&& 0 <= i[1] && i[1] < terrainY 
					&& 0 <= i[2] && i[2] < terrainZ
					&& isPassable(i) && !unit.isNeighbouringPassableTerrain(i)){
				neighbours.add(i);
			}
		
		return neighbours;
	}
	
	public static Unit unit;
	
	private boolean isPassable(int[] pos){
		return world.isPassableTerrain(pos)||pos[2]==0;
	}
	
	// A class for a single node in the algorithms priority queue.
	private class Node{
		
		private final int[] position;
		private final Node previous;
		
		public Node(int[] pos, Node previous) throws IOException{
			if (pos.length != 3) throw new IOException("Invalid position length given to node");
			position = pos;
			this.previous = previous;
		}

		public Node getPrevious() {
			return previous;
		}

		public int[] getPosition() {
			return position;
		}
		
		
		@Override
		public String toString(){
			return Arrays.toString(position);
		}
		
		
		@Override
		public boolean equals(Object obj){
			
			if (! (obj instanceof Node)) return false;
			
			Node o = (Node)obj;
			
			return (position[0] == o.getPosition()[0] && position[1] == o.getPosition()[1] && position[2] == o.getPosition()[2]);
		}
		
	}
	
}
