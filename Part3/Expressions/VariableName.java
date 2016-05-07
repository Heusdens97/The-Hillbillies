package hillbillies.expressions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class VariableName extends BasicExpression {

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

//	/**
//	 * Initialize this new integer  with name 0.
//	 *
//	 * @effect This new integer  is initialized with 0
//	 *         as its name.
//	 *       | this(0)
//	 */
//	public Integer() {
//		// We must explicitly initialize the final instance variable name in
//		// this constructor, either in a direct way or in an indirect way.
//		this(0);
//	}
//
//	/**
//	 * Constant referencing a predefined integer  with name 0.
//	 * 
//	 * @invar  The constant references an effective integer ,
//	 *         whose name is 0.
//	 *       | ZERO.getname() == 0
//	 */
//	public final static Integer ZERO = new Integer();

	/**
	 * Return the name of this integer .
	 */
	@Override
	@Basic @Immutable
	public String getResult() {
		return name;
	}

	/**
	 * Variable registering the name of this integer .
	 */
	private final String name;

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
		return (other instanceof VariableName)
				&& (getResult() == ((VariableName) other).getResult());
	}

	/**
	 * Check whether the state of this integer  can be changed.
	 * 
	 * @return Always false.
	 *       | result == false
	 */
	@Override
	public final boolean isMutable() {
		return false;
	}
}
