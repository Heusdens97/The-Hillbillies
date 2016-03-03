package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import ogp.framework.util.ModelException;
import java.util.*;

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
	 *       	| new.getstamina() == GetMaxStaminaAndHitPoints() (=200*(weight/100)*(toughness/100))
	 * @post   	The orientation is equal to math.pi/2
	 * 			| new.getorientation() == math.pi/2
	 * @throws	ModelException
	 * 			Throws an exception if it isnt't valid
	 * 			| ! canHaveAsPosition(initialposition)
	 */
	public Unit(String name, int[] initialposition, int weight, int agility, int strength, int toughness, boolean enableDefaultBehavior) throws ModelException{
		this.setName(name);
		double[] position = {initialposition[0]+0.5,initialposition[1]+0.5,initialposition[2]+0.5};
		if (! canHaveAsPosition(position))
			throw new ModelException();
		this.position = position;
		setWeight(weight, LOWER, UPPER);
		setAgility(agility, LOWER, UPPER);
		setStrength(strength, LOWER, UPPER);
		setToughness(toughness, LOWER, UPPER);
		this.setStamina(this.getMaxStaminaAndHitPoints());
		this.setHitpoints(this.getMaxStaminaAndHitPoints());
		setOrientation(Math.PI/2);
		this.timetillrest = this.initial_timetillrest;
		setDefaultBehaviourEnabled(enableDefaultBehavior);
		
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
	 * @return 	true if the stamina is valid: stamina is between GetMaxStaminaAndHitPoints() and 0 or equal his bounds
	 *       	| if (GetMaxStaminaAndHitPoints()) >= stamina >= 0)
	 *       	| 	then result == true
	 *       	| 	else result == false
	*/
	public boolean isValidStamina(int stamina) {
		return (stamina >= 0 && stamina <= (this.getMaxStaminaAndHitPoints()));
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
	 * @return 	true if the hitpoints is valid: hitpoints is between GetMaxStaminaAndHitPoints() and 0 or equal his bounds
	 *       	| if (GetMaxStaminaAndHitPoints()) >= hitpoints >= 0)
	 *       	| 	then result == true
	 *       	| 	else result == false
	*/
	public boolean isValidHitpoints(int hitpoints) {
		return (hitpoints >= 0 && hitpoints <= (this.getMaxStaminaAndHitPoints()));
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
	private double[] destiny;
	
	private double speed;
	
	public void advanceTime(double dt) throws ModelException{
		if (!isValidTime(dt))
			throw new ModelException();
		else {
			timetillrest -= dt;
			if (timetillrest <= 0){
				rest();
			}
			if (isResting()){
				if (this.getHitpoints() != this.getMaxStaminaAndHitPoints()){
					this.setHitpoints(this.getHitpoints() + 1); //niet juist
				}
				else if(this.getStamina() != this.getMaxStaminaAndHitPoints()){
					this.setStamina(this.getStamina() + 1); // niet juist
				}
				else{
					this.resting = false;
				}
			}
			if (isResting() && isAttacking()){
				this.resting = false;
			}
			if (this.getStamina() == 0 && isSprinting())
				stopSprinting();
			if (isWorking())
				this.worktime = this.worktime - dt;
			if (isMoving()){
				//efficienter!!
				//setSpeed();
				double d = Math.sqrt(Math.pow((this.getDestiny()[0]-this.getPosition()[0]),2)+Math.pow((this.getDestiny()[1]-this.getPosition()[1]),2)+Math.pow((this.getDestiny()[2]-this.getPosition()[2]),2));
				double[] v = {this.getSpeed()*((this.getDestiny()[0]-this.getPosition()[0])/(double)d),this.getSpeed()*((this.getDestiny()[1]-this.getPosition()[1])/(double)d),this.getSpeed()*((this.getDestiny()[2]-this.getPosition()[2])/(double)d)};
				double[] New = {this.getPosition()[0] + v[0]*dt,this.getPosition()[1] + v[1]*dt,this.getPosition()[2] + v[2]*dt};
				this.position = New;
				this.orientation = Math.atan2(v[1],v[0]);
			}
		}
	}
	
	public boolean isMoving(){
//		double []now = this.getPosition();
//		advanceTime(t);
//		if (now == this.getPosition())
//				return false;
//		return true;
		return this.moving;
	}
	
	public boolean isValidTime(double dt){
		return ((dt >= 0) && (dt < 0.2));
	}
	
	private boolean moving;
	
	public void startMoving(){
		this.moving = true;
	}
	
	public void stopMoving(){
		this.moving = false;
	}
	
	public void setSpeed(){
	    double speed = 1.5*((this.getStrength()+this.getAgility())/(double)(200*(this.getWeight()/(double)100)));
		if ((isMoving()) && (this.getDestiny() != null)){
			if ((int)(this.getPosition()[2]- this.getDestiny()[2]) == -1)
				speed = (1/(double)2)*speed;
			else if ((int)(this.getPosition()[2]-this.getDestiny()[2]) == 1)
				speed = (1.2)*speed;
			if (isSprinting())
				startSprinting();
			//else
			//speed = speed
		}
		this.speed = speed;
	}
	
	public double getSpeed(){
		setSpeed();
		return this.speed;
	}
	
	public boolean isSprinting(){
		return this.sprinting;
	}
	
	public double[] getDestiny(){
		return this.destiny;
	}
	
	public void setDestiny(double[] position){
		if (canHaveAsPosition(position))
			this.destiny = position;
	}
	
	public void startSprinting(){
		if ((isMoving())&& (this.getStamina() > 0))
			this.speed = 2*1.5*((this.getStrength()+this.getAgility())/(double)(200*(this.getWeight()/(double)100)));;
			this.sprinting = true;
	}
	
	public void stopSprinting(){
		this.sprinting = false;
		setSpeed();
	}
	
	public void moveToAdjacent(int dx, int dy, int dz) throws ModelException{
		double[] Adjacent = {this.getPosition()[0]+dx,this.getPosition()[1]+dy,this.getPosition()[2]+dz};
		setDestiny(Adjacent);
		startMoving();
		if (canHaveAsPosition(Adjacent)){
			while ((this.getPosition()[0] != Adjacent[0])||(this.getPosition()[1] != Adjacent[1])||(this.getPosition()[2] != Adjacent[2])){
				advanceTime(t);
				if ((this.getPosition()[0]>= Adjacent[0])&&(this.getPosition()[1]>= Adjacent[1])&&(this.getPosition()[2]>= Adjacent[2])){
					this.position = Adjacent; //waarom haakjes?
				}
			}
		}
	}
	
	private double t = 0.1;
	
	public void moveTo(int[] cube) throws ModelException{
		double[] Position = {cube[0]+0.5,cube[1]+0.5,cube[2]+0.5};
		//setDestiny(position);
		int dx,dy,dz;
		while ((this.getPosition()[0] != Position[0])&&(this.getPosition()[1] != Position[1])&&(this.getPosition()[2] != Position[2])){
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
			if (this.getPosition()[2] == Position[0])
				dz = 0;
			else if (this.getPosition()[2] < Position[0])
				dz = 1;
			else
				dz = -1;
			moveToAdjacent(dx, dy, dz);
		}	
		this.position = Position;
	}
	
	private boolean working;
	
	public boolean isWorking(){
		return this.working;
	}
	public void work() throws ModelException{
		this.worktime = 500 / (double)this.getStrength();
		while(this.worktime > 0){
			this.working = true;
			advanceTime(t);
			if (this.interrupt)
				break;
		}
		this.working = false;
		this.interrupt = false;
	}
	
	private double worktime;
	
	private boolean interrupt;
	
	public int getMaxStaminaAndHitPoints(){
		return (int)(200*(this.getWeight()/(double)100)*(this.getToughness()/(double)100));
	}
	
	public int[] getCubeCoordinate() throws ModelException{
		return new int[] {(int) this.getPosition()[0],(int) this.getPosition()[1],(int) this.getPosition()[2]};
	}
	public void fight(Unit attacker, Unit defender) throws ModelException{
		attack(defender);
		defend(attacker);
		
	}
	public void attack(Unit defender) throws ModelException{ //controleren getposition = defenderposition, java doet raar
		if ((this.getPosition() == defender.getPosition())||(Neighbouring(defender))){
			this.attacking = true;
		}
			
	}
	
	public void defend(Unit attacker) throws ModelException{
		if ((this.getPosition() == attacker.getPosition())||(Neighbouring(attacker))){
			
		}
			
	}
	
	public boolean Neighbouring(Unit other){
		double[] me = {this.getPosition()[0], this.getPosition()[1],this.getPosition()[2]};
		double[] different = {other.getPosition()[0], other.getPosition()[1],other.getPosition()[2]};
		if ((((int)(me[0]-different[0]) == 1)||(((int)me[0]-different[0]) == -1))&&((((int)me[1]-different[1]) == -1)||(((int)me[1]-different[1]) == -1))&&((((int)me[2]-different[2]) == -1)||(((int)me[2]-different[2]) == -1)))
			return true;	
		return false;
	}
	
	private double initial_timetillrest = 180;
	private double timetillrest;
	
	public void rest() throws ModelException{
		this.resting = true;
		this.timetillrest = 180;
		while (isResting()){
			advanceTime(t);
		}
		
	}
	private boolean resting;
	public boolean isResting(){
		return this.resting;	
	}
	
	public boolean isAttacking(){
		return this.attacking;
	}
	
	public boolean attacking;
	
	public void startDefaultBehaviour(){
		this.defaultBehaviour = true;
	}
	public void stopDefaultBehaviour(){
		this.defaultBehaviour = false;
	}
	
	public boolean isDefaultBehaviourEnabled(){
		return this.defaultBehaviour;
	}

	public void setDefaultBehaviourEnabled(boolean value) throws ModelException{
		this.defaultBehaviour = value;
		if (value){
			Random rand = new Random();
			int randomNumber = rand.nextInt(3);
			switch (randomNumber){
				case 0:
					rest();
					break;
				case 1:
					work();
					break;
				case 2:
					int randomx = rand.nextInt(getMaxSize() - 1);
				    int randomy = rand.nextInt(getMaxSize() - 1);
				    int randomz = rand.nextInt(getMaxSize() - 1);
				    int[] randompos = {randomx, randomy, randomz};
				    moveTo(randompos);
				    break;
			}
		}
	}
	
	
}
