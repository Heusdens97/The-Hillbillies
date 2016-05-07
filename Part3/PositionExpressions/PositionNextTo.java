package hillbillies.positionExpressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.expressions.Expression;

public class PositionNextTo extends Position {

	public PositionNextTo(Expression position){
		Integer[] pos = (Integer[]) position.getResult();
		Set<int[]> neighbors = new HashSet<int[]>();
		for (int i = -1; i<= 1; i++){
			for (int j = -1; j<= 1; j++){
				int[] poss = {pos[0]+i, pos[1]+j,pos[2]};
				if (isValidPosition(poss)){
					neighbors.add(poss);
				}
			}
		}
		this.nextTo = get(neighbors);
	}
	
	public Integer[] nextTo;
	
	@Override
	public Integer[] getResult() {
		return this.nextTo;
	}
	

}
