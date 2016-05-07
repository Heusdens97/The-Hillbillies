package hillbillies.positionExpressions;

import hillbillies.model.*;

public class PositionHere extends Position {

	public PositionHere(){
		for(Unit unit: Faction.world.getUnits()){
			if (unit.getTask() != null){
				if (!unit.getTask().isAvailable()){
					int[] oldArray = unit.getCubeCoordinate();
					Integer[] newArray = new Integer[oldArray.length];
					int i = 0;
					for (int value : oldArray) {
					    newArray[i++] = Integer.valueOf(value);
					}
					this.here = newArray;
					break;
				}
			}
		}
	}
	
	public Integer[] here;
	
	@Override
	public Integer[] getResult() {
		return this.here;
	}
	
	



}
