package hillbillies.positionExpressions;

import hillbillies.model.*;

public class PositionWorkshop extends Position {

	public PositionWorkshop(){
		for (int i = 0; i < Faction.world.getX(); i++) {
			for (int j = 0; j < Faction.world.getY(); j++) {
				for (int k = 0; k < Faction.world.getZ(); k++) {
					if (World.TYPE_WORKSHOP == Faction.world.getCubeType(i, j, k)){
						int[] pos = {i,j,k};
						this.position = IntToInteger(pos);
						break;
					}
				}
			}
		}
	}
	
	public Integer[] position;
	
	@Override
	public Integer[] getResult() {
		return this.position;
	}

	
}
