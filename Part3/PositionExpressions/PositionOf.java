package hillbillies.positionExpressions;

import hillbillies.expressions.Expression;

public class PositionOf<T> extends Position<T> {

	public PositionOf(Expression<?> unit){
		this.position = (T) unit.getResult();
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

}
