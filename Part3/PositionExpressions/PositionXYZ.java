package hillbillies.positionExpressions;


public class PositionXYZ<T> extends Position<T> {

	public PositionXYZ(int x, int y, int z){
		int[] pos = {x,y,z};
		this.position = (T) IntToInteger(pos);
	}
	
	public T position;
	
	@Override
	public T getResult() {
		return this.position;
	}
	@Override
	public boolean equals(Object other) {
		return (other instanceof PositionXYZ<?>)
				&& (getResult() == ((PositionXYZ<T>) other).getResult());
	}

	
}
