package hillbillies.positionExpressions;

public class PositionSelected<T> extends Position<T> {

	@Override
	public T getResult() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof PositionSelected<?>)
				&& (getResult() == ((PositionSelected<T>) other).getResult());
	}
}
