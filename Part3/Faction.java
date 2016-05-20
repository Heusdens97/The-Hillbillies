package hillbillies.model;

import java.util.*;

/**
 * 
 * A class of factions with unit inside.
 *
 * @author  Bart Jacobs and Jordy Heusdens
 * @version 1.0
 */
public class Faction{

	/**
	 * Create a new faction with the single unit from the parameter.
	 * 
	 * @param unit
	 * 		The unit that we want in this faction.
	 */
	public Faction(Unit unit){
		this.members.add(unit);
		this.index = world.getActiveFactions().size();
		world.getActiveFactions().add(this);
		unit.faction = this;
		this.scheduler = new Scheduler();
		scheduler.setFaction(this);
	}
	
	private Scheduler scheduler;
	
	/**
	 * The index of the faction is the name of the faction. 
	 */
	public int index;
	
	/**
	 * The world in which this faction exists.
	 */
	public static World world; 
	
	private static Faction createFaction(Unit unit){
		return new Faction(unit);
	}
	
	/**
	 * The max amount of factions, which is 5.
	 */
	public final static int maxFactions = 5;
	
	/**
	 * The max size of a faction, which is 50.
	 */
	public final static int maxSizeFactions = 50;

	/**
	 * Method to add a unit to a faction.
	 * @param unit
	 * 		The unit we want to add to a faction.
	 * @post
	 * 		|if(world.activeFactions.size() != maxfactions)
	 * 		|	CreateFaction(unit)
	 * 		|else
	 * 		|	Smallestfaction.members.add(unit)
	 */
	public static void addToFaction(Unit unit){
		if (world.getActiveFactions().size() != maxFactions){
			createFaction(unit);
		} else {
			//minimum berekenen van de grootte vd factions
			int[] sizeFaction = new int[maxFactions];
			int i = 0;
			for (Faction fac : world.getActiveFactions()){
				sizeFaction[i++] = fac.members.size();
			}
			int min = getMinValue(sizeFaction);
			for (Faction fac : world.getActiveFactions()){ 
				if ((min == fac.members.size())&&(!isFull(fac.members))){
					fac.members.add(unit);
					unit.faction = fac;
					break;
				}
			}
		}
	}
	
	/**
	 * 
	 * @return a hashset that contains the members of this faction.
	 */
	public Set<Unit> getUnitsOfFaction(){
		return this.members;
	}
	
	/**
	 * The hashset that contains the members of this faction.
	 */
	public final Set<Unit> members = new HashSet<Unit>(maxFactions);
	
	/**
	 * 
	 * @param faction
	 * 		The faction we want to check.
	 * @return a boolean which tells us that the faction is full or not.
	 * 		
	 */
	public static boolean isFull(Set<Unit> faction){
		return faction.size() == maxSizeFactions;
	}
	
	private static int getMinValue(int[] array){
		int min = array[0];
			for (int i = 1; i < array.length;i++){
				if(array[i]<min)
					min = array[i];
			}
		return min;
	}

	/**
	 * 
	 * @return the scheduler for this faction.
	 */
	public Scheduler getScheduler(){
		return this.scheduler;
	}




	

	
	
}
