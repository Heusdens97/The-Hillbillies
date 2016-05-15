package hillbillies.unitExpressions;

import java.util.Set;

import hillbillies.expressions.Expression;
import hillbillies.model.Objects;
import hillbillies.model.Unit;

public abstract class UnitExpression<T> extends Expression<T> {
	
	public Object get(Set<?> set){
		for (Object unit: set){
			return unit;
		}
		return null;
	}

}
