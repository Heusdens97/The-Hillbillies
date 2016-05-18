package hillbillies.statements;

import javax.sound.midi.Sequence;

import hillbillies.expressions.Expression;

public class StatementIf extends Statement {
	public StatementIf(Expression<?> condition, Statement ifBody, Statement elseBody) {
		setCondition(condition);
		setIfbody(ifBody);
		setElsebody(elseBody);
	}
	
	private Expression<?> condition;
	private Statement ifbody;
	private Statement elsebody;
	
	public Expression<?> getCondition() {
		return condition;
	}

	public void setCondition(Expression<?> condition) {
		this.condition = condition;
	}

	public Statement getIfbody() {
		return ifbody;
	}

	public void setIfbody(Statement ifbody) {
		this.ifbody = ifbody;
	}

	public Statement getElsebody() {
		return elsebody;
	}

	public void setElsebody(Statement elsebody) {
		this.elsebody = elsebody;
	}
	
	@Override
	public void execute() {
		getCondition().unit = unit;
		getCondition().execute();
		if (condition.getResult().equals(true)){
			getIfbody().unit = unit;
			getUnit().getTask().sequence.add(getIfbody());
		} else {
			if (getElsebody() != null){
				getElsebody().unit = unit;
				getUnit().getTask().sequence.add(getElsebody());
			}
		}
		
	}
	
	
}
