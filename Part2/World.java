package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.part2.listener.TerrainChangeListener;
import ogp.framework.util.*;
import hillbillies.model.Unit;

import java.util.*;


/**
 * 
 * 
 * @author	Bart Jacobs and Jordy Heusdens
 * @invar 
 * 
 * @version 1.0
 *
 */
public class World {
	
	/**
	 * 
	 * @param terrainTypes
	 * @param modelListener
	 * @throws ModelException 
	 */
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException{
		this.world = terrainTypes;
		World.x = terrainTypes.length;
		World.y = terrainTypes[0].length;
		World.z = terrainTypes[0][0].length;
	}
	private int[][][] world;
	
	/**
	 * 
	 */
	private static final int TYPE_AIR = 0;
	
	/**
	 * 
	 */
	private static final int TYPE_ROCK = 1;
	
	/**
	 * 
	 */
	private static final int TYPE_TREE = 2;
	
	/**
	 * 
	 */
	private static final int TYPE_WORKSHOP = 3;
	
	/**
	 * 
	 * @return
	 */
	@Basic @Raw
	public static int getX() {
		return World.x;
	}
	
	/**
	 * 
	 * @return
	 */
	@Basic @Raw
	public static int getY() {
		return World.y;
	}
	
	/**
	 * 
	 * @return
	 */
	@Basic @Raw
	public static int getZ() {
		return World.z;
	}
	
	/**
	 * 
	 */
	private static int x;
	
	/**
	 * 
	 */
	private static int y;
	
	/**
	 * 
	 */
	private static int z;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	@Basic @Raw
	public int getCubeType(int x, int y, int z){
		return this.world[x][y][z];
	}

	public void setCubeType(int x, int y, int z, int value){
		this.world[x][y][z] = value;
	}
	
	public Unit randomUnit(boolean enabledefaultbehaviour) throws ModelException{
		Random rand = new Random();
		String name = "HillBilly"; //random naam?
		int randomx = rand.nextInt(World.getX());
		int randomy = rand.nextInt(World.getY());
		int randomz = rand.nextInt(World.getZ());
		
		if (randomz != 0){
			while ((this.world[randomx][randomy][randomz]!=TYPE_AIR)&&(this.world[randomx][randomy][randomz]!=TYPE_WORKSHOP)){
				randomx = rand.nextInt(World.getX());
				randomy = rand.nextInt(World.getY());
				randomz = rand.nextInt(World.getZ());
				if (randomz == 0){break;}
				else{
					while (((this.world[randomx][randomy][randomz-1]!=TYPE_ROCK)&&(this.world[randomx][randomy][randomz-1]!=TYPE_TREE))){
						randomx = rand.nextInt(World.getX());
						randomy = rand.nextInt(World.getY());
						randomz = rand.nextInt(((World.getZ()-1) -1) + 1) + 1; //UPPER = 49; LOWER = 1
					}
				}
			}
		}
		
		int[] position = {randomx,randomy,randomz};
		int weight = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		int agility = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		int strength = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		int toughness = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		return new Unit(name,position,weight,agility,strength,toughness, enabledefaultbehaviour);
	}
	
	public void addUnit(Unit unit) throws ModelException{
		if (isFull())
			throw new ModelException("World is full");
		this.members.add(unit);
	}
	
	private boolean isFull(){
		return this.maxMembersWorld == this.members.size();
	}
	
	public Set<Unit> getUnits(){
		return this.members;
	}
	
	private final int maxMembersWorld = 100;
	private Set<Unit> members = new HashSet<Unit>(maxMembersWorld);

	private int UPPER = 100;
	private int LOWER = 25;
	
}
