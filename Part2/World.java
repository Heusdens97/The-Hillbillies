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
		this.x = terrainTypes.length;
		this.y = terrainTypes[0].length;
		this.z = terrainTypes[0][0].length;
		Unit.world = this; // of world meegeven hier beneden!!!
		Faction.world = this;
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
	public int getX() {
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 */
	@Basic @Raw
	public int getY() {
		return this.y;
	}
	
	/**
	 * 
	 * @return
	 */
	@Basic @Raw
	public int getZ() {
		return this.z;
	}
	
	/**
	 * 
	 */
	private int x;
	
	/**
	 * 
	 */
	private int y;
	
	/**
	 * 
	 */
	private int z;
	
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
		String name = "HillBilly";
		int[] position = randomPositionUnit(rand);
		int weight = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		int agility = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		int strength = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		int toughness = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		return new Unit(name,position,weight,agility,strength,toughness, enabledefaultbehaviour);
	}

	private int[] randomPositionUnit(Random rand) {
		int randomx = rand.nextInt(this.getX());
		int randomy = rand.nextInt(this.getY());
		int randomz = rand.nextInt(this.getZ());
		if (randomz != 0){
			while (((this.world[randomx][randomy][randomz]!=TYPE_AIR)||(this.world[randomx][randomy][randomz]!=TYPE_WORKSHOP))&&((this.world[randomx][randomy][randomz-1]!=TYPE_ROCK)||(this.world[randomx][randomy][randomz-1]!=TYPE_TREE))){
				randomx = rand.nextInt(this.getX());
				randomy = rand.nextInt(this.getY());
				randomz = rand.nextInt(this.getZ());
				if (randomz == 0){break;}
				else{
					if (((this.world[randomx][randomy][randomz]==TYPE_AIR)||(this.world[randomx][randomy][randomz]==TYPE_WORKSHOP))&&((this.world[randomx][randomy][randomz-1]==TYPE_ROCK)||(this.world[randomx][randomy][randomz-1]==TYPE_TREE))){
						break;
					}else{
						randomx = rand.nextInt(this.getX());
						randomy = rand.nextInt(this.getY());
						randomz = rand.nextInt(((this.getZ()-1) -1) + 1) + 1; //UPPER = 49; LOWER = 1 (random  between [1,49])
					}
				}
			}
		}
		return new int[] {randomx, randomy, randomz};
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
	
	
	
	public Set<Faction> getActiveFactions() {
		return this.activeFactions;
	}
	
	public Set<Faction> activeFactions = new HashSet<Faction>(Faction.maxFactions);
	
}
