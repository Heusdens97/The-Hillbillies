package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import ogp.framework.util.ModelException;

/**
 * A class of a unit involving a name, initial position, weight, agility, strength, toughness and the
 * default behavior.
 * 
 * @author 	Bart Jacobs and Jordy Heusdens
 * @invar 	The name, position, weight, agility, strength, toughness, stamina, orientation
 * 			 of each unit must be a valid name, position, weight, agility, strength, toughness, stamina, orientation for any unit.
 * 			|isValidName(getName())
 * 			|canHaveAsPosition(this.getPosition())
 * 			|IsValidWeight(getWeight())
 * 			|isValidAgility(getAgility())
 * 			|isValidStrength(getStrength())
 * 			|isValidToughness(getToughness())
 * 			|isValidStamina(getStamina())
 * 			|isValidOrietation(getOrientation())
 * 
 * @version 1.0
 *
 */
public class Unit {
	
	/**
	 * Initialize this new Unit with the next parameters
	 * 
	 * @param 	name
	 * 			The name of this unit
	 * @param 	initialposition
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
	 * @pre		The given Stamina must be a valid Stamina for any unit.
	 *       	| isValidstamina(Stamina)
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
	 * @post   	The Stamina of this new unit is equal to the given
	 *         	Stamina.
	 *       	| new.getstamina() == MAX_STAMINA_AND_HITPOINTS (=200*(weight/100)*(toughness/100))
	 * @post   	The orientation is equal to math.pi/2
	 * 			| new.getorientation() == math.pi/2
	 * @throws	ModelException
	 * 			Throws an exception if it isnt't valid
	 * 			| ! canHaveAsPosition(initialposition)
	 */
	public Unit(String name, int[] initialposition, int weight, int agility, int strength, int toughness) throws ModelException{
		this.setName(name);
		double[] position = {initialposition[0]+0.5,initialposition[1]+0.5,initialposition[2]+0.5};
		if (! canHaveAsPosition(position))
			throw new ModelException();
		this.position = position;
		setWeight(weight, LOWER, UPPER);
		setAgility(agility, LOWER, UPPER);
		setStrength(strength, LOWER, UPPER);
		setToughness(toughness, LOWER, UPPER);
		this.setStamina(MAX_STAMINA_AND_HITPOINTS);
		this.setHitpoints(MAX_STAMINA_AND_HITPOINTS);
		setOrientation(Math.PI/2);
		
	}
	private int UPPER = 100;
	private int LOWER = 25;
	private int MAX_STAMINA_AND_HITPOINTS = 200*(this.weight/100)*(this.toughness/100);
	
	/**
	 * Set the Name of this Unit to the given Name.
	 * 
	 * @param  	name
	 *         	The new Name for this Unit.
	 * @post   	The Name of this new Unit is equal to
	 *         	the given Name.
	 *       	| new.getName() == name
	 * @throws 	ModelException
	 *         	The given Name is not a valid Name for any
	 *         	Unit.
	 *       	| ! isValidName(getName())
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
		if (!name.matches("[\"|\'|A-Z|a-z|\\s]*"))
			return false;
		return true;		
	}
	
	/**
	 * 
	 * Variable registering the position of this Unit.
	 * 
	 */
	private final double[] position;
	
	/**
	 * 
	 * Returns the position of this unit.
	 * 
	 */
	@Basic @Raw @Immutable
	public double[] getPosition(){
		return this.position;
	}
	
	/**
	 * Return the maximal size of the game world.
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
	public boolean canHaveAsPosition(double[] position){
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
		if (!isValidWeight(weight, begin, end))
			if (weight > end)
				weight = ((weight-begin) % (end - begin+1))+begin;		
			else if (weight < begin || weight < (this.getStrength()+this.getAgility())/2)
				if (this.getWeight() == 0)
					weight = begin;
				else
					weight = this.getWeight();
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
		if (!isValidAgility(agility, begin, end))
			if (agility > end)
				agility = ((agility-begin) % (end - begin+1))+begin;		
			else if (agility < begin)
				if (this.getAgility() == 0)
					agility = begin;
				else
					agility = this.getAgility();
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
		if (!isValidStrength(strength, begin, end))
			if (strength > end)
				strength = ((strength-begin) % (end - begin+1))+begin;		
			else if (strength < begin)
				if (this.getStrength() == 0)
					strength = begin;
				else
					strength = this.getStrength();
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
		if (!isValidToughness(toughness, begin, end))
			if (toughness > end)
				toughness = ((toughness-begin) % (end - begin+1))+begin;		
			else if (toughness < begin)
				if (this.getToughness() == 0)
					toughness = begin;
				else
					toughness = this.getToughness();
		this.toughness = toughness;
	}
	
	/**
	 * Variable registering the toughness of this unit.
	 */
	private int toughness;
	
