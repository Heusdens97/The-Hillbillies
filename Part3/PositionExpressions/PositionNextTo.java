	package hillbillies.positionExpressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.expressions.Expression;

public class PositionNextTo<T> extends Position<T> {

	public PositionNextTo(Expression<?> position){
		setExpression(position);
	}
	
	private T nextTo;
	private Expression<?> expression;
	
	public Expression<?> getExpression() {
		return expression;
	}

	public void setExpression(Expression<?> expression) {
		this.expression = expression;
	}

	@Override
	public T getResult() {
		return this.nextTo;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof PositionNextTo<?>)
				&& (getResult() == ((PositionNextTo<T>) other).getResult());
	}

	@Override
	public void execute() {
		getExpression().unit = unit;
		getExpression().execute();
		//System.out.println(getExpression().getResult());
		int[] pos = (int[]) getExpression().getResult();
		Set<int[]> neighbors = new HashSet<int[]>();
		for (int i = -1; i<= 1; i++){
			for (int j = -1; j<= 1; j++){
				int[] poss = {pos[0]+i, pos[1]+j,pos[2]};
				if (isValidPosition(poss)&& !poss.equals(pos)&&getUnit().world.isPassableTerrain(poss)){
					neighbors.add(poss);
				}
			}
		}
		int[] neigh =  (int[]) get(neighbors);
		this.nextTo = (T) neigh;
	}
	

}
