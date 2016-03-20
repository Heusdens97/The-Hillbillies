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
public class World { //abstract
	
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
		int randomx = rand.nextInt(this.getX());
		int randomy = rand.nextInt(this.getY());
		int randomz = rand.nextInt(this.getZ());
		
		if (randomz != 0){
			while ((this.world[randomx][randomy][randomz]!=TYPE_AIR)&&(this.world[randomx][randomy][randomz]!=TYPE_WORKSHOP)){
				randomz = rand.nextInt(this.getZ());
				if (randomz == 0){break;}
				else{
					while (((this.world[randomx][randomy][randomz-1]!=TYPE_ROCK)&&(this.world[randomx][randomy][randomz-1]!=TYPE_TREE))){
						randomz = rand.nextInt((this.getZ()-1 -1) + 1) + 1;
					}
				}
			}
		}
		
		int[] position = {randomx,randomy,randomz};
		int weight = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		int agility = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		int strength = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		int toughness = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		//Add to faction
		return new Unit(name,position,weight,agility,strength,toughness, enabledefaultbehaviour);
	}
	
	public void addUnit(Unit unit){
		
	}
	
	private int UPPER = 100;
	private int LOWER = 25;
	
}
