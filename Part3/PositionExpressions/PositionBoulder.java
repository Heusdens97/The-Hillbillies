package hillbillies.positionExpressions;


import hillbillies.model.*;
import ogp.framework.util.ModelException;

public class PositionBoulder<T> extends Position<T> {
	
	public T boulder;
	
	@Override
	public T getResult() {
		return this.boulder;
	}

	@Override
	public void execute() {
		if (getUnit().world.boulders.isEmpty())
			try {
				throw new ModelException();
			} catch (ModelException e) {
				System.out.println("no boulder available");
				e.printStackTrace();
			}
		else{
			double distance = Double.POSITIVE_INFINITY;
			Boulder boulder = null;
			for (Boulder b : getUnit().world.boulders){
				if (distanceOf(getUnit().getCubeCoordinate(), b.getCubeCoordinate())< distance){
					distance = distanceOf(getUnit().getCubeCoordinate(),b.getCubeCoordinate());
					boulder = b;
				}
			}
			int[] oldArray = boulder.getCubeCoordinate();
			this.boulder = (T)(oldArray);
		}
	}
	

}
