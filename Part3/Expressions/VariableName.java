package hillbillies.expressions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class VariableName<T> extends Expression<T> {

	/**
	 * Initialize this new integer  with given name.
	 *
	 * @param  name
	 *         The name for this new integer .
	 * @post   The name of this new integer  is equal to
	 *         the given name.
	 *       | new.getname() == name
	 */
	public VariableName(String name) {
		this.name = name;
	}
	
	/**
	 * Return the name of this integer .
	 */
	public String getName() {
		return name;
	}

	/**
	 * Variable registering the name of this integer .
	 */
	private final String name;
	
	private T result;

	@Override
	public void execute() {
		if (getUnit().getTask().assignment.containsKey(getName())){
			setResult((T)getUnit().getTask().assignment.get(getName()));
		}
		
	}

	public void setResult(T result) {
		this.result = result;
	}

	@Override
	public T getResult() {
		return this.result;
	}
}
