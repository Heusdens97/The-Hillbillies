package hillbillies.unitExpressions;

public class UnitAny<T> extends UnitExpression<T> {
	
	@Override
	public T getResult() {
		return this.unitAny;
	}
	
	private T unitAny;
	
	public void setUnit(T unit){
		this.unitAny = unit;
	}

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void execute() {
		setUnit((T)get(getUnit().world.worldMembers));
	}
}
