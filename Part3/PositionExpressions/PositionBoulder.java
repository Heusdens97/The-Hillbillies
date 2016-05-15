package hillbillies.positionExpressions;


import hillbillies.model.*;
import ogp.framework.util.ModelException;

public class PositionBoulder<T> extends Position<T> {

	public PositionBoulder(){
		if (Faction.world.boulders.isEmpty())
			try {
				throw new ModelException();
			} catch (ModelException e) {
				System.out.println("no boulder available");
				e.printStackTrace();
			}
		else{
//			for (Boulder boulder: Faction.world.boulders){
//				int[] oldArray = boulder.getCubeCoordinate();
//				Integer[] newArray = new Integer[oldArray.length];
//				int i = 0;
//				for (int value : oldArray) {
//				    newArray[i++] = Integer.valueOf(value);
//				}
//				this.boulder = newArray;
//				break;
//			}
			this.boulder = (T) get(Faction.world.boulders);
		}
	}
	
	public T boulder;
	
	@Override
	public T getResult() {
		return this.boulder;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof PositionBoulder<?>)
				&& (getResult() == ((PositionBoulder<T>) other).getResult());
	}
	

}
