package hillbillies.statements;

import hillbillies.expressions.Expression;

public class StatementWhile extends Statement {

	public StatementWhile(Expression<?> condition, Statement body) {
		setBody(body);
		setCondition(condition);
	}
	
	private Expression<?> condition;
	private Statement body;
	
	public Expression<?> getCondition() {
		return condition;
	}
	public void setCondition(Expression<?> condition) {
		this.condition = condition;
	}
	public Statement getBody() {
		return body;
	}
	public void setBody(Statement body) {
		this.body = body;
	}
	@Override
	public void execute() {
		getCondition().unit = unit;
		getBody().unit = unit;
		getCondition().execute();
		getBody().execute();
		getUnit().getTask().sequence.add(this);	
	}
	
}
