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
	public boolean equals(Object other) {
		return (other instanceof PositionHere<?>)
				&& (getResult() == ((PositionHere<T>) other).getResult());
	}

	@Override
	public void execute() {
		setHere((T)getUnit().getCubeCoordinate());
		
	}
	
	



}
