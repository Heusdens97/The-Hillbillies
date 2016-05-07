package hillbillies.expressions;

public class And extends BinaryExpression {

	/**
	 * Initialize this new addition with given operands.
	 *
	 * @param  left
	 *         The left operand for this new addition.
	 * @param  right
	 *         The right operand for this new addition.
	 * @effect This new addition is initialized as a binary expression
	 *         with the given operands.
	 *       | super(left,right)
	 */
	public And(Expression left, Expression right) {
		super(left, right);
	}

	/**
	 * Return the value of this addition.
	 *
	 * @return The sum of the values of the operands of this addition.
	 *       | result ==
	 *       |   getLeftOperand().getValue() + 
	 *       |   getRightOperand().getValue()
	 */
	@Override
	public Boolean getResult() {
		return (Boolean)getLeftOperand().getResult() && (Boolean)getRightOperand().getResult();
	}

	/**
	 * Return the symbol representing the operator of this addition.
	 * 
	 * @return The string "+"
	 *       | result.equals("+")
	 */
	@Override
	public String getOperatorSymbol() {
		return "&&";
	}
}
