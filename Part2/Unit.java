package hillbillies.model;


import be.kuleuven.cs.som.annotate.*;
import ogp.framework.util.*;
import hillbillies.model.World;
import hillbillies.model.Faction;
import java.math.*;
import java.util.*;


/**
 * TO DO: 	- opdelen in classes?
 * 			- documentatie aanvullen
 * 			- tests schrijven
 * 			- "spaghetti" code verwijderen
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
	public Unit(String name, int[] initialposition, int weight, int agility, int strength, int toughness, boolean enableDefaultBehavior) throws ModelException{
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
	}
	
	private void setExperiencePoints(int experience){
		this.experience = experience;
	}
	
	private int experience;
	
	public int getExperiencePoints(){
		return this.experience;
	}
	
	public Faction faction;
	public static World world;

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
	
	
	public void advanceTime(double dt) throws ModelException{
		if (!isValidTime(dt)){
			throw new ModelException();
		} else {
			this.timetillrest -= dt;
			if (this.timetillrest <= 0){
				rest();
			}
			advanceTime_Fight(dt);
			if ((isResting())&&(!isWorking())){
				if (this.getHitpoints() != this.getMaxStaminaAndHitPoints()){
					this.hitpoints_double = this.hitpoints_double + dt * (this.getToughness()/((double)(200)*0.2));
					if (this.hitpoints_double > this.getMaxStaminaAndHitPoints())
						this.setHitpoints(this.getMaxStaminaAndHitPoints());
					this.setHitpoints((int)(this.hitpoints_double));
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
			if (isDefaultBehaviourEnabled()&&(!isMoving())&&(!isWorking())&&(!isAttackingDefaultBehaviour)&&(!isAttacking())&&((int)this.getPosition()[0]==this.finaldest[0])&&((int)this.getPosition()[1]==this.finaldest[1])&&((int)this.getPosition()[2]==this.finaldest[2])&&(!isAttacking()))
				setDefaultBehaviourEnabled(true);
			if (isWorking()){
				work();
				this.worktime -= dt;
			}
			advanceTime_levelUp();
		}
	}

	private void advanceTime_Moving(double dt) throws ModelException {
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
			if ((round(this.getPosition()[0],1) == this.getDestiny()[0])&&(round(this.getPosition()[1],1) == this.getDestiny()[1])&&(round(this.getPosition()[2],1) == this.getDestiny()[2])){
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
	private void advanceTime_Fight(double dt) throws ModelException {
		if ((defender != null)&&(this.fighttime == 1)){
			if ((defender.isAlive())&&(isAttackingDefaultBehaviour)){
				this.fight(defender);
			}
			if (!defender.isAlive()){
				isAttackingDefaultBehaviour = false;
				this.defender = null;
			}
			if ((defender != null)&&(defender!=this)&&(!this.isMoving())){
				if ((!isNeighbour(this.getCubeCoordinate(),defender.getCubeCoordinate()))&&(!Arrays.equals(this.getPosition(),defender.getPosition()))&&((int)this.getPosition()[0]==this.finaldest[0])&&((int)this.getPosition()[1]==this.finaldest[1])&&((int)this.getPosition()[2]==this.finaldest[2])){
					moveTo(defender.getCubeCoordinate());
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
	public boolean isValidTime(double dt) throws ModelException{
		return ((dt >= 0) && (dt <= 0.2));
			
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
	public void moveToAdjacent(int dx, int dy, int dz) throws ModelException{
		if (!isMoving()){
			double[] Adjacent = {this.getPosition()[0]+dx,this.getPosition()[1]+dy,this.getPosition()[2]+dz};
			setDestiny(Adjacent);
			if (isValidPosition(Adjacent)){
				this.attacking = false;
				startMoving();
				setSpeed();
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
	
	/**
	 * 
	 * The unit moves to the cube.
	 * 
	 * @param 	cube
	 * 			the new position of the unit.
	 * @throws 	ModelException
	 * 			if it's not a valid position.
	 */
	public void moveTo(int[] cube) throws ModelException{
		double[] Position = {0,0,0};
		Position[0] = (double)(cube[0])+0.5;
		Position[1] = (double)(cube[1])+0.5;
		Position[2] = (double)(cube[2])+0.5;
		if (isValidPosition(Position)){
			this.working = false;
			this.arrowKeys = false;
			this.finaldest = cube;
			int dx,dy,dz;
			if ((this.getPosition()[0] != Position[0])||(this.getPosition()[1] != Position[1])||(this.getPosition()[2] != Position[2])){
				if (this.getPosition()[0] == Position[0])
					dx = 0;
				else if (this.getPosition()[0] < Position[0])
					dx = 1;
				else
					dx = -1;
				if (this.getPosition()[1] == Position[1])
					dy = 0;
				else if (this.getPosition()[1] < Position[1])
					dy = 1;
				else
					dy = -1;
				if (this.getPosition()[2] == Position[2])
					dz = 0;
				else if (this.getPosition()[2] < Position[2])
					dz = 1;
				else
					dz = -1;
				moveToAdjacent(dx, dy, dz);
			}	
		}
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
	/**
	 * 
	 * the unit starts working.
	 */
	public void work() throws ModelException{ 
		this.resting = false;
		this.sprinting = false;//wegdoen om hem te laten onthouden om te rusten
		if (!isWorking()){
			this.worktime = (500/(double)this.getStrength());
			this.working = true;
		}else{
			if (this.worktime <=0){
				this.working = false;
				setExperiencePoints(this.getExperiencePoints()+workExperience);
			}
		}
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
	public int[] getCubeCoordinate() throws ModelException{
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
	public void fight(Unit defender) throws ModelException{ 
		this.working = false;
		if (!isAttacking())
			this.fighttime = 1;
		if ((!isMoving())&&(defender != null)&&(defender != this)&&(this.faction != defender.faction)&&((Arrays.equals(this.getPosition(),defender.getPosition()))||(isNeighbour(this.getCubeCoordinate(),defender.getCubeCoordinate())))){
			attack(this,defender); 
			defend(defender,this);
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
	private void attack(Unit attacker,Unit defender) throws ModelException{
		attacker.attacking = true;	
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
	private void defend(Unit defender,Unit attacker) throws ModelException{
		defender.attacking = false;
		defender.defender = defender;
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
	private void dodge(Unit defender,Unit attacker) throws ModelException{
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
		if (((Math.abs((int)me[0]-(int)other[0]) == 1)||((Math.abs((int)me[0]-(int)other[0]) == 0))&&((Math.abs((int)me[1]-(int)other[1]) == 1)||((Math.abs((int)me[1]-(int)other[1]) == 0)))&&((Math.abs((int)me[1]-(int)other[1]) != 0)||(Math.abs((int)me[1]-(int)other[1]) != 0))))
			return true;
		return false;
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
	public void rest() throws ModelException{
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
	private void startDefaultBehaviour() throws ModelException{
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
						work();
						System.out.println("work");
						checker = false;
						break;
					case 2:
						int randomx = rand.nextInt(getMaxSize());
						int randomy = rand.nextInt(getMaxSize());
						int randomz = rand.nextInt(getMaxSize());
						int[] randompos = {randomx, randomy, randomz};
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

	private void randomSprinting(Random rand) {
		int randomMove = rand.nextInt(2);
		if (randomMove == 0){
			startSprinting();
			System.out.println("start sprinten");
		}else {
			System.out.println("gewoon verder wandelen");
		}
	}
	
	public int[] positionNearCube(int[] cube) throws ModelException{
		Random rand = new Random();
		boolean validposition = true;
		int random = rand.nextInt(2);
		if (random == 1){
			while (((world.getCubeType(cube[0], cube[1], cube[2])!=World.TYPE_AIR)&&(world.getCubeType(cube[0], cube[1], cube[2])!=World.TYPE_WORKSHOP))&&(!isNeighbour(cube, this.defender.getCubeCoordinate()))&&(validposition)){
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
					break;
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
	public void setDefaultBehaviourEnabled(boolean value) throws ModelException{
		this.defaultBehaviour = value;
		if (value){
			startDefaultBehaviour();
		}else{
			stopDefaultBehaviour();
		}
	}
	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
