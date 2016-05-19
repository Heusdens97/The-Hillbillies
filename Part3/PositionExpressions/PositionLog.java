package hillbillies.positionExpressions;

import hillbillies.model.*;
import ogp.framework.util.ModelException;

public class PositionLog<T> extends Position<T> {
	
	public T log;
	
	@Override
	public T getResult() {
		return this.log;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof PositionLog<?>)
				&& (getResult() == ((PositionLog<T>) other).getResult());
	}

	@Override
	public void execute() {
		if (getUnit().world.logs.isEmpty())
			try {
				throw new ModelException();
			} catch (ModelException e) {
				System.out.println("no log available");
				e.printStackTrace();
			}
		else{
			for (Log log : getUnit().world.logs){
				int[] oldArray = log.getCubeCoordinate();
				this.log = (T)(oldArray);
				break;
			}
		}
	}
	

}
