package hillbillies.positionExpressions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hillbillies.model.*;

public class PositionWorkshop<T> extends Position<T> {
	
	public T position;
	
	@Override
	public T getResult() {
		return this.position;
	}
	
	
	@Override
	public void execute() {
		double distance = Double.POSITIVE_INFINITY;
		int[] positionWorkshop = new int[] {0,0,0};
		for (int i = 0; i < getUnit().world.getX(); i++) {
			for (int j = 0; j < getUnit().world.getY(); j++) {
				for (int k = 0; k < getUnit().world.getZ(); k++) {
					if (World.TYPE_WORKSHOP == getUnit().world.getCubeType(i, j, k)){
						int[] pos = {i,j,k};
						if (distanceOf(getUnit().getCubeCoordinate(), pos)< distance){
							distance = distanceOf(getUnit().getCubeCoordinate(), pos);
							positionWorkshop[0] = pos[0];
							positionWorkshop[1] = pos[1];
							positionWorkshop[2] = pos[2];			
						}
					}
				}
			}
		}
		this.position = (T) positionWorkshop;

	}

	
}
