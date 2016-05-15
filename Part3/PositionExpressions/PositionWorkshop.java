package hillbillies.positionExpressions;

import hillbillies.model.*;

public class PositionWorkshop<T> extends Position<T> {

	public PositionWorkshop(){
		for (int i = 0; i < Faction.world.getX(); i++) {
			for (int j = 0; j < Faction.world.getY(); j++) {
				for (int k = 0; k < Faction.world.getZ(); k++) {
					if (World.TYPE_WORKSHOP == Faction.world.getCubeType(i, j, k)){
						int[] pos = {i,j,k};
						this.position = (T) IntToInteger(pos);
						break;
					}
				}
			}
		}
	}
	
	public T position;
	
	@Override
	public T getResult() {
		return this.position;
	}
	@Override
	public boolean equals(Object other) {
		return (other instanceof PositionWorkshop<?>)
				&& (getResult() == ((PositionWorkshop<T>) other).getResult());
	}

	
}
