package hillbillies.unitExpressions;

import java.util.Set;

import hillbillies.expressions.Expression;
import hillbillies.model.Objects;
import hillbillies.model.Unit;

public abstract class UnitExpression<T> extends Expression<T> {

	public double distanceOf(int[] pos1, int[] pos2){
		return Math.sqrt(Math.pow((pos2[0]-pos1[0]),2)+Math.pow((pos2[1]-pos1[1]),2)+Math.pow((pos2[2]-pos1[2]),2));
	}
}
