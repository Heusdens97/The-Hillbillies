package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import ogp.framework.util.ModelException;

/**
 * A class of a unit involving a name, initial position, weight, agility, strength, toughness and the
 * default behavior.
 * 
 * @author 	Bart Jacobs and Jordy Heusdens
 * @invar 	Each Unit can have this name or position
 * 			|isValidName(getName())
 * 			|canHaveAsName(this.getPosition())
 * @version 1.0
 *
 */
public class Unit {
	
	/**
	 * Initialize this new Unit with the next parameters
	 * 
	 * @param 	name
	 * 			The name of this unit
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
	 * @effect	The name of this new unit is set to the given name
	 * 			| this.setName(name)
	 * @post 	Position changes to the initial position
	 * 			|new.getPosition() == initialposition
	 * @throws	ModelException
	 * 			Throws an exception if it isnt't valid
	 * 			| ! canHaveAsPosition(initialposition)
	 * 
	 */
	public Unit(String name, int[] initialPosition) throws ModelException{
		this.setName(name);
		if (! canHaveAsPosition(initialPosition))
			throw new ModelException();
		this.position = initialPosition;
	}
	
	/**
	 * Set the Name of this Unit to the given Name.
	 * 
	 * @param  name
	 *         The new Name for this Unit.
	 * @post   The Name of this new Unit is equal to
	 *         the given Name.
	 *       | new.getName() == name
	 * @throws ModelException
	 *         The given Name is not a valid Name for any
	 *         Unit.
	 *       | ! isValidName(getName())
	 */
	@Raw
	public void setName(String name) throws ModelException {
		if (! isValidName(name))
			throw new ModelException(name);
		this.name = name;
	}
		
	/**
	 * 
	 * Return the name of the unit.
	 * 
	 */
	@Basic @Raw
	public String getName(){
		return this.name;
	}
	
	/**
	 * 
	 * Variable registering the name of this Unit.
	 * 
	 */
	private String name;
	
	 /**
	  * 
	  * Check whether this Unit can have the given name as its name.
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
	 * Variable registering the position of this Unit.
	 * 
	 */
	private final int[] position;
	
	/**
	 * 
	 * Returns the position of this unit.
	 * 
	 */
	@Basic @Raw @Immutable
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
	 * Check whether this Unit can have the given position as its position.
	 * 
	 * @param 	position
	 * 			The position of the unit	
	 * @return	true if the position is valid: the position is less than getMaxSize() and more or equal than 0
	 * 			| if (position[0]< getMaxSize() && position[0] >= 0) && (position[1]< getMaxSize() && position[1] >= 0) && (position[2]< getMaxSize() && position[2] >= 0)
	 * 			|	then result == true
	 * 			|	else result == false
	 */			
	@Raw
	public boolean canHaveAsPosition(int[] position){
		return (position[0]< getMaxSize() && position[0] >= 0) && (position[1]< getMaxSize() && position[1] >= 0) && (position[2]< getMaxSize() && position[2] >= 0);
	}
	
}
