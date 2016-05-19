package hillbillies.positionExpressions;

import hillbillies.model.*;

public class PositionWorkshop<T> extends Position<T> {
	
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
	@Override
	public void execute() {
		for (int i = 0; i < getUnit().world.getX(); i++) {
			for (int j = 0; j < getUnit().world.getY(); j++) {
				for (int k = 0; k < getUnit().world.getZ(); k++) {
					if (World.TYPE_WORKSHOP == getUnit().world.getCubeType(i, j, k)){
						int[] pos = {i,j,k};
						this.position = (T)(pos);
						break;
					}
				}
			}
		}
	}

	
}
