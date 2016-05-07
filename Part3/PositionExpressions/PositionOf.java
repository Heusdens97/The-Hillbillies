package hillbillies.positionExpressions;

import hillbillies.expressions.Expression;

public class PositionOf extends Position {

	public PositionOf(Expression position){
		this.position = (Integer[]) position.getResult();
	}
	
	public Integer[] position;
	
	@Override
	public Integer[] getResult() {
		return this.position;
	}

}
