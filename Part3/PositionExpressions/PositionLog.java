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
	public void execute() {
		if (getUnit().world.logs.isEmpty())
			try {
				throw new ModelException();
			} catch (ModelException e) {
				System.out.println("no log available");
				e.printStackTrace();
			}
		else{
			double distance = Double.POSITIVE_INFINITY;
			Log log = null;
			for (Log b : getUnit().world.logs){
				if (distanceOf(getUnit().getCubeCoordinate(), b.getCubeCoordinate())< distance){
					distance = distanceOf(getUnit().getCubeCoordinate(),b.getCubeCoordinate());
					log = b;
				}
			}
			int[] oldArray = log.getCubeCoordinate();
			this.log = (T)(oldArray);
		}
	}
	

}
