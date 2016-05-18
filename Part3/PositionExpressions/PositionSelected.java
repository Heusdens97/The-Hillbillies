package hillbillies.positionExpressions;

public class PositionSelected<T> extends Position<T> {

	public PositionSelected(){
		setPosition(null);
		//seq.add(this);
	}
	
	public void setPosition(T newposition){
		this.pos = newposition;
	}
	@Override
	public T getResult() {
		return (T) pos;
	}
	
	private T pos;
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof PositionSelected<?>)
				&& (getResult() == ((PositionSelected<T>) other).getResult());
	}

	public void execute() {
		setPosition((T)getUnit().getTask().getPosition());
	}
}
