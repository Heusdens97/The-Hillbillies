package hillbillies.positionExpressions;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;

public class PositionOf<T> extends Position<T> {

	public PositionOf(Expression<?> unit){
		setExpression(unit);
	}
	
	private Expression<?> expression;
	
	public Expression<?> getExpression() {
		return expression;
	}

	public void setExpression(Expression<?> expression) {
		this.expression = expression;
	}

	public void setPosition(T position) {
		this.position = position;
	}

	public T position;
	
	@Override
	public T getResult() {
		return this.position;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof PositionOf<?>)
				&& (getResult() == ((PositionOf<T>) other).getResult());
	}

	@Override
	public void execute() {
		Unit unitPos = getUnit();
		setPosition((T)unitPos.getPosition());
	}
}
