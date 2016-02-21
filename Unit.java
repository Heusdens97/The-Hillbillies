package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import ogp.framework.util.ModelException;
/**
 * A class of unit involving a name, initialposition, weight, agility, strength, toughness and the
 * enableDefaultBehavior.
 * 
 * @author Bart Jacobs and Jordy Heusdens
 * @version 1.0
 *
 */
public class Unit {
	
	/**
	 * 
	 * @param 	name
	 * 			The name of the unit
	 * @param 	initialPosition
	 * 			The initial position of the unit, as an array with 3 elements {x,y,z}
	 * @param 	weight
	 * 			The weight of the unit
	 * @param 	agility
	 * 			The agility of the unit
	 * @param 	strength
	 * 			The strength of the unit
	 * @param 	toughness
	 * 			The toughness of the unit
	 * @param 	enableDefaultBehavior
	 * 			Whether the default behavior of the unit is enabled
	 * @post 	Name changes to the new name
	 * 			|new.getName() == name
	 * @post 	Position changes to the initial position
	 * 			|new.getPosition() == initialposition
	 * @throws	ModelException
	 * 			Throws an exception if it isnt't valid
	 * 			| ! isValidName(name)
	 * 			| ! isValidPosition(initialposition)
	 * 
	 */
	public Unit(String name, int[] initialPosition) throws ModelException{
		if (! isValidName(name) || ! isValidPosition(initialPosition))
			throw new ModelException(name);
		this.name = name;
		this.position = initialPosition;
	}
		
	/**
	 * 
	 * Returns the names of the unit.
	 * 
	 */
	@Basic
	public String getName(){
		return this.name;
	}
	
	/**
	 * 
	 * The name of the unit
	 * 
	 */
	private String name;
	
	 /**
	  * 
	  * @param 	name
	  * 		The name of the unit.
	  * @return true if the string is at least 2 character, starts with a capital letter and only uses valid characters(",',A-Z,a-z, )
	  *   		|if (name.length() < 2)
	  *   		|	then result == false
	  *   		|else if (!Character.isUpperCase(name.codePointAt(0)))
	  *   		|	then result == false
	  *   		|else if (!name.matches("[\"|\'|A-Z|a-z|\\s]"))
	  *   		| 	then result == false
	  *   		|else 
	  *   		| 	result == true
	  * 
	  */
	public boolean isValidName(String name){
		// mogelijkheid extra parameter voor aanpasbaarheid
		if (name.length() < 2) 
			return false;
		if (!Character.isUpperCase(name.codePointAt(0)))
			return false;
		// vraag: mag eerste letter " zijn?
		if (!name.matches("[\"|\'|A-Z|a-z|\\s]*"))
			return false;
		return true;		
	}
	
	/**
	 * 
	 * The position of the unit
	 * 
	 */
	private int[] position;
	
	/**
	 * 
	 * Returns the position of the unit.
	 * 
	 */
	@Basic
	public int[] getPosition(){
		return this.position;
	}
	
	/**
	 * 
	 * Return the maximal sizes of the world.
	 * 
	 */
	@Basic
	public static int getMaxSize(){
		return 50;
	}
	/**
	 * 
	 * @param position
	 * @return
	 */
	public boolean isValidPosition(int[] position){
		return (position[0]< getMaxSize() && position[0] >= 0) && (position[1]< getMaxSize() && position[1] >= 0) && (position[2]< getMaxSize() && position[2] >= 0);
	}
	
}
