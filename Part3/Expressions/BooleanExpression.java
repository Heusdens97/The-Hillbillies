package hillbillies.expressions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class BooleanExpression<T> extends Expression<T> {

	public BooleanExpression(T bool){
		this.b = bool;
	}
	
	private T b;
	
	@Override
	@Basic @Immutable
	public T getResult() {
		return b;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof BooleanExpression<?>)
				&& (getResult() == ((BooleanExpression<T>) other).getResult());
	}

	@Override
	public void execute() {
		//niet nodig
	}
}
