package hillbillies.statements;

import java.util.Arrays;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;

public class StatementWithUnit extends Statement {

	public StatementWithUnit(Expression<?> unitToFollow) {
		setfollowUnit((Unit) unitToFollow.getResult());
		setExpression(unitToFollow);
	}
	
	public Unit followUnit;
	
	public void setfollowUnit(Unit newunit){
		this.followUnit = newunit;
	}
	
	public Expression<?> expression;
	
	public Expression<?> getExpression() {
		return expression;
	}

	public void setExpression(Expression<?> expression) {
		this.expression = expression;
	}

	public void execute() {
		getExpression().unit = unit;
		getExpression().execute();
		setfollowUnit((Unit) getExpression().getResult());
		int[] posToGo = followUnit.getCubeCoordinate();
		while (!Arrays.equals(getUnit().getCubeCoordinate(), followUnit.getCubeCoordinate())){
			getUnit().moveTo(posToGo);
			posToGo = followUnit.getCubeCoordinate();
		}
	}
	
	
}
