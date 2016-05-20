package hillbillies.model;

import java.util.*;

import hillbillies.statements.Statement;

/**
 * 
 * A class of tasks.
 *
 * @author  Bart Jacobs and Jordy Heusdens
 * @version 1.0
 */
public class Task {
	
	/**
	 * Create a task with a name, priority, statement and position.
	 * 
	 * @param name
	 * 		The name of this task.
	 * @param priority
	 * 		The priority of this task.
	 * @param statement
	 * 		The statement of this task.
	 * @param pos
	 * 		The position of this task.
	 * @post Name changes to the given name.
	 * 		|new.getName() == name
	 * @post Priority changes to the given priority.
	 * 		|new.getPriority() == priority
	 * @post Statement changes to the given statement.
	 * 		|new.getStatement() == statement
	 * @post Position changes to the given position.
	 * 		|new.getPosition() == pos
	 * @post isAvailable is set to true.
	 * 		|new.isAvailable() == true
	 */
	public Task(String name, int priority, Statement statement, int[] pos){
		setName(name);
		setPriotity(priority);
		setStatement(statement);
		setPosition(pos);
		setAvailable(true);
	}
	
	private boolean isAvailable;
	
	/**
	 * Checks if this task is currently available.
	 * 
	 * @return whether the task is available.
	 * 		|result == isAvailable
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * Set isAvailable to the given boolean.
	 * 
	 * @param isAvailable
	 * 		The given boolean.
	 * @post
	 * 		|new.isAvailable() == isAvailable
	 */
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	private int[] position;
	
	/**
	 * Get the position of this task.
	 * 
	 * @return the position of this task.
	 * 		|result == position
	 */
	public int[] getPosition() {
		return position;
	}

	/**
	 * Set the position of this task to the given position.
	 * 
	 * @param position
	 * 		The given position.
	 * @post
	 * 		|new.getPosition() == position
	 */
	public void setPosition(int[] position) {
		this.position = position;
	}

	/**
	 * Get the statement of this task.
	 * 
	 * @return the statement of this task.
	 * 		|result == statement
	 */
	public Statement getStatement() {
		return statement;
	}

	/**
	 * Set the statement of this task to the given statement.
	 * 
	 * @param statement
	 * 		The given statement.
	 * @post
	 * 		|new.getStatement() == statement
	 */
	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	private Statement statement;
	
	private int priority;
	
	/**
	 * Get the priority of this task.
	 * 
	 * @return the priority of this task.
	 * 		|result == priority
	 */
	public int getPriority(){
		return this.priority;
	}
	
	private String name;
	
	/**
	 * Get the name of this task.
	 * 
	 * @return the name of this task.
	 * 		|result == name
	 */
	public String getname(){
		return this.name;
	}
	
	/**
	 * Set the name of this task to the given name.
	 * 
	 * @param name
	 * 		The given name.
	 * @post
	 * 		|new.getname() == name
	 */
	public void setName(String newname){
		this.name = newname;
	}
	
	/**
	 * Set the priority of this task to the given priority.
	 * 
	 * @param priority
	 * 		The given priority.
	 * @post
	 * 		|new.getPriority() == priority
	 */
	public void setPriotity(int n){
		this.priority = n;
	}
	
	private Unit unit;

	/**
	 * Get the unit to whom this task is assigned.
	 *  
	 * @return the unit of this task.
	 * 		|result == unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Set the unit of this task to the given unit.
	 * Assign this unit to the statement of this task.
	 * 
	 * @param unit
	 * 		The given unit.
	 * @post
	 * 		|new.getUnit() == unit
	 * 		|new.statement.getUnit() == unit
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
		statement.setUnit(unit);
	}
	
	private World world;

	/**
	 * Get the world in which this task exists.
	 *  
	 * @return the world of this task.
	 * 		|result == world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Set the world of this task to the given world.
	 * 
	 * @param world
	 * 		The given world.
	 * @post
	 * 		|new.getWorld() == world
	 */
	public void setWorld(World world) {
		this.world = world;
	}
	
	/**
	 * Create a set of schedulers in which this task is present.
	 * 
	 * @return a set of schedulers.
	 * 		|result == schedulers.contains(task)
	 */
	public Set<Scheduler> getSchedulers(){
		Set<Scheduler> schedulers = new HashSet<Scheduler>();
		for (Faction fac: world.getActiveFactions()){
			Scheduler scheduler = fac.getScheduler();
			for (Task task: scheduler.tasks){
				if (task == this){
					schedulers.add(scheduler);
				}
			}
		}
		return schedulers;
	}
	
	/**
	 * Assign a object from a task to a string and put them in a map.
	 */
	public Map<String,Object> assignment = new HashMap<String,Object>();
	
	/**
	 * List of statements that need to be executed.
	 */
	public List<Statement> sequence = new ArrayList<Statement>();
	
	
	
	
}
