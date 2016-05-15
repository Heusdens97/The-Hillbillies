package hillbillies.unitExpressions;

import hillbillies.expressions.Expression;
import hillbillies.model.Faction;
import hillbillies.model.Unit;

public class UnitEnemy<T> extends UnitExpression<T> {
	
	public UnitEnemy(){
		Faction fac = (Faction) get(Faction.world.activeFactions);
		Expression<T> unitthis = new UnitThis<T>();
		Unit unit = (Unit) unitthis.getResult();
		while (fac.equals(unit.getFaction())){
			fac = (Faction) get(Faction.world.activeFactions);
		}
		this.unit = (T) get(fac.getUnitsOfFaction());
	}
	
	private T unit;
	
	@Override
	public T getResult() {
		return this.unit;
	}

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return false;
	}
}
