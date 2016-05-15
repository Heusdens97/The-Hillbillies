package hillbillies.positionExpressions;

import hillbillies.model.*;

public class PositionHere<T> extends Position<T> {

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
					this.here = (T) newArray;
					break;
				}
			}
		}
	}
	
	public T here;
	
	@Override
	public T getResult() {
		return this.here;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof PositionHere<?>)
				&& (getResult() == ((PositionHere<T>) other).getResult());
	}
	
	



}
