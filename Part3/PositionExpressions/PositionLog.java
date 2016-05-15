package hillbillies.positionExpressions;

import hillbillies.model.*;
import ogp.framework.util.ModelException;

public class PositionLog<T> extends Position<T> {

	public PositionLog(){
		if (Faction.world.logs.isEmpty())
			try {
				throw new ModelException();
			} catch (ModelException e) {
				System.out.println("no log available");
				e.printStackTrace();
			}
		else{
			for (Log log : Faction.world.logs){
				int[] oldArray = log.getCubeCoordinate();
				Integer[] newArray = new Integer[oldArray.length];
				int i = 0;
				for (int value : oldArray) {
				    newArray[i++] = Integer.valueOf(value);
				}
				this.log = newArray;
				break;
			}
		}
	}
	
	public Integer[] log;
	
	@Override
	public Integer[] getResult() {
		return this.log;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof PositionLog<?>)
				&& (getResult() == ((PositionLog<T>) other).getResult());
	}
	

}
