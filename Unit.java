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
 * @invar 	Each Unit can have his name or position
 * 			|isValidName(getName())
 * 			|canHaveAsName(this.getPosition())
 * 			|IsValidWeight(getWeight())
 * 			|isValidAgility(getAgility())
 * 			|isValidStrength(getStrength())
 * 			|isValidToughness(getToughness())
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
	 * @post   	If the given weight is a valid weight for any unit,
	 *         	the weight of this new unit is equal to the given
	 *         	weight. Otherwise, the weight of this new unit is equal
	 *         	to strength + agility divided by 2.
	 *      	| if (isValidWeight(weight))
	 *   	    |   then new.getWeight() == weight
	 *	       	|   else new.getWeight() == (strength + agility)/2
	 * @post   	If the given agility is a valid agility for any unit,
	 *         	the agility of this new unit is equal to the given
	 *         	agility. Otherwise, the agility of this new unit is equal
	 *         	to a random value between 25 and 100 (inclusively)
	 *      	| if (isValidAgility(agility))
	 *   	    |   then new.getAgility() == agility
	 *	       	|   else new.getAgility() == random(25,100)
	 * @post   	If the given strength is a valid strength for any unit,
	 *         	the strength of this new unit is equal to the given
	 *         	strength. Otherwise, the strength of this new unit is equal
	 *         	to a random value between 25 and 100 (inclusively)
	 *      	| if (isValidStrength(strength))
	 *   	    |   then new.getStrength() == strength
	 *	       	|   else new.getStrength() == random(25,100)
	 * @post   	If the given toughness is a valid toughness for any unit,
	 *         	the toughness of this new unit is equal to the given
	 *         	toughness. Otherwise, the toughness of this new unit is equal
	 *         	to a random value between 25 and 100 (inclusively)
	 *      	| if (isValidToughness(toughness))
	 *   	    |   then new.getToughness() == toughness
	 *	       	|   else new.getToughness() == random(25,100)
	 * @throws	ModelException
	 * 			Throws an exception if it isnt't valid
	 * 			| ! canHaveAsPosition(initialposition)
	 * 
	 */
	public Unit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness) throws ModelException{
		this.setName(name);
		if (! canHaveAsPosition(initialPosition))
			throw new ModelException();
		this.position = initialPosition;
		if (! isValidWeight(weight, 25,100))
			weight = (strength + agility)/2; 
		setWeight(weight, 25, 100);
		if (! isValidAgility(agility,25,100))
			agility = (int)(Math.random() * (100 - 25)) + 25;
		setAgility(agility, 25, 100);
		if (! isValidStrength(strength,25,100))
			strength = (int)(Math.random() * (100 - 25)) + 25;
		setStrength(strength, 25, 100);
		if (! isValidToughness(toughness,25,100))
			toughness = (int)(Math.random() * (100 - 25)) + 25;
		setToughness(toughness, 25, 100);
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
	/**
	 * 
	 * Return the weight of the unit.
	 * 
	 */
	@Basic @Raw
	public int getWeight(){
		return this.weight;
	}
	
	/**
	 * Variable registering the weight of the unit.
	 */
	private int weight;
	
	/**
	 * Check whether the given Weight is a valid Weight for
	 * any unit.
	 *  
	 * @param  	weight
	 *         	The Weight to check.
	 * @param	begin
	 * 			the lower bound 
	 * @param	end
	 * 			the upper bound 
	 * @return 	true if the position is valid: the weight is less or equal than end and more or equal than begin and strength and agility is less or equal than weight
	 *       	| if ((weight >= begin) && (weight <= end) && ((strength/agility)/2 <= weight))
	 * 			|	then result == true
	 * 			|	else result == false
	*/
	public boolean isValidWeight(int weight, int begin, int end) {
		return((weight >= begin) && (weight <= end) && ((this.getStrength()+this.getAgility())/2 <= weight));	
	}
	
	/**
	 * Set the Weight of this unit to the given Weight.
	 * 
	 * @param 	weight
	 *         	The new Weight for this unit.
	 * @param	begin
	 * 			the lower bound 
	 * @param	end
	 * 			the upper bound 
	 * @post   	If the given Weight is a valid Weight for any unit,
	 *         	the Weight of this new unit is equal to the given
	 *         	Weight.
	 *       	| if (isValidWeight(weight))
	 *       	|   then new.getWeight() == weight
	 */
	@Raw
	public void setWeight(int weight, int begin, int end) {
		if (isValidWeight(weight, begin, end))
			this.weight = weight;
	}
	
	/**
	 * Return the agility of this unit.
	 */
	@Basic @Raw
	public int getAgility() {
		return this.agility;
	}

	/**
	 * Check whether the given agility is a valid agility for
	 * any unit.
	 *  
	 * @param  	agility
	 *         	The agility to check.
	 * @param	begin
	 * 			the lower bound 
	 * @param	end
	 * 			the upper bound 
	 * @return 	true if the position is valid: the agility is less or equal than end and more or equal than begin
	 *       	| if ((agility >= begin) && (agility <= end)
	 * 			|	then result == true
	 * 			|	else result == false
	*/
	public static boolean isValidAgility(int agility, int begin, int end) {
		return ((agility >= begin) && (agility <= end)); 
	}

	/**
	 * Set the agility of this unit to the given agility.
	 * 
	 * @param  	agility
	 *         	The new agility for this unit.
	 * @param	begin
	 * 			the lower bound 
	 * @param	end
	 * 			the upper bound 
	 * @post   	If the given agility is a valid agility for any unit,
	 *         	the agility of this new unit is equal to the given
	 *         	agility.
	 *       	| if (isValidAgility(agility))
	 *       	|   then new.getAgility() == agility
	 */
	@Raw
	public void setAgility(int agility, int begin, int end) {
		if (isValidAgility(agility, begin, end))
			this.agility = agility;
	}

	/**
	 * Variable registering the agility of this unit.
	 */
	private int agility;

	
	/**
	 * Return the strength of this unit.
	 */
	@Basic @Raw
	public int getStrength() {
		return this.strength;
	}

	/**
	 * Check whether the given strength is a valid strength for
	 * any unit.
	 *  
	 * @param  	strength
	 *         	The strength to check.
	 * @param	begin
	 * 			the lower bound 
	 * @param	end
	 * 			the upper bound 
	 * @return 	true if the position is valid: the strength is less or equal than end and more or equal than begin
	 *       	| if ((strength >= begin) && (strength <= end)
	 * 			|	then result == true
	 * 			|	else result == false
	*/
	public static boolean isValidStrength(int strength, int begin, int end) {
		return ((strength >= begin) && (strength <= end)); 
	}

	/**
	 * Set the strength of this unit to the given strength.
	 * 
	 * @param  	strength
	 *         	The new strength for this unit.
	 * @param	begin
	 * 			the lower bound 
	 * @param	end
	 * 			the upper bound 
	 * @post   	If the given strength is a valid strength for any unit,
	 *         	the strength of this new unit is equal to the given
	 *         	strength.
	 *       	| if (isValidStrength(strength))
	 *       	|   then new.getStrength() == strength
	 */
	@Raw
	public void setStrength(int strength, int begin, int end) {
		if (isValidStrength(strength, begin, end))
			this.strength = strength;
	}

	/**
	 * Variable registering the strength of this unit.
	 */
	private int strength;
	

	/**
	 * Return the toughness of this unit.
	 */
	@Basic @Raw
	public int getToughness() {
		return this.toughness;
	}
	
	/**
	 * Check whether the given toughness is a valid toughness for
	 * any unit.
	 *  
	 * @param  	toughness
	 *         	The toughness to check.
	 * @param	begin
	 * 			the lower bound 
	 * @param	end
	 * 			the upper bound 
	 * @return 	true if the position is valid: the toughness is less or equal than end and more or equal than begin
	 *       	| if ((toughness >= begin) && (toughness <= end)
	 * 			|	then result == true
	 * 			|	else result == false
	*/
	public static boolean isValidToughness(int toughness, int begin, int end) {
		return ((toughness >= begin) && (toughness <= end)); 
	}
	
	/**
	 * Set the toughness of this unit to the given toughness.
	 * 
	 * @param  	toughness
	 *         	The new toughness for this unit.
	 * @param	begin
	 * 			the lower bound 
	 * @param	end
	 * 			the upper bound
	 * @post   	If the given toughness is a valid toughness for any unit,
	 *         	the toughness of this new unit is equal to the given
	 *         	toughness.
	 *       	| if (isValidToughness(toughness))
	 *       	|   then new.getToughness() == toughness
	 */
	@Raw
	public void setToughness(int toughness, int begin, int end) {
		if (isValidToughness(toughness, begin, end))
			this.toughness = toughness;
	}
	
	/**
	 * Variable registering the toughness of this unit.
	 */
	private int toughness;
	
}
