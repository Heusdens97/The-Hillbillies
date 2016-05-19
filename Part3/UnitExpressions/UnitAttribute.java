package hillbillies.unitExpressions;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;

public class UnitAttribute<T> extends UnitExpression<T> {

	public UnitAttribute(Expression<?> unit){
		setExpression(unit);
	}
	
	private Expression<?> expression;
	private Boolean result;

	public void setExpression(Expression<?> unit) {
		this.expression = unit;
	}
	
	public Expression<?> getExpression(){
		return this.expression;
	}

	@Override
	public T getResult() {
		return (T) this.result;
	}
	
	public void setResult(Boolean newb){
		this.result = newb;
	}

	@Override
	public void execute() {
		getExpression().unit = unit;
		getExpression().execute();
		Unit u = (Unit) getExpression().getResult();
		if (u.isCarryingBoulder() || u.isCarryingLog()){
			setResult(true);
		} else {
			setResult(false);
		}
		
	}

}
