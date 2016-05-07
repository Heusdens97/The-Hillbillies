package hillbillies.positionExpressions;

import hillbillies.model.*;
import ogp.framework.util.ModelException;

public class PositionBoulder extends Position {

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
			this.boulder = get(Faction.world.boulders);
		}
	}
	
	public Integer[] boulder;
	
	@Override
	public Integer[] getResult() {
		return this.boulder;
	}
	

}
