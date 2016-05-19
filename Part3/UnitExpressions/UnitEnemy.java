package hillbillies.unitExpressions;

import hillbillies.expressions.Expression;
import hillbillies.model.Faction;
import hillbillies.model.Unit;

public class UnitEnemy<T> extends UnitExpression<T> {
	
	private T unitEnemy;
	
	@Override
	public T getResult() {
		return this.unitEnemy;
	}

	@Override
	public void execute() {
		double distance = Double.POSITIVE_INFINITY;
		Unit min = null;
		for (Unit unit: getUnit().getWorld().worldMembers){
			if (distanceOf(getUnit().getCubeCoordinate(), unit.getCubeCoordinate())< distance && !unit.getFaction().equals(getUnit().getFaction())){
				distance = distanceOf(getUnit().getCubeCoordinate(),unit.getCubeCoordinate());
				min = unit;
			}
		}
		this.unitEnemy = (T) min;
	}
}
