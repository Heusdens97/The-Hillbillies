package hillbillies.expressions;

public class Or extends BinaryExpression {

	/**
	 * Initialize this new multiplication with given operands.
	 *
	 * @param  left
	 *         The left operand for this new multiplication.
	 * @param  right
	 *         The right operand for this new multiplication.
	 * @effect This new multiplication is initialized as a binary expression
	 *         with the given operands.
	 *       | super(left,right)
	 */
	public Or(Expression left, Expression right) {
		super(left, right);
	}

	/**
	 * Return the value of this multiplication.
	 *
	 * @return The product of the values of the operands of this addition.
	 *       | result ==
	 *       |   getLeftOperand().getValue() *
	 *       |   getRightOperand().getValue()
	 */
	@Override
	public Boolean getResult() {
		return (Boolean)getLeftOperand().getResult() || (Boolean)getRightOperand().getResult();
	}

	/**
	 * Return the symbol representing the operator of this multiplication.
	 * 
	 * @return The string "*"
	 *       | result.equals("*")
	 */
	@Override
	public String getOperatorSymbol() {
		return "||";
	}
}
