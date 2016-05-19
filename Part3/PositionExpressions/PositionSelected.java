package hillbillies.positionExpressions;

public class PositionSelected<T> extends Position<T> {
	
	public void setPosition(T newposition){
		this.pos = newposition;
	}
	@Override
	public T getResult() {
		return (T) pos;
	}
	
	private T pos;

	public void execute() {
		setPosition((T)getUnit().getTask().getPosition());
	}
}