	/**
	 * Return the stamina of this unit.
	 */
	@Basic @Raw
	public int getStamina() {
		return this.stamina;
	}

	/**
	 * Check whether the given stamina is a valid stamina for
	 * any unit.
	 *  
	 * @param  	stamina
	 *         	The stamina to check.
	 * @return 	true if the stamina is valid: stamina is between MAX_STAMINA_AND_HITPOINTS and 0 or equal his bounds
	 *       	| if (MAX_STAMINA_AND_HITPOINTS) >= stamina >= 0)
	 *       	| 	then result == true
	 *       	| 	else result == false
	*/
	public boolean isValidStamina(int stamina) {
		return (stamina >= 0 && stamina <= (MAX_STAMINA_AND_HITPOINTS));
	}

	/**
	 * Set the stamina of this unit to the given stamina.
	 * 
	 * @param  	stamina
	 *         	The new stamina for this unit.
	 * @pre    	The given stamina must be a valid stamina for any
	 *         	unit.
	 *       	| isValidStamina(stamina)
	 * @post   	The stamina of this unit is equal to the given
	 *         	stamina.
	 *       	| new.getStamina() == stamina
	 */
	@Raw
	public void setStamina(int stamina) {
		assert isValidStamina(stamina);
		this.stamina = stamina;
	}

	/**
	 * Variable registering the stamina of this unit.
	 */
	private int stamina;
	

	/**
	 * Return the hitpoints of this unit.
	 */
	@Basic @Raw
	public int getHitpoints() {
		return this.hitpoints;
	}
	
	/**
	 * Check whether the given hitpoints is a valid hitpoints for
	 * any unit.
	 *  
	 * @param 	hitpoints
	 *         	The hitpoints to check.
	 * @return 	true if the hitpoints is valid: hitpoints is between MAX_STAMINA_AND_HITPOINTS and 0 or equal his bounds
	 *       	| if (MAX_STAMINA_AND_HITPOINTS) >= hitpoints >= 0)
	 *       	| 	then result == true
	 *       	| 	else result == false
	*/
	public boolean isValidHitpoints(int hitpoints) {
		return (hitpoints >= 0 && hitpoints <= (MAX_STAMINA_AND_HITPOINTS));
	}
	
	/**
	 * Set the hitpoints of this unit to the given hitpoints.
	 * 
	 * @param  	hitpoints
	 *         	The new hitpoints for this unit.
	 * @pre    	The given hitpoints must be a valid hitpoints for any
	 *         	unit.
	 *       	| isValidHitpoints(hitpoints)
	 * @post   	The hitpoints of this unit is equal to the given
	 *         	hitpoints.
	 *       	| new.getHitpoints() == hitpoints
	 */
	@Raw
	public void setHitpoints(int hitpoints) {
		assert isValidHitpoints(hitpoints);
		this.hitpoints = hitpoints;
	}
	
	/**
	 * Variable registering the hitpoints of this unit.
	 */
	private int hitpoints;
	
	/**
	 * Return the orientation of this unit.
	 */
	@Basic @Raw
	public double getOrientation() {
		return this.orientation;
	}

	/**
	 * Check whether the given orientation is a valid orientation for
	 * any unit.
	 *  
	 * @param  	orientation
	 *         	The orientation to check.
	 * @return 	true if the orientation is valid: orientation is between 0 and 2*math.pi or equal his bounds.
	 *       	| if (0 <= orientation < 2*math.pi)
	 *       	| 	then result == true
	 *       	| 	else result == false 
	*/
	public static boolean isValidOrientation(double orientation) {
		return ((orientation >= 0) && (orientation < 2*Math.PI));
	}

