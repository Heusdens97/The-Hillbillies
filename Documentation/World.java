package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import javax.swing.JOptionPane; 
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;
import ogp.framework.util.*;
import hillbillies.model.Unit;

import java.util.*;


/**
 * 
 * 
 * @author	Bart Jacobs and Jordy Heusdens
 * @invar The time of the world must be a valid time for any world.
 * 		|isValidTime(dt)
 * 
 * @version 1.0
 *
 */
public class World {
	
	/**
	 * 
	 * @param terrainTypes
	 * 		
	 * @param modelListener
	 * @throws ModelException 
	 */
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener){
		this.terrain = terrainTypes;
		this.x = terrainTypes.length;
		this.y = terrainTypes[0].length;
		this.z = terrainTypes[0][0].length;
		Faction.world = this;
		this.modelListener = modelListener;
		this.border = new ConnectedToBorder(this.getX(), this.getY(), this.getZ());
		CheckTerrainWorld();
		this.pathfinding = new Pathfinding(this);
	}
	private int[][][] terrain;
	
	/**
	 * A* pathfinding algorithm
	 */
	public Pathfinding pathfinding;
	
	/**
	 * 
	 * @return the terrain of the world
	 */
	public int[][][] getTerrain(){
		return this.terrain;
	}
	
	private TerrainChangeListener modelListener;
	
	/**
	 * We use a number to represent the type air in a matrix
	 */
	private static final int TYPE_AIR = 0;
	
	/**
	 * We use a number to represent the type rock in a matrix
	 */
	public static final int TYPE_ROCK = 1;
	
	/**
	 * We use a number to represent the type tree in a matrix
	 */
	public static final int TYPE_TREE = 2;
	
	/**
	 * We use a number to represent the type workshop in a matrix
	 */
	public static final int TYPE_WORKSHOP = 3;
	
	/**
	 * 
	 * @return the lenght of the world
	 */
	@Basic @Raw
	public int getX() {
		return this.x;
	}
	
	/**
	 * 
	 * @return the width of the world
	 */
	@Basic @Raw
	public int getY() {
		return this.y;
	}
	
	/**
	 * 
	 * @return the height of the world 
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
	 * @return The cubetype of position[x][y][z]
	 */
	@Basic @Raw
	public int getCubeType(int x, int y, int z){
		return this.terrain[x][y][z];
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param value
	 * @post
	 * 		|The cubetype of position[x][y][z] == Value
	 * 
	 */
	public void setCubeType(int x, int y, int z, int value){
		this.terrain[x][y][z] = value;
	}
	/**
	 * 
	 * @param enabledefaultbehaviour
	 * 			|Determines whether defaultbehaviour should be enabled or not
	 * @return A random unit with
	 * 			a random validposition
	 * 			a random weight between 25 and 100
	 * 			a random agility between 25 and 100
	 * 			a random strength between 25 and 100
	 * 			a random toughness between 25 and 100
	 * @throws ModelException
	 */
	public Unit randomUnit(boolean enabledefaultbehaviour) throws ModelException{
		Random rand = new Random();
		String name = "HillBilly";
		int[] position = randomPositionUnit(rand);
		int weight = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		int agility = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		int strength = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		int toughness = rand.nextInt((UPPER - LOWER) + 1) + LOWER;
		return new Unit(name,position,weight,agility,strength,toughness, enabledefaultbehaviour, this);
	}
	/**
	 * 
	 * @param rand
	 * 			java.util random generator
	 * @return a random valid position for a unit
	 * 			Which means that the position under the unit is solid
	 * 			and the position itself is passable
	 * 			
	 */
	private int[] randomPositionUnit(Random rand) {
		int randomx = rand.nextInt(this.getX());
		int randomy = rand.nextInt(this.getY());
		int randomz = rand.nextInt(this.getZ());
		if (randomz != 0){
			while (((this.terrain[randomx][randomy][randomz]!=TYPE_AIR)||(this.terrain[randomx][randomy][randomz]!=TYPE_WORKSHOP))&&((this.terrain[randomx][randomy][randomz-1]!=TYPE_ROCK)||(this.terrain[randomx][randomy][randomz-1]!=TYPE_TREE))){
				randomx = rand.nextInt(this.getX());
				randomy = rand.nextInt(this.getY());
				randomz = rand.nextInt(this.getZ());
				if (randomz != 0){break;}
				else{
					if (((this.terrain[randomx][randomy][randomz]==TYPE_AIR)||(this.terrain[randomx][randomy][randomz]==TYPE_WORKSHOP))&&((this.terrain[randomx][randomy][randomz-1]==TYPE_ROCK)||(this.terrain[randomx][randomy][randomz-1]==TYPE_TREE))){
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
	/**
	 * 
	 * @param unit
	 * 			The unit we want to add to the world
	 * @post	
	 * 			|if(world != full)
	 * 			|	add unit to the world
	 * @throws ModelException
	 * 			|if world == full
	 */
	public void addUnit(Unit unit) throws ModelException{
		if (isFull())
			throw new ModelException("World is full");
		this.worldMembers.add(unit);
	}
	
	private boolean isFull(){
		return this.maxMembersWorld == this.worldMembers.size();
	}
	/**
	 * 
	 * @return a hashset of all the living units
	 */
	public Set<Unit> getUnits(){
		return this.worldMembers;
	}
	
	private final int maxMembersWorld = 100;

	private Set<Unit> worldMembers = new HashSet<Unit>(maxMembersWorld); //changed to private

	private int UPPER = 100;
	private int LOWER = 25;
	
	
	/**
	 * 
	 * @return a hashset of the active factions (with living units) 
	 */
	public Set<Faction> getActiveFactions() {
		return this.activeFactions;
	}
	
	private Set<Faction> activeFactions = new HashSet<Faction>(Faction.maxFactions); // changed to private
	
	private ConnectedToBorder border;
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * 		 parameters of the position[x][y][z]
	 * @return
	 * 		 a recursive method that will check if the neighbours of the current position are connected to a border
	 */
	public boolean isSolidConnectedToBorder(int x, int y, int z){
		return border.isSolidConnectedToBorder(x, y, z);
	}
	
	/**
	 * Check whether the given time is a valid time for
	 * any unit.
	 *  
	 * @param  	dt
	 *         	The time to check.
	 * @return 	true if the time is valid: time equal or bigger than 0 and less than 0,2.
	 *       	| if (0 <= dt < 0.2)
	 *       	| 	then result == true
	 *       	| 	else result == false 
	*/
	private boolean isValidTime(double dt){
		return ((dt >= 0) && (dt <= 0.2));		
	}
	/**
	 * 
	 * @param dt
	 * 		 Time that passed since the last advancetime call
	 * @throws ModelException
	 * 		| if((dt <= 0) && (dt >= 0.2))
	 * @post
	 * 		| if(gameovertimer >= 0)
	 * 		|	update gameovertimer
	 * 		|	for(all units)
	 * 		|		if(unit == dead)
	 * 		|			remove unit
	 * 		|	for(all boulders and logs)
	 * 		|		call their advance time
	 */
	public void advanceTime(double dt) throws ModelException{
		if (!isValidTime(dt)){
			throw new ModelException();
		} else {
			if (!gameOverCheck()){
				if (this.worldMembers.size() != 0)
					gameOverTimer -= dt;
				for (Iterator<Unit> i = worldMembers.iterator(); i.hasNext();) {
				    Unit unit = i.next();
					if (unit.getHitpoints() <= 0){
						//this.worldMembers.remove(unit);
						Faction fac = unit.getFaction();
						fac.members.remove(unit);
						unit.removeBoulderAndAddToInventory();
						unit.removeLogAndAddToInventory();
						// dit hieronder weglaten ==> eigen faction na dood is onbestuurbaar
						if (fac.members.size() == 0) {
							this.activeFactions.remove(fac);
						}
						i.remove();
						System.out.println("dead, remaining: " + worldMembers.size());
					}
					unit.advanceTime(dt);
				}
				for (Boulder boulder : boulders){
					boulder.advanceTime(dt);
				}
				for (Log log: logs){
					log.advanceTime(dt);
				}
				CheckTerrainWorld();
			} else {
				int index = -1;
				for (Faction fac: activeFactions){
					if (fac.members.size() != 0){
						index = fac.index;
						break;
					}		
				}
				JOptionPane.showMessageDialog(null, "Faction " + index + " won! The game will close");
				System.exit(0);
			}
		}
	}
	
	private void CheckTerrainWorld() {
		for (int i = 0; i < this.getX();i++){
			for (int j = 0; j < this.getY();j++){
				for (int k = 0; k < this.getZ();k++){
					if (!this.isSolidConnectedToBorder(i, j, k)){
						collapse(i, j, k);
						System.out.println("collapse");
					}
				}
			}
		}
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * 		 parameters of the position[x][y][z]
	 * @post
	 * 		 Destroys the block on the current position
	 * 		 and has a 25% chance to create a boulder or a log
	 * 		 depending on the TYPE
	 */
	public void collapse(int x, int y, int z){
		int[] position = {x,y,z};
		int previousCubeType = this.getCubeType(x, y, z);
		setCubeType(x, y, z, TYPE_AIR);
		border.changeSolidToPassable(x, y, z);
		modelListener.notifyTerrainChanged(x, y, z);
		double probability = 0.25;
		Random rand = new Random();
		if (rand.nextDouble() <= probability){
			if (previousCubeType==World.TYPE_ROCK){
				createBoulder(this,position);
				System.out.println("spawn boulder");
			}else if (previousCubeType==World.TYPE_TREE){
				createLog(this,position);
				System.out.println("spawn log");
			}
		}
		//collapse();
	}
	
	private static double gameOverTimer = 300;
	
	private boolean gameOverCheck(){		
		return ((gameOverTimer <0)&&((this.worldMembers.size() <= 1)||(oneFactionLeft())));
	}
	
	private boolean oneFactionLeft(){
//		int count = 0;
//		for (Faction fac: activeFactions){
//			if (fac.members.size() != 0)
//				count += 1;
//		}
//		return (count == 1);
		//bovenste indien faction niet worden verwijderd uit activefactions 
		return (activeFactions.size() == 1);
	}
	/**
	 *  Hashset that contains all the boulders
	 */
	public Set<Boulder> boulders = new HashSet<Boulder>();
	/**
	 *  Hashset that contains all the logs
	 */
	public Set<Log> logs = new HashSet<Log>();
	
	private Boulder createBoulder(World world, int[] position){
		return new Boulder(world, position);
	}
	
	private Log createLog(World world, int[] position){
		return new Log(world, position);
	}
	/**
	 * 
	 * @return the hashset that contains the world's boulders
	 */
	public Set<Boulder> getBoulders(){
		return this.boulders;
	}
	/**
	 * 
	 * @return the hashset that contains the world's logs
	 */
	public Set<Log> getLogs(){
		return this.logs;
	}
	/**
	 * 
	 * @param position 
	 * 		 The position of the log that needs to be removed
	 * @return the log that was just removed 
	 */
	public Log removeLog(double[] position){
		for (Log log: this.logs){
			if (Arrays.equals(log.getPosition(), position)){
				logs.remove(log);
				return log;
			}
	  	}
		return null;
	}

	/**
	 * 
	 * @param position 
	 * 		 The position of the boulder that needs to be removed
	 * @return the boulder that was just removed 
	 */
	public Boulder removeBoulder(double[] position){
		for (Boulder boulder: this.boulders){
			if (Arrays.equals(boulder.getPosition(), position)){
				boulders.remove(boulder);
				return boulder;
			}
		}
		return null;
	}
	/**
	 * 
	 * @param position
	 * 		 position that needs to be checked
	 * @return whether the position is passable or not
	 * 		 TYPE_AIR && TYPE_WORKSHOP are passable
	 */
	public boolean isPassableTerrain(int[] position){
		return ((this.getCubeType(position[0], position[1], position[2])==World.TYPE_AIR)||(this.getCubeType(position[0], position[1], position[2])==World.TYPE_WORKSHOP)); 
	}
	/**
	 * 
	 * @param position
	 * 		 position that needs to be checked
	 * @return whether the position is impassable or not
	 * 		 TYPE_ROCK && TYPE_TREE are impassable
	 */
	public boolean isImpassableTerrain(int[] position){
		return ((this.getCubeType(position[0], position[1], position[2])==World.TYPE_ROCK)||(this.getCubeType(position[0], position[1], position[2])==World.TYPE_TREE)); 
	}


}
