package hillbillies.unitExpressions;

import hillbillies.model.Faction;

public class UnitAny<T> extends UnitExpression<T> {

	public UnitAny(){
		this.unit = (T) get(Faction.world.worldMembers);
	}
	@Override
	public T getResult() {
		return this.unit;
	}
	
	private T unit;

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return false;
	}
}
