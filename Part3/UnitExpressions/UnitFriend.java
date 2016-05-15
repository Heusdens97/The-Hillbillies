package hillbillies.unitExpressions;

import hillbillies.expressions.Expression;
import hillbillies.model.*;
import ogp.framework.util.ModelException;

public class UnitFriend<T> extends UnitExpression<T> {
	
	public UnitFriend(){
		if (Faction.world.worldMembers.isEmpty()){
			try {
				throw new ModelException();
			} catch (ModelException e) {
				System.out.println("no Ts available");
				e.printStackTrace();
			}
		}
		else{
			Expression<T> unitthis = new UnitThis<T>();
			Unit unit = (Unit) unitthis.getResult();
			Faction faction = unit.getFaction();
			if (faction.members.size() > 1){
				for (Unit u: faction.members){
					if (u != unit){
						this.friend = (T) u;
						break;
					}
				}
			} else {
				try {
					throw new ModelException();
				} catch (ModelException e) {
					System.out.println("he is his own friend");
					e.printStackTrace();
				}
			}
		}
	}
	
	public T friend;
	
	@Override
	public T getResult() {
		return this.friend;
	}

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return false;
	}
}
