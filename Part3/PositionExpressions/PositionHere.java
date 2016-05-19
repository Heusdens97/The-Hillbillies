package hillbillies.positionExpressions;

import hillbillies.model.*;

public class PositionHere<T> extends Position<T> {

	public void setHere(T ds) {
		this.here =  ds;
	}

	public T here;
	
	@Override
	public T getResult() {
		return this.here;
	}

	@Override
	public void execute() {
		setHere((T)getUnit().getCubeCoordinate());
		
	}
	
	



}
