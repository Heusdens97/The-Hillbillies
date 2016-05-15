package hillbillies.model;

import java.util.*;

public class Faction{

	
	
	public Faction(Unit unit){
		//this.name = name;
		this.members.add(unit);
		this.index = world.activeFactions.size();
		world.activeFactions.add(this);
		unit.faction = this;
		this.scheduler = new Scheduler();
		scheduler.setFaction(this);
	}
	
	private Scheduler scheduler;
	
	public int index;
	public static World world; 
	
	private static Faction createFaction(Unit unit){
		return new Faction(unit);
	}
	
	public final static int maxFactions = 5;
	public final static int maxSizeFactions = 50;

	public static void addToFaction(Unit unit){
		if (world.activeFactions.size() != maxFactions){
			createFaction(unit);
		} else {
			//minimum berekenen van de grootte vd factions
			int[] sizeFaction = new int[maxFactions];
			int i = 0;
			for (Faction fac : world.activeFactions){
				sizeFaction[i++] = fac.members.size();
			}
			int min = getMinValue(sizeFaction);
			for (Faction fac : world.activeFactions){ 
				if ((min == fac.members.size())&&(!isFull(fac.members))){
					fac.members.add(unit);
					unit.faction = fac;
					break;
				}
			}
		}
	}
	
	
	
	public Set<Unit> getUnitsOfFaction(){
		return this.members;
	}
	

	
	public final Set<Unit> members = new HashSet<Unit>(maxFactions);
	
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

	
	public Scheduler getScheduler(){
		return this.scheduler;
	}




	

	
	
}
