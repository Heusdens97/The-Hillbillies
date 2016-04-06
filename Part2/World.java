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
		Unit.world = this; 
		Faction.world = this;
		this.modelListener = modelListener;
		this.border = new ConnectedToBorder(this.getX(), this.getY(), this.getZ());
		CheckTerrainWorld();
	}
	private int[][][] world;
	
	TerrainChangeListener modelListener;
	
	/**
	 * 
	 */
	public static final int TYPE_AIR = 0;
	
	/**
	 * 
	 */
	public static final int TYPE_ROCK = 1;
	
	/**
	 * 
	 */
	public static final int TYPE_TREE = 2;
	
	/**
	 * 
	 */
	public static final int TYPE_WORKSHOP = 3;
	
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
		this.worldMembers.add(unit);
	}
	
	private boolean isFull(){
		return this.maxMembersWorld == this.worldMembers.size();
	}
	
	public Set<Unit> getUnits(){
		return this.worldMembers;
	}
	
	private final int maxMembersWorld = 100;
	public Set<Unit> worldMembers = new HashSet<Unit>(maxMembersWorld);

	private int UPPER = 100;
	private int LOWER = 25;
	
	
	
	public Set<Faction> getActiveFactions() {
		return this.activeFactions;
	}
	
	public Set<Faction> activeFactions = new HashSet<Faction>(Faction.maxFactions);
	
	private ConnectedToBorder border;
	
	public boolean isSolidConnectedToBorder(int x, int y, int z){
		return border.isSolidConnectedToBorder(x, y, z);
	}
	
	public void advanceTime(double dt) throws ModelException{
		if (!gameOverCheck()){
			if (this.worldMembers.size() != 0)
				gameOverTimer -= dt;
			for (Iterator<Unit> i = worldMembers.iterator(); i.hasNext();) {
			    Unit unit = i.next();
			    if (!unit.isValidTime(dt))
					throw new ModelException();
				else {
					if (unit.getHitpoints() <= 0){
						//this.worldMembers.remove(unit);
						Faction fac = unit.getFaction();
						fac.members.remove(unit);
						if (fac.members.size() == 0) {
							this.activeFactions.remove(fac);
						}
						i.remove();
						System.out.println("dead, remaining: " + worldMembers.size());
						//drops objects
					}
					unit.advanceTime(dt);
				}
			}
		} else {
			int index = -1;
			for (Faction fac: activeFactions){
				index = fac.index;
			}
			JOptionPane.showMessageDialog(null, "Faction " + index + " won! The game will close");
			System.exit(0);
		}
	}
	
	private void CheckTerrainWorld() throws ModelException{
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
	
	public void collapse(int x, int y, int z) throws ModelException{
		int[] position = {x,y,z};
		setCubeType(x, y, z, TYPE_AIR);
		border.changeSolidToPassable(x, y, z);
		modelListener.notifyTerrainChanged(x, y, z);
		double probability = 0.25;
		Random rand = new Random();
		if (rand.nextDouble() <= probability){
			if (this.getCubeType(x, y, z)==World.TYPE_ROCK){
				createBoulder(this,position);
				System.out.println("spawn boulder");
			}else if (this.getCubeType(x, y, z)==World.TYPE_TREE){
				createLog(this,position);
				System.out.println("spawn log");
			}
		}
	}
	
	private static double gameOverTimer = 10000;
	
	private boolean gameOverCheck(){		
		return ((gameOverTimer <0)&&((this.worldMembers.size() <= 1)||(oneFactionLeft())));
	}
	
	private boolean oneFactionLeft(){
		return (this.activeFactions.size() == 1);
	}
	
	public Set<Boulder> boulders = new HashSet<Boulder>();
	
	public Set<Log> logs = new HashSet<Log>();
	
	public Boulder createBoulder(World world, int[] position) throws ModelException{
		return new Boulder(world, position);
	}
	
	public Log createLog(World world, int[] position) throws ModelException{
		return new Log(world, position);
	}
	
	public Set<Boulder> getBoulders(){
		return this.boulders;
	}
	
	public Set<Log> getLogs(){
		return this.logs;
	}
	
	public Log removeLog(double[] position){
		for (Log log: this.logs){
			if (log.getPosition() == position){
				logs.remove(log);
				return log;
			}
	  	}
		return null;
	}
	
	public Boulder removeBoulder(double[] position){
		for (Boulder boulder: this.boulders){
			if (boulder.getPosition() == position){
				boulders.remove(boulder);
				return boulder;
			}
		}
		return null;
	}
	
	public boolean isPassableTerrain(int[] position){
		return ((this.getCubeType(position[0], position[1], position[2])==World.TYPE_AIR)||(this.getCubeType(position[0], position[1], position[2])==World.TYPE_WORKSHOP)||(position[2]==0)); 
	}
	


}
