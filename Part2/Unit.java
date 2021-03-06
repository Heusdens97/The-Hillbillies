package hillbillies.model;


import be.kuleuven.cs.som.annotate.*;
import ogp.framework.util.*;
import hillbillies.model.World;
import hillbillies.model.Faction;

import java.io.IOException;
import java.math.*;
import java.util.*;


/**
 * TO DO: 	- opdelen in classes?
 * 			- documentatie aanvullen
 * 			- tests schrijven
 * 			- "spaghetti" code verwijderen
 * 			- getters toevoegen
 */

/**
 * A class of a unit involving a name, initial position, weight, agility, strength, toughness and the
 * default behavior.
 * 
 * @author	Bart Jacobs and Jordy Heusdens
 * @invar 	The name of each unit must be a valid name for any unit.
 * 			|isValidName(getName())
 * @invar	The position of each unit must be a valid position for any unit.
 * 			|isValidPosition(this.getPosition())
 * @invar	The weight of each unit must be a valid weight for any unit.
 * 			|IsValidWeight(getWeight())
 * @invar	The agility of each unit must be a valid agility for any unit.
 * 			|isValidAgility(getAgility())
 * @invar	The strength of each unit must be a valid strength for any unit.
 * 			|isValidStrength(getStrength())
 * @invar	The toughness of each unit must be a valid toughness for any unit.
 * 			|isValidToughness(getToughness())
 * @invar	The stamina of each unit must be a valid stamina for any unit.
 * 			|isValidStamina(getStamina())
 * @invar 	The hitpoints of each unit must be a valid hitpoints for any unit.
 * 			|isValidHitpoints(getHitpoints()
 * @invar	The orientation of each unit must be a valid orientation for any unit.
 * 			|isValidOrietation(getOrientation())
 * @invar	The time of each unit must be a valid time for any unit.
 * 			|isValidTime(dt)
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
	 *       	| new.getstamina() == GetMaxStaminaAndHitPoints() (=200*(weight/100)*(toughness/100))
	 * @post   	The orientation is equal to math.pi/2
	 * 			| new.getorientation() == math.pi/2
	 * @throws	ModelException
	 * 			Throws an exception if it isnt't valid
	 * 			| ! canHaveAsPosition(initialposition)
	 */
	public Unit(String name, int[] initialposition, int weight, int agility, int strength, int toughness, boolean enableDefaultBehavior, World world) throws ModelException{
		this.world = world;
		this.setName(name);
		finaldest = initialposition;
		double[] position = {initialposition[0]+0.5,initialposition[1]+0.5,initialposition[2]+0.5};
		if (! isValidPosition(position))
			throw new ModelException();
		setPosition(position);
		setWeight(weight, LOWER, UPPER);
		setAgility(agility, LOWER, UPPER);
		setStrength(strength, LOWER, UPPER);
		setToughness(toughness, LOWER, UPPER);
		this.setStamina(this.getMaxStaminaAndHitPoints());
		this.stamina_double = this.getMaxStaminaAndHitPoints();
		this.hitpoints_double = this.getMaxStaminaAndHitPoints();
		this.setHitpoints(this.getMaxStaminaAndHitPoints());
		setOrientation(Math.PI/2);
		this.timetillrest = this.initial_timetillrest;
		setDefaultBehaviourEnabled(enableDefaultBehavior);
		arrowKeys = true;
		world.addUnit(this);
		Faction.addToFaction(this);
		setExperiencePoints(0);
		Pathfinding.unit = this;
	}
	
	private void setExperiencePoints(int experience){
		this.experience = experience;
	}
	
	private int experience;
	
	public int getExperiencePoints(){
		return this.experience;
	}
	
	public Faction faction;
	public World world;

	/**
	 * 
	 * @return
	 */
	public boolean isAlive(){
		return (this.getHitpoints() != 0);
	}
	

	
	public Faction getFaction(){
		return this.faction;
	}
	
	private int UPPER = 100;
	private int LOWER = 25;
	
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
	private boolean isValidName(String name){
		return ((name.length() >= 2)&& (Character.isUpperCase(name.codePointAt(0)))&& (name.matches("[\"|\'|A-Z|a-z|\\s]*")));	
	}
	
	/**
	 * 
	 * Variable registering the position of this Unit.
	 * 
	 */
	private double[] position;
	
	/**
	 * 
	 * Returns the position of this unit.
	 * 
	 */
	@Basic @Raw
	public double[] getPosition(){
		return this.position;
	}
	
	private void setPosition(double[] position){
		this.position = position;
	}
	
	/**
	 * Return the maximal size of the game world.
	 * 
	 */
	@Basic
	public int getMaxSize(){
		return Math.max(world.getX(), Math.max(world.getY(),world.getZ()));
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
	private boolean isValidPosition(double[] position){
		return (position[0]< getMaxSize() && position[0] >= 0.5) && (position[1]< getMaxSize() && position[1] >= 0.5) && (position[2]< getMaxSize() && position[2] >= 0.5);
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
	private boolean isValidWeight(int weight, int begin, int end) {
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
	private boolean isValidAgility(int agility, int begin, int end) {
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
	private boolean isValidStrength(int strength, int begin, int end) {
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
	private boolean isValidToughness(int toughness, int begin, int end) {
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
	 * @return 	true if the stamina is valid: stamina is between GetMaxStaminaAndHitPoints() and 0 or equal his bounds
	 *       	| if (GetMaxStaminaAndHitPoints()) >= stamina >= 0)
	 *       	| 	then result == true
	 *       	| 	else result == false
	*/
	private boolean isValidStamina(int stamina) {
		return ((stamina >= 0) && (stamina <= this.getMaxStaminaAndHitPoints()));
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
	private void setStamina(int stamina) {
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
	 * @return 	true if the hitpoints is valid: hitpoints is between GetMaxStaminaAndHitPoints() and 0 or equal his bounds
	 *       	| if (GetMaxStaminaAndHitPoints()) >= hitpoints >= 0)
	 *       	| 	then result == true
	 *       	| 	else result == false
	*/
	private boolean isValidHitpoints(int hitpoints) {
		return ((hitpoints >= 0) && (hitpoints <= this.getMaxStaminaAndHitPoints()));
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
	private void setHitpoints(int hitpoints) {
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
	 *       	| if (-math.pi <= orientation < math.pi)
	 *       	| 	then result == true
	 *       	| 	else result == false 
	*/
	private boolean isValidOrientation(double orientation) {
		return ((orientation >= -Math.PI) && (orientation <= Math.PI));
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
	private void setOrientation(double orientation) {
		if (!isValidOrientation(orientation))
			if (orientation > Math.PI)
				orientation = ((orientation) % (-Math.PI-Math.PI));
			else if (orientation < -Math.PI)
				orientation = -Math.PI;	
		this.orientation = orientation;
	}

	/**
	 * Variable registering the orientation of this unit.
	 */
	private double orientation;
	
	/**
	 * Variable registering the defaultBehaviour of this unit.
	 */
	private boolean defaultBehaviour;
	
	/**
	 * Variable registering the sprinting of this unit.
	 */
	private boolean sprinting;
	
	/**
	 * Variable registering the destiny of this unit.
	 */
	private double[] destiny;
	
	/**
	 * Variable registering the speed of this unit.
	 */
	private double speed;
	
	
	public void advanceTime(double dt){
		this.timetillrest -= dt;
		if (this.timetillrest <= 0){
			rest();
		}
		if (startfalling||isFalling()){
			fall();
		}
		advanceTime_Fight(dt);
		if ((isResting())&&(!isWorking())){
			if (this.getHitpoints() != this.getMaxStaminaAndHitPoints()){
				this.hitpoints_double = this.hitpoints_double + dt * (this.getToughness()/((double)(200)*0.2));
				if (this.hitpoints_double > this.getMaxStaminaAndHitPoints())
					this.setHitpoints(this.getMaxStaminaAndHitPoints());
				this.setHitpoints((int)(this.hitpoints_double));
				// soms nog een error?
			}
			else if(this.getStamina() != this.getMaxStaminaAndHitPoints()){
				this.stamina_double = this.stamina_double + dt * (this.getToughness()/((double)(100)*0.2));
				if (this.stamina_double > this.getMaxStaminaAndHitPoints())
					this.setStamina(this.getMaxStaminaAndHitPoints());
				this.setStamina((int)(this.stamina_double));
			}
			else{
				this.resting = false;
			}
		}
		advanceTime_Moving(dt);
		if (isDefaultBehaviourEnabled()&&(!isMoving())&&(!isMovingToWork)&&(!isWorking())&&(!isAttackingDefaultBehaviour)&&(!isAttacking())&&((int)this.getPosition()[0]==this.finaldest[0])&&((int)this.getPosition()[1]==this.finaldest[1])&&((int)this.getPosition()[2]==this.finaldest[2])&&(!isAttacking()))
			setDefaultBehaviourEnabled(true);
		if (((isWorking()||isMovingToWork))&&(!isMoving()&&((int)this.getPosition()[0]==this.finaldest[0])&&((int)this.getPosition()[1]==this.finaldest[1])&&((int)this.getPosition()[2]==this.finaldest[2]))){
			if (isWorking())
				this.worktime -= dt;
			int x = workat[0];
			int y = workat[1];
			int z = workat[2];
			workAt(x, y, z);
		}
		
		advanceTime_levelUp();
	}
	
	private int[] workat;
	
	private boolean startfalling = false;
	
	private int z_falling;

	private void fall() {
		if (!startfalling){
			this.z_falling= this.getCubeCoordinate()[2];
		}
		this.startfalling = true;
		int[] posUnder = {this.getCubeCoordinate()[0],this.getCubeCoordinate()[1],this.getCubeCoordinate()[2]-1};
		if (world.isPassableTerrain(posUnder)){
			moveToAdjacent(0, 0, -1);
			this.sprinting = false;
		} else if ((world.isImpassableTerrain(posUnder))||(this.getCubeCoordinate()[2]==0)){
			this.startfalling = false;
			int dz = this.z_falling - this.getCubeCoordinate()[2];
			int hitpoints = this.getHitpoints()-(dz*10);
			if (hitpoints < 0)
				setHitpoints(0);
			else {
				setHitpoints(hitpoints);
			}
		}
	}

	private void advanceTime_Moving(double dt) {
		if ((Math.round(this.getPosition()[0] -0.5)) != (Math.round(finaldest[0])) || (Math.round(this.getPosition()[1]-0.5)) != (Math.round(finaldest[1]))||(Math.round(this.getPosition()[2]-0.5)) != (Math.round(finaldest[2]))){
		     moveTo(finaldest);
		}else{
			arrowKeys = true;
		}
		if (this.getStamina() == 0 && isSprinting())
			stopSprinting();
		if ((this.getPosition() != null)&&(this.getDestiny()!=null)){
			if (!Arrays.equals(this.getPosition(), this.getDestiny())){
				double d = Math.sqrt(Math.pow((this.getDestiny()[0]-this.getPosition()[0]),2)+Math.pow((this.getDestiny()[1]-this.getPosition()[1]),2)+Math.pow((this.getDestiny()[2]-this.getPosition()[2]),2));
				double[] v = {this.getSpeed()*((this.getDestiny()[0]-this.getPosition()[0])/(double)d),this.getSpeed()*((this.getDestiny()[1]-this.getPosition()[1])/(double)d),this.getSpeed()*((this.getDestiny()[2]-this.getPosition()[2])/(double)d)};
				double[] New = {this.getPosition()[0] + v[0]*dt,this.getPosition()[1] + v[1]*dt,this.getPosition()[2] + v[2]*dt};
				setPosition(New);
				setOrientation(Math.atan2(v[1],v[0]));
				if (isSprinting()){
					this.stamina_double = this.stamina_double - (dt/(double)0.1);
					if (this.stamina_double < 0)
						setStamina(0);
					setStamina((int)this.stamina_double);
				}
			}
			if ((Math.abs((this.getPosition()[2]-this.getDestiny()[2])) == 1) || ((this.getPosition()[2] - this.getDestiny()[2]) == 0)){
				setSpeed();
			}
			if ((round(this.getPosition()[0],1) == this.getDestiny()[0])&&(round(this.getPosition()[1],1) == this.getDestiny()[1])&&(round(this.getPosition()[2],1) == this.getDestiny()[2])&&((isMoving()||isMovingToWork))){
				stopMoving();
				this.position = this.getDestiny();	
			}
		}
		if (arrowKeys){
			finaldest[0] = (int)this.getPosition()[0];
			finaldest[1] = (int)this.getPosition()[1];
			finaldest[2] = (int)this.getPosition()[2];
		}
	}
	
	/**
	 * 
	 * @param dt
	 * @throws ModelException
	 */
	private void advanceTime_Fight(double dt) {
		if (isDefaultBehaviourEnabled()){
			if ((defender != null)&&(this.fighttime == 1)){
				if ((defender!=this)&&(!this.isMoving())){
					if ((!isNeighbour(this.getCubeCoordinate(),defender.getCubeCoordinate()))&&(!Arrays.equals(this.getPosition(),defender.getPosition()))&&((int)this.getPosition()[0]==this.finaldest[0])&&((int)this.getPosition()[1]==this.finaldest[1])&&((int)this.getPosition()[2]==this.finaldest[2])){
						moveTo(defender.getCubeCoordinate());
					}
				}
				if ((defender.isAlive())&&(isAttackingDefaultBehaviour)&&(!defender.isMoving())){
					this.fight(defender);
				}
				if (!defender.isAlive()){
					isAttackingDefaultBehaviour = false;
					this.defender = null;
				}
			}
		}
		if (isAttacking()||isAttackingDefaultBehaviour){
			this.fighttime -= dt;
			this.resting = false;
		}
		if (this.fighttime <= 0){
			this.attacking = false;
			this.fighttime = 1;
		}
	}
	
	/**
	 * 
	 */
	public void advanceTime_levelUp(){
		if (this.getExperiencePoints() >= experienceLevelUp){
			setExperiencePoints(this.getExperiencePoints()- experienceLevelUp);
			Random rand = new Random();
			int Attribute = rand.nextInt(3);
			switch (Attribute){
				case 0:
					this.setStrength((this.getStrength() + 1),1,200);
					break;
				case 1:
					this.setAgility((this.getAgility() + 1),1,200);
					break;
				case 2:
					this.setToughness((this.getToughness() + 1),1,200);
					break;
				}
		}	
	}
	
	private final static int experienceLevelUp = 10;
	
	/**
	 * Variable registering the stamina of this unit in double.
	 */
	private double stamina_double;
	
	/**
	 * Variable registering the hitpoints of this unit in double.
	 */
	private double hitpoints_double;

	/**
	 * Check whether the given Unit is moving.
	 * 
	 * @return 	true if the unit is moving.
	 *       	| if (this.moving)
	 * 			|	then result == true
	 * 			|	else result == false
	*/
	public boolean isMoving(){
		return this.moving;
	}
	
	/**
	 * Variable registering whether the unit is moving.
	 */
	private boolean moving;
	
	/**
	 * The unit start to move, the boolean value changes.
	 *  
	 * @post 	moving has to change to true and resting has to change to false
	 * 			|this.moving = true;
	 * 			|this.resting = false; 
	*/
	private void startMoving(){
		this.moving = true;
		this.resting = false;	
	}
	/**
	 * The unit halts.
	 *  
	 * @post 	moving has to change to false.
	 * 			|this.moving = false;
	 * 			
	*/
	private void stopMoving(){
		this.moving = false;
	}
	
	/**
	 * Set the speed of this unit
	 * @throws ModelException 
	 * 
	 *
	 * @post   	if the unit is sprinting
	 * 			| speed = 2*speed
	 * @post	if the unit is declining
	 * 			| speed = 1/2 speed
	 * @post	if the unit is rising
	 * 			| speed = 1.2 speed
	 * @post 	if the unit is not moving
	 * 			|speed = 0
	 */
	@Raw
	private void setSpeed(){
	    double speed = 1.5*((this.getStrength()+this.getAgility())/(double)(200*(this.getWeight()/(double)100)));
	    if (isFalling()||startfalling){
	    	speed = 3;
	    } else {
	    	if ((isMoving()) && (this.getDestiny() != null)){
				if (((int)this.getPosition()[2]- (int)this.getDestiny()[2]) < 0) //verandert in part 2!
					speed = (1/(double)2)*speed;
				else if (((int)this.getPosition()[2]-(int)this.getDestiny()[2]) > 0)
					speed = (1.2)*speed;
				if (isSprinting())
					speed = 2*speed;
				
				//else
				//speed = speed
			}
			else if (!isMoving())
				speed = 0*speed;
	    }
		this.speed = speed;
	}
	/**
	 * Return the speed of this unit.
	 */
	@Basic @Raw
	public double getSpeed(){
		return this.speed;
	}
	
	/**
	 * Return whether the unit is sprinting.
	 */
	public boolean isSprinting(){
		return this.sprinting;
	}
	
	/**
	 * Return the destiny of this unit.
	 */
	@Basic @Raw
	private double[] getDestiny(){
		return this.destiny;
	}
	
	/**
	 * Set the destiny of this unit to the position witch is given.
	 * 
	 * @param  	position
	 * 			| the destiny of the unit
	 *
	 * @post   	if it's a valid position, it will be the destiny
	 * 			| if (isValidPosition(position)
	 * 			|	then this.destiny = position
	 * 			|	
	 */
	@Raw
	private void setDestiny(double[] position){
		if (isValidPosition(position))
			this.destiny = position;
	}
	/**
	 * The unit starts to sprint.
	 *  
	 * @post 	sprinting has to change to true and speed will change.
	 * 			|if ((isMoving) && (this.getStamina > 0))
	 * 			|	speed = 2*speed
	 * 			|	this.printing = true;
	*/
	public void startSprinting(){
		if ((isMoving())&& (this.getStamina() > 0))
			this.speed = 2*this.speed;
			this.sprinting = true;
	}
	/**
	 * The unit stops sprinting and the new speed will be calculated.
	 * @throws ModelException 
	 *  
	 * @post 	sprinting has to change to false.
	 * 			|this.sprinting = false
	 * 			| setSpeed();
	*/
	public void stopSprinting(){
		this.sprinting = false;
		setSpeed();
	}
	/**
	 * 
	 * The unit moves to the adjacent position
	 * 
	 * @param 	dx
	 * 			position will be the position + dx
	 * @param 	dy
	 * 			position will be the position + dy
	 * @param 	dz
	 * 			position will be the position + dz
	 * @throws 	ModelException
	 * 			if it's not a valid position.
	 */
	public void moveToAdjacent(int dx, int dy, int dz){
		if (!isMoving()){
			double[] Adjacent = {this.getPosition()[0]+dx,this.getPosition()[1]+dy,this.getPosition()[2]+dz};
			int[] intAdjacent = {(int)Adjacent[0],(int)Adjacent[1],(int)Adjacent[2]};
			if (isValidPosition(Adjacent)&&(getWorld().isPassableTerrain(intAdjacent)||intAdjacent[2]==0)){
				setDestiny(Adjacent);
				this.attacking = false;
				startMoving();
				setSpeed();
				if (!isFalling())
					setExperiencePoints(this.getExperiencePoints()+movementExperience);
			}
		}
	}
	
	private static final int movementExperience = 1;
	
	/**
	 * Variable registering the final destination of this unit.
	 */
	private int[] finaldest;
	
	/**
	 * Variable registering the state of the arrowKeys.
	 */
	private Boolean arrowKeys;

	private List<int[]> path;
	
	public void moveTo(int[] cube){
		double[] doublepos = {cube[0]+0.5,cube[1]+0.5,cube[2]+0.5};
		if (isValidPosition(doublepos)){
			this.working = false;
			this.arrowKeys = false;
			this.finaldest = cube;
			if (!Arrays.equals(this.getPosition(),doublepos)&&(!isMoving())){
				// path altijd opnieuw laten berekenen ==> mogelijk terrain changes
				try {
					path = getWorld().pathfinding.searchPath(this.getCubeCoordinate(), cube);
					if (path == null)
						throw new ModelException("there is no pad");
					else {
						path.remove(0);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ModelException e) {
					System.out.println("no pad available");
					this.finaldest = this.getCubeCoordinate();
					e.printStackTrace();
				}
				if (path != null){
					int [] first = path.get(0);
					int dx = first[0] - this.getCubeCoordinate()[0];
					int dy = first[1] - this.getCubeCoordinate()[1];
					int dz = first[2] - this.getCubeCoordinate()[2];  
					moveToAdjacent(dx, dy, dz);
					path.remove(0);
				}
			}
		}
	}
	
	private World getWorld(){
		return this.world;
	}	
	
	/**
	 * Variable registering whether the unit is working.
	 */
	private boolean working;
	/**
	 * 
	 * returns whether the unit is working.
	 * 
	 */
	public boolean isWorking(){
		return this.working;
	}

	private boolean isMovingToWork = false;
	
	private boolean isAdjacent (int[] me, int[] other){
		return (((Math.abs(me[0]-other[0]) == 1)||(me[0]-other[0]) == 0)
				&&((Math.abs(me[1]-other[1]) == 1)||((me[1]-other[1]) == 0))
				&&(((me[0]-other[0]) != 0)||((me[1]-other[1]) != 0)));
	}
	/**
	 * 
	 * the unit starts working.
	 */
	public void workAt(int x, int y, int z){ 
		//pathfinding met workat is soms nogal raar?
		try {
			if (z == 0)
				throw new ModelException("Not allowed to work at level 0");
		} catch (ModelException e){
			e.printStackTrace();
			return;
		}
		int[] position = {x,y,z};
		double[] doubleposition = {x+0.5, y+0.5, z+0.5};
		this.workat = position;
		if ((!isAdjacent(this.getCubeCoordinate(), position)&&(!Arrays.equals(this.getPosition(), doubleposition)))&&(!isMoving())&&(!isWorking())&&(!isMovingToWork)){
			for (int i = x-1; i <= x+1;i++){
				for (int j = y-1; j <= y+1;j++){
					int[] pos = {i,j,z};
					double[] doublepos = {i+0.5,j+0.5,z+0.5};
					if ((isAdjacent(position, pos))&&(isValidPosition(doublepos))&&(world.isPassableTerrain(pos))){
						System.out.println(pos);
						moveTo(pos);
						this.isMovingToWork = true;
						break;
					}
				}
			}
		} else{
			this.isMovingToWork = false;
		}
		if ((!isWorking())&&(!isMovingToWork)&&(isAdjacent(this.getCubeCoordinate(), position))){
			this.setOrientation((Math.atan2((doubleposition[1]-this.getPosition()[1]),(doubleposition[0]-this.getPosition()[0]))));
			this.resting = false;
			this.worktime = (500/(double)this.getStrength());
			this.working = true;
			this.sprinting = false;
		}else{
			if ((this.worktime <=0)&&(!isMovingToWork)){
				this.working = false;
				//mogelijkheid om associatie tussen unit en objects toe te voegen
				if ((this.isCarryingBoulder())||(this.isCarryingLog())){
					if (this.isCarryingBoulder()){
						removeBoulderAndAddToInventory();
					} else if (this.isCarryingLog()){
						removeLogAndAddToInventory();
					}
				}
				else if ((world.getCubeType(x, y, z) == World.TYPE_WORKSHOP)&&(logAvailable(doubleposition)&&(boulderAvailable(doubleposition)))){
					world.removeBoulder(doubleposition);
					world.removeLog(doubleposition);
					setWeight(getWeight()+ 5, 1, 200);
					setToughness(getToughness()+5, 1, 200);
				}
				else if (boulderAvailable(doubleposition)){
					Boulder boulder = world.removeBoulder(doubleposition);
					this.carryingBoulder.add(boulder);
					setWeight(getWeight()+boulder.getWeight(), 1, 200+boulder.getWeight());
				}
				else if (logAvailable(doubleposition)){
					Log log = world.removeLog(doubleposition);
					this.carryingLog.add(log);
					setWeight(getWeight()+log.getWeight(), 1, 200+log.getWeight());
				}
				else if ((world.getCubeType(x, y, z)==World.TYPE_TREE)||(world.getCubeType(x, y, z)==World.TYPE_ROCK)){
					world.collapse(x, y, z);
				}
				setExperiencePoints(this.getExperiencePoints()+workExperience);
			}
		}
	}
	
	public void removeBoulderAndAddToInventory(){
		for (Boulder boulder: carryingBoulder){
			boulder.setPosition(this.getPosition()); // mss xyz van work
			carryingBoulder.remove(boulder);
			setWeight(this.getWeight()-boulder.getWeight(), 1, 200);
			world.boulders.add(boulder);
		}
	}
	
	public void removeLogAndAddToInventory(){
		for (Log log: carryingLog){
			log.setPosition(this.getPosition());
			carryingLog.remove(log);
			setWeight(this.getWeight()-log.getWeight(), 1, 200);
			world.logs.add(log);
		}
	}
	
	private boolean logAvailable(double[] position){
		for (Log log: world.logs){
			if (Arrays.equals(log.getPosition(), position)){
				return true;
			}
		}	
		return false;
	}
	
	private boolean boulderAvailable(double[] position){
		for (Boulder boulder: world.boulders){
			if (Arrays.equals(boulder.getPosition(),position)){
				return true;
			}
		}
		return false;
	}
	
	
	private Set<Boulder> carryingBoulder = new HashSet<Boulder>();
	
	public boolean isCarryingBoulder(){
		return (this.carryingBoulder.size() != 0);
	}
	
	private Set<Log> carryingLog = new HashSet<Log>();
	
	public boolean isCarryingLog(){
		return (this.carryingLog.size() != 0);
	}
	
	private static final int workExperience = 10;
	/**
	 * Variable registering the worktime of this unit.
	 */
	private double worktime;
	/**
	 * 
	 * returns the max stamina and hitpoints.
	 */
	@Basic @Raw
	public int getMaxStaminaAndHitPoints(){
		return (int)(200*(this.getWeight()/(double)100)*(this.getToughness()/(double)100));
	}
	
	/**
	 * 
	 * returns the cubecoordinates.
	 */
	@Basic @Raw
	public int[] getCubeCoordinate() {
		return new int[] {(int) this.getPosition()[0],(int) this.getPosition()[1],(int) this.getPosition()[2]};
	}
	
	/**
	 * Variable registering the defender of this unit.
	 */
	private Unit defender;
	
	/**
	 * Variable registering the fighttime of this unit.
	 */
	private double fighttime;
	/**
	 * 
	 * the unit start to fight.
	 * 
	 * @param 	defender
	 * 			the defending unit
	 */
	public void fight(Unit defender){ 
		this.working = false;
		if (!isAttacking())
			this.fighttime = 1;
		if ((!isMoving())&&(defender != null)&&(defender != this)&&(this.faction != defender.faction)&&((Arrays.equals(this.getPosition(),defender.getPosition()))||(isNeighbour(this.getCubeCoordinate(),defender.getCubeCoordinate())))){
			attack(this,defender); 
			defend(defender,this);
			// mogelijkheid met een parameter
			this.setOrientation((Math.atan2((defender.getPosition()[1]-this.getPosition()[1]),(defender.getPosition()[0]-this.getPosition()[0]))));
			defender.setOrientation((Math.atan2((this.getPosition()[1]-defender.getPosition()[1]),(this.getPosition()[0]-defender.getPosition()[0]))));
		}		
	}
	/**
	 * 
	 * attacker attacks the defender
	 * 
	 * @param	attacker
	 * 			the attacker
	 * @param	defender
	 * 			the defender
	 */
	private void attack(Unit attacker,Unit defender){
		attacker.attacking = true;	
		// mogelijkheid met een parameter
	}
	/**
	 * 
	 * defender defends relative to the attacker.
	 * 
	 * @param	attacker
	 * 			the attacker
	 * @param	defender
	 * 			the defender
	 */
	private void defend(Unit defender,Unit attacker){
		// mogelijkheid met een parameter
		defender.attacking = false;
		attacker.defender = defender;
	//	defender.defender = defender;
		double probability_dodge = (0.20)*((defender.getAgility())/((double)attacker.getAgility()));
		double probability_block = (0.25)*((defender.getStrength()+defender.getAgility())/((double)attacker.getStrength()+attacker.getAgility()));
		Random rand = new Random();
		if (rand.nextDouble() <= probability_dodge){
			dodge(defender,attacker);
			defender.setExperiencePoints(defender.getExperiencePoints()+fightExperience);
			System.out.println("dodged");
		} else if (rand.nextDouble() <= probability_block){
			System.out.println("blocked");
			defender.setExperiencePoints(defender.getExperiencePoints()+fightExperience);
		} else {
			defender.hitpoints_double = defender.hitpoints_double - (attacker.getStrength()/(double)10);
			if (defender.hitpoints_double < 0){
				defender.setHitpoints(0);
			} else {
				defender.setHitpoints((int)(defender.hitpoints_double));
			}
			attacker.setExperiencePoints(attacker.getExperiencePoints()+fightExperience);
			System.out.println("hit");
		}
	}	
	
	private static final int fightExperience = 20;
	/**
	 * 
	 * defender dodges the attacker
	 * 
	 * @param	attacker
	 * 			the attacker
	 * @param	defender
	 * 			the defender
	 */
	private void dodge(Unit defender,Unit attacker){
		int random_x = 0;
		int random_y = 0;
		Random rand = new Random();
		int randomNumber = rand.nextInt(8);
		// wat als hem dodget naar buiten de wereld? while adden met passable etc
		switch (randomNumber){
			case 0:
				random_x=1;
				random_y=-1;
				break;
			case 1:
				random_x=0;
				random_y=-1;
				break;
			case 2:
				random_x=-1;
				random_y=-1;
				break;
			case 3:
				random_x=-1;
				random_y=0;
				break;
			case 4:
				random_x=1;
				random_y=0;
				break;
			case 5:
				random_x=-1;
				random_y=1;
				break;
			case 6:
				random_x=0;
				random_y=1;
				break;
			case 7:
				random_x=1;
				random_y=1;	
				break;
		}
	    defender.moveToAdjacent(random_x, random_y, 0);
	}
	/**
	 * 
	 * returns whetehr the unit me is a neighbour of other.
	 * 
	 * @param	me
	 * 			a unit
	 * @param	other
	 * 			a unit
	 */
	private boolean isNeighbour (int[] me, int[] other){
		return (((Math.abs(me[0]-other[0]) == 1)||(me[0]-other[0]) == 0)
				&&((Math.abs(me[1]-other[1]) == 1)||((me[1]-other[1]) == 0))
				&&(((me[0]-other[0]) != 0)||((me[1]-other[1]) != 0)||((me[2]-other[2]) != 0))
				&&((Math.abs(me[2]-other[2]) == 1)||(me[2]-other[2]) == 0));
	}
	
	public boolean isNeighbouringPassableTerrain(int[] position){
		int[] cube = Arrays.copyOf(position, 3);
		for (int i=0; i < 200; i++){
			position = Arrays.copyOf(cube, 3);
			Random rand = new Random();
			int max = 1;
			int min = -1;
			int x = rand.nextInt((max - min) + 1) + min;
			int y = rand.nextInt((max - min) + 1) + min;
			int z = rand.nextInt((max - min) + 1) + min;
			position[0] += x;
			position[1] += y;
			position[2] += z;
			double[] doubleposition = {position[0]+0.5,position[1]+0.5,position[2]+0.5};
			if ((isValidPosition(doubleposition))&&(world.isImpassableTerrain(position)||(position[2]==0)) && (isNeighbour(cube, position))){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Variable registering the initial time till rest of this unit.
	 */
	private double initial_timetillrest = 180;
	/**
	 * Variable registering the time till rest of this unit.
	 */
	private double timetillrest;
	/**
	 * 
	 * the unit starts resting.
	 * 
	 */
	public void rest(){
		if ((this.getStamina() < this.getMaxStaminaAndHitPoints()) || (this.getHitpoints() < this.getMaxStaminaAndHitPoints())){
			this.sprinting = false;
			this.resting = true;
			this.working = false;
			this.timetillrest = initial_timetillrest;
		}
	}
	
	/**
	 * Variable registering whether the unit is resting.
	 */
	private boolean resting;
	
	public boolean isResting(){
		return this.resting;	
	}
	/**
	 * 
	 * returns whether the unit is attacking.
	 */
	public boolean isAttacking(){
		return this.attacking;
	}
	/**
	 * Variable registering whether the unit is attacking.
	 */
	private boolean attacking;
	/**
	 * 
	 * start defaultbehaviour
	 * @throws ModelException 
	 */
	private void startDefaultBehaviour() {
		if ((!isWorking())&& (!isResting())&&(!isMoving())){
			this.defaultBehaviour = true;
			boolean checker = true;
			Random rand = new Random();
			while (checker){
				int randomNumber = rand.nextInt(4);
				switch (randomNumber){
					case 0:
						if ((getStamina() < getMaxStaminaAndHitPoints()) || (getHitpoints() < getMaxStaminaAndHitPoints())){
							rest();
							System.out.println("rest");
							checker = false;
							break;
						}
						break;
					case 1:
						int x = rand.nextInt(getMaxSize());
						int y = rand.nextInt(getMaxSize());
						int z = rand.nextInt(getMaxSize());
						int [] pos = {x,y,z};
						calculatePathTo(pos);
						while ((z== 0)||(path == null)){
							x = rand.nextInt(getMaxSize());
							y = rand.nextInt(getMaxSize());
							z = rand.nextInt(getMaxSize());
							pos[0] = x;
							pos[1] = y;
							pos[2] = z;
							calculatePathTo(pos);
						}
						workAt(x, y, z);
						System.out.println("work at " + x +" "+ y +" "+ z);
						checker = false;
						break;
					case 2:
						int randomx = rand.nextInt(getMaxSize());
						int randomy = rand.nextInt(getMaxSize());
						int randomz = rand.nextInt(getMaxSize());
						int[] randompos = {randomx, randomy, randomz};
						calculatePathTo(randompos);
						while ((!world.isPassableTerrain(randompos))||(path==null)){
							randomx = rand.nextInt(getMaxSize());
							randomy = rand.nextInt(getMaxSize());
							randomz = rand.nextInt(getMaxSize());
							randompos[0] = randomx;
							randompos[1] = randomy;
							randompos[2] = randomz;
							calculatePathTo(randompos);
						}
						moveTo(randompos);
						randomSprinting(rand);
						checker = false;
						System.out.println("move to"+randompos[0]+","+randompos[1]+","+randompos[2]);
						break;
					case 3:
						for (Unit unit: world.worldMembers){
							if (unit.faction != this.faction){
								int[] cube = unit.getCubeCoordinate();
								this.defender = unit;
								int[] positionToGo = positionNearCube(cube);
								calculatePathTo(positionToGo);
								if (path == null){
									continue;
								}
								this.moveTo(positionToGo);
								randomSprinting(rand);
								this.isAttackingDefaultBehaviour = true;
								System.out.println("fight at " + positionToGo[0] + " " + positionToGo[1] + " " + positionToGo[2] );
								checker = false;
								break;
							}
						}
						break;		
				}
			}
		}
	}

	private void calculatePathTo(int[] randompos) {
		try {
			path = getWorld().pathfinding.searchPath(this.getCubeCoordinate(), randompos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void randomSprinting(Random rand) {
		int randomMove = rand.nextInt(2);
		if (randomMove == 0){
			startSprinting();
			System.out.println("start sprinten");
		}else {
			System.out.println("gewoon verder wandelen");
		}
	}
	
	public int[] positionNearCube(int[] cube) {
		Random rand = new Random();
		boolean validposition = false;
		int random = rand.nextInt(2);
		if (random == 1){
			while ((!world.isPassableTerrain(cube))&&(!isNeighbour(cube, this.defender.getCubeCoordinate()))&&(!validposition)){
				int min = -1;
				int max = 1;
				int x = rand.nextInt((max - min) + 1) + min;
				int y = rand.nextInt((max - min) + 1) + min;
				cube[0] += x;
				cube[1] += y;
				double[] doublecube = {cube[0]+0.5,cube[1]+0.5,cube[2]+0.5};
				if (isValidPosition(doublecube)){
					validposition = true;
				}else { 
					validposition = false;
					cube = defender.getCubeCoordinate();
				}
			}
		}
		
		return cube;
			
	}
	
	private boolean isAttackingDefaultBehaviour;
	/**
	 * 
	 * start defaultbehaviour
	 */
	private void stopDefaultBehaviour(){
		this.defaultBehaviour = false;
	}
	/**
	 * 
	 * returns the defaultbehaviour
	 */
	public boolean isDefaultBehaviourEnabled(){
		return this.defaultBehaviour;
	}
	/**
	 * Set the defaultbehaviour of the unit on the value.
	 * 
	 * @param  	 value
	 * 			the value of defaultbehaviour
	 *	
	 */
	public void setDefaultBehaviourEnabled(boolean value){
		this.defaultBehaviour = value;
		if (value){
			startDefaultBehaviour();
		}else{
			stopDefaultBehaviour();
		}
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	private boolean isFalling(){
		return isNeighbouringPassableTerrain(this.getCubeCoordinate());
		
	}
	
	
}