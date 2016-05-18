package hillbillies.statements;

import hillbillies.expressions.Expression;

public class StatementWithPosition extends Statement {

	public StatementWithPosition(Expression<?> position) {
		setExpression(position);
		setPosition((int[]) position.getResult());
	}
	
	
	public Expression<?> expression;
	
	public Expression<?> getExpression() {
		return expression;
	}

	public void setExpression(Expression<?> expression) {
		this.expression = expression;
	}


	public int[] pos;
	
	public void setPosition(int[] newposition){
		this.pos = newposition;
	}
	
	public void execute(){
		getExpression().unit = unit;
		getExpression().execute();
		setPosition((int[]) getExpression().getResult());
		getUnit().workAt(pos[0], pos[1], pos[2]);
	}
}
