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
	public void execute() {
		//niet nodig
	}
}
