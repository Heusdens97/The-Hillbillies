package hillbillies.positionExpressions;


public class PositionXYZ<T> extends Position<T> {

	public PositionXYZ(int x, int y, int z){
		int[] pos = {x,y,z};
		setPos(pos);
	}
	
	private T position;
	private int[] pos;
	
	public int[] getPos() {
		return pos;
	}
	public void setPos(int[] pos) {
		this.pos = pos;
	}
	@Override
	public T getResult() {
		return this.position;
	}
	@Override
	public boolean equals(Object other) {
		return (other instanceof PositionXYZ<?>)
				&& (getResult() == ((PositionXYZ<T>) other).getResult());
	}
	@Override
	public void execute() {
		this.position = (T) IntToInteger(getPos());
	}

	
}