	/**
	 * Set the orientation of this unit to the given orientation.
	 * 
	 * @param  	orientation
	 *         	The new orientation for this unit.
	 * @post   	If the given orientation is a valid orientation for any unit,
	 *         	the orientation of this new unit is equal to the given
	 *         	orientation.
	 *       	| if (isValidorientation(orientation))
	 *       	|   then new.getorientation() == orientation
	 */
	@Raw
	public void setOrientation(double orientation) {
		if (!isValidOrientation(orientation))
			if (orientation > 2*Math.PI)
				orientation = ((orientation) % (2*Math.PI - 0));		
			else if (orientation < 0)
				orientation = 0;
		this.orientation = orientation;
	}

	/**
	 * Variable registering the orientation of this unit.
	 */
	private double orientation;
	
	private boolean defaultBehaviour;
	private boolean sprinting;
	private int[] destiny;
	
	private double speed;
	
	public void advanceTime(double dt){
		
	}
	
	
	public boolean isMoving(){
		double []now = this.getPosition();
		advancetime();
		if (now == this.getPosition())
				return false;
		return true;
		
	}
	
	public void setSpeed(){
	    double speed = (1.5)*((this.getStrength()+this.getAgility())/((200)*(this.getWeight()/100)));
		if ((isMoving()) && (this.getDestiny() != null)){
			double[] cubePosition = {this.getDestiny()[0]+0.5,this.getDestiny()[1]+0.5,this.getDestiny()[2]+0.5};
			if (this.getPosition()[2]-

					Position[2] == -1)
				speed = (1/2)*speed;
			else if (this.getPosition()[2]-cubePosition[2] == 1)
				speed = (1.2)*speed;	
			else if (isSprinting())
				startSprinting();
				//stoppen als uitgeput
		}
		this.speed = speed;
	}
	
	public double getSpeed(){
		return this.speed;
	}
	
	public boolean isSprinting(){
		return this.sprinting;
	}
	
	public int[] getDestiny(){
		return this.destiny;
	}
	
	public void startSprinting(){
		if ((isMoving())&& (this.getStamina() > 0))
			this.speed = 2*(1.5)*((this.getStrength()+this.getAgility())/((200)*(this.getWeight()/100)));
			this.sprinting = true;
	}
	
	public void stopSprinting(){
		this.sprinting = false;
	}
	
	public void moveToAdjacent(int dx, int dy, int dz){
		double[] Adjacent = {this.getPosition()[0]+dx+0.5,this.getPosition()[1]+dy+0.5,this.getPosition()[2]+dz+0.5};
		if (canHaveAsPosition(Adjacent)){
			double d = Math.sqrt(Math.pow((Adjacent[0]-this.getPosition()[0]),2)+Math.pow((Adjacent[1]-this.getPosition()[1]),2)+Math.pow((Adjacent[2]-this.getPosition()[2]),2));
			double[] v = {this.getSpeed()*((Adjacent[0]-this.getPosition()[0])/d),this.getSpeed()*((Adjacent[1]-this.getPosition()[1])/d),this.getSpeed()*((Adjacent[2]-this.getPosition()[2])/d)};
			double[] New = {this.getPosition()[0] + v[0]*t,this.getPosition()[1] + v[1]*t,this.getPosition()[2] + v[2]*t};
			while (this.getPosition() != Adjacent){
				this.position[0] = this.position[0] +0.5;
				this.position[1] = this.position[1] +0.5;
				this.position[2] = this.position[2] +0.5;
				advancetime(0.2);
				
			}
		}
	}
	
	private double t = 0.2;
	
	public void moveTo(int[] cube){
		double[] position = {cube[0]+0.5,cube[1]+0.5,cube[2]+0.5};
		double x = this.getPosition()[0];
		double y = this.getPosition()[1];
		double z = this.getPosition()[2];
		int dx,dy,dz;
		while (this.getPosition() != position){
			if (x == position[0])
				dx = 0;
			else if (x < position[0])
				dx = 1;
			else
				dx = -1;
			if (y == position[1])
				dy = 0;
			else if (y < position[1])
				dy = 1;
			else
				dy = -1;
			if (z == position[0])
				dz = 0;
			else if (z < position[0])
				dz = 1;
			else
				dz = -1;
			moveToAdjacent(dx, dy, dz);
		}
	}

	
	
	
	
	
	
}
