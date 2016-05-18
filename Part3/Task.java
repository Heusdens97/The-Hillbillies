package hillbillies.model;

import java.util.*;

import hillbillies.expressions.Expression;
import hillbillies.statements.Statement;

public class Task {
	
	public Task(String name, int priority, Statement statement, int[] pos){
		setName(name);
		setPriotity(priority);
		setStatement(statement);
		setPosition(pos);
		setAvailable(true);
	}
	
	private boolean isAvailable;
	
	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	private int[] position;
	
	public int[] getPosition() {
		return position;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	private Statement statement;
	
	private int priority;
	
	public int getPriority(){
		return this.priority;
	}
	
	private String name;
	
	public String getname(){
		return this.name;
	}
	
	public void setName(String newname){
		this.name = newname;
	}
	
	public void setPriotity(int n){
		this.priority = n;
	}
	
	private Unit unit;

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
		statement.setUnit(unit);
	}
	
	private World world;

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public Set<Scheduler> getSchedulers(){
		Set<Scheduler> schedulers = new HashSet<Scheduler>();
		for (Faction fac: world.activeFactions){
			Scheduler scheduler = fac.getScheduler();
			for (Task task: scheduler.tasks){
				if (task == this){
					schedulers.add(scheduler);
				}
			}
		}
		return schedulers;
	}
	
	public Map<String,Expression<?>> assignment = new HashMap<String,Expression<?>>();
	public List<Statement> sequence = new ArrayList<Statement>();
	private boolean complete;

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	
	
}
