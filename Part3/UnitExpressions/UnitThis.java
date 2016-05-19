package hillbillies.unitExpressions;

import hillbillies.model.Faction;
import hillbillies.model.Unit;

public class UnitThis<T> extends UnitExpression<T> {

	@Override
	public T getResult() {
	     return (T) unitThis;
	}
	
	private Unit unitThis;

	public void setUnit(Unit unit) {
		this.unitThis = unit;
	}

	@Override
	public void execute() {
		setUnit(getUnit());
	}
}
