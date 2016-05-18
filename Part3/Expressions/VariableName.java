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
	public VariableName(T name) {
		this.name = name;
	}

	/**
	 * Return the name of this integer .
	 */
	@Override
	@Basic @Immutable
	public T getResult() {
		return name;
	}

	/**
	 * Variable registering the name of this integer .
	 */
	private final T name;

	/**
	 * Check whether this integer  is equal to the given
	 * object.
	 *
	 * @returnTrue if and only if the other object is an effective
	 *         integer , whose name is equal to the name
	 *         of this integer .
	 *       | result ==
	 *       |   (other instanceof Integer) &&
	 *       |   (this.getname() == ((Integer)other).getname())
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof VariableName<?>)
				&& (getResult() == ((VariableName<T>) other).getResult());
	}

	@Override
	public void execute() {
		//niet nodig;
		
	}
}
