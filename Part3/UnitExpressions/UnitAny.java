package hillbillies.unitExpressions;

import hillbillies.model.Unit;

public class UnitAny<T> extends UnitExpression<T> {
	
	@Override
	public T getResult() {
		return this.unitAny;
	}
	
	private T unitAny;
	
	public void setAnyUnit(T unit){
		this.unitAny = unit;
	}
	
	@Override
	public void execute() {
		Unit min = null;
		double distance = Double.POSITIVE_INFINITY;
		for (Unit unit: getUnit().getWorld().worldMembers){
			if (distanceOf(getUnit().getCubeCoordinate(), unit.getCubeCoordinate())< distance){
				distance = distanceOf(getUnit().getCubeCoordinate(),unit.getCubeCoordinate());
				min = unit;
			}
		}
		setAnyUnit((T)min);
	}
}
