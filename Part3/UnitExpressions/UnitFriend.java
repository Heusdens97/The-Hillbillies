package hillbillies.unitExpressions;

import hillbillies.expressions.Expression;
import hillbillies.model.*;
import ogp.framework.util.ModelException;

public class UnitFriend<T> extends UnitExpression<T> {
	
	public T friend;
	
	@Override
	public T getResult() {
		return this.friend;
	}


	@Override
	public void execute() {
		if (getUnit().world.worldMembers.isEmpty()){
			try {
				throw new ModelException();
			} catch (ModelException e) {
				System.out.println("no units available");
				e.printStackTrace();
			}
		}
		else{
			Unit unit = getUnit();
			Faction faction = unit.getFaction();
			double distance = Double.POSITIVE_INFINITY;
			Unit min = null;
			if (faction.members.size() > 1){
				for (Unit u: faction.members){
					if (u != unit && distanceOf(getUnit().getCubeCoordinate(), u.getCubeCoordinate())< distance){
						distance = distanceOf(getUnit().getCubeCoordinate(), u.getCubeCoordinate());
						min = u;
					}
				}
			} else {
				try {
					throw new ModelException();
				} catch (ModelException e) {
					System.out.println("only one member in his faction, so he is his own friend");
					e.printStackTrace();
				}
			}
			this.friend = (T) unit;
		}
		
	}
}
