package hillbillies.model;

public class Task {
	
	public Task(String name, int priority, MyStatement statement, int[] pos){
		setName(name);
		setPriotity(priority);
		setStatement(statement);
		setPosition(pos);
	}
	
	private int[] position;
	
	public int[] getPosition() {
		return position;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public MyStatement getStatement() {
		return statement;
	}

	public void setStatement(MyStatement statement) {
		this.statement = statement;
	}

	private MyStatement statement;
	
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
}
