package hillbillies.model;

import java.util.*;



public class Faction{
	
	//faction names:
	//	- Appalachia
	//	- Austinville
	//	- Ozark
	//	- Simpsons (they live near Springfield)
	//	- New Jersey
	
	
	public Faction(Unit unit){
		//this.name = name;
		this.members.add(unit);
		this.index = world.activeFactions.size();
		world.activeFactions.add(this);
		unit.faction = this;
	}
	
	public int index;
	public static World world; 
	
	private static Faction createFaction(Unit unit){
		return new Faction(unit);
	}
	
	//private String name;
	public final static int maxFactions = 5;
	public final static int maxSizeFactions = 50;

	public static void addToFaction(Unit unit){
		//Random rand = new Random();
		if (world.activeFactions.size() != maxFactions){
//			String name = null;
//			while (name == null){
//				int n = rand.nextInt(factionNames.length);
//				name = factionNames[n];
//				if (factionNames[n]!= null)
//					factionNames[n]=null;
//			}
			createFaction(unit);
		} else {
			//minimum berekenen van de grootte vd factions
			int[] sizeFaction = new int[maxFactions];
			int i = 0;
			for (Faction fac : world.activeFactions){
				sizeFaction[i] = fac.members.size();
				i += 1;
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
	
	
	
//	public Set<Unit> getUnitsOfFaction(Faction faction){
//		for (Faction fac : activeFactions){
//			if (fac == faction){
//				return fac.members;
//			}
//		}
//		return null;
//	}
	
	public Set<Unit> getUnitsOfFaction(){
		return this.members;
	}
	
	
	//private static String[] factionNames = {"Appalachia", "Austinville", "Ozark", "Simpsons", "New Jersey"};
	
	
	public final Set<Unit> members = new HashSet<Unit>(maxFactions);
	
	public static boolean isFull(Set<Unit> faction){
		return faction.size() == maxSizeFactions;
	}
	
//	public static Faction getFaction(Unit unit) {
//		for (Faction fac : world.activeFactions){
//			if (fac.members.contains(unit))
//				return fac;
//		} 
//		return null;
//	}
	
	private static int getMinValue(int[] array){
		int min = array[0];
			for (int i = 1; i < array.length;i++){
				if(array[i]<min)
					min = array[i];
			}
		return min;
	}

//	public static Set<Faction> getActiveFactions(World world) {
//		return activeFactions;
//	}
//	
//	private static Set<Faction> activeFactions = new HashSet<Faction>(maxFactions);
	
	//activefactions verplaatsen naar world
	//getfaction naar unit
	
	// MORGEN:
	// - path finding algoritme implementeren
	// - game over


	

	
	
}
