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

public class Pathfinding {
	
	
	
	public Pathfinding(World world){
		this.terrain = world.getTerrain();
		X = world.getX();
		Y = world.getY();
		Z = world.getZ();
		this.world = world;
	}
	
	private World world;	
	private int[][][] terrain;
	private int X, Y, Z;	
	public List<int[]> searchPath(int[] start, int[] end) throws IOException{
		
		
		ArrayList<N> Checked = new ArrayList<N>();
		
		PriorityQueue<N> positionQueue = new PriorityQueue<N>(new Comparator<N>(){
			@Override
			public int compare(N o1, N o2) {
				int cX = o1.getPosition()[0], cY = o1.getPosition()[1], cZ = o1.getPosition()[2];
				int dX = o2.getPosition()[0], dY = o2.getPosition()[1], dZ = o2.getPosition()[2];
				
				double distance1 = Math.sqrt(Math.pow((cX-end[0]),2) + Math.pow((cY-end[1]),2) + Math.pow((cZ-end[2]),2));
				double distance2 = Math.sqrt(Math.pow((dX-end[0]),2) + Math.pow((dY-end[1]),2) + Math.pow((dZ-end[2]),2));
				
				return Double.compare(distance1, distance2);
				
			}			
		});
		
		
		N lastN = null;		
		positionQueue.add(new N(start, null));		
		while (!positionQueue.isEmpty()){			
			N p = positionQueue.remove();			
			int[] pos = p.getPosition();			
			if (pos[0] == end[0] && pos[1] == end[1] && pos[2] == end[2]){
				lastN = p;
				break;
			}			
			Set<int[]> neighbours = getNeighbours(p.getPosition());			
			for (int[] i : neighbours){				
				N n = new N(i, p);				
				if (!Checked.contains(n) && !positionQueue.contains(n)){					
					positionQueue.add(n);
				}
			}			
			Checked.add(p);
		}
		
		
		if (lastN != null){			
			ArrayList<int[]> path = new ArrayList<int[]>();			
			N currentN = lastN;			
			while(currentN != null){				
				path.add(currentN.getPosition());				
				currentN = currentN.getPrevious();
			}			
			Collections.reverse(path);			
			return path;
		}
		else
			return null;
		
		
	}

	
	private Set<int[]> getNeighbours(int[] currentPos){		
		Set<int[]> neighbours = new HashSet<int[]>();
		int x = currentPos[0], y = currentPos[1], z = currentPos[2]; 
		int[][] Possibilities = new int[28][3];		
		int k = 0;
		for(int i = -1; i <= 1; i++){
			for (int j = -1; j <= 1; j++){
				for (int q = -1; q <= 1; q++){
					Possibilities[k++] = new int[]{x+q,y+j,z+i};	
				}
			}
		}
		
		
		for (int[] i : Possibilities)
			if (0 <= i[0] && i[0] < X && 0 <= i[1] && i[1] < Y && 0 <= i[2] && i[2] < Z && isPassable(i) && !unit.isNeighbouringPassableTerrain(i)){
				neighbours.add(i);
			}
		
		return neighbours;
	}
	
	public static Unit unit;
	
	private boolean isPassable(int[] pos){
		return world.isPassableTerrain(pos)||pos[2]==0;
	}
	
	
	private class N{
		
		private final int[] position;
		private final N previous;
		
		public N(int[] pos, N previous) throws IOException{
			if (pos.length != 3) throw new IOException("Invalid position length given to N");
			position = pos;
			this.previous = previous;
		}

		public N getPrevious() {
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
			if (! (obj instanceof N)) 
				return false;			
			N node = (N)obj;			
			return (position[0] == node.getPosition()[0] && position[1] == node.getPosition()[1] && position[2] == node.getPosition()[2]);
		}
		
	}
	
}