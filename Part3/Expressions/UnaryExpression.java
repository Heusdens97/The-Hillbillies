package hillbillies.expressions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class UnaryExpression extends ComposedExpression {

	/**
	 * Initialize this new unary expression with given operand.
	 *
	 * @param  operand
	 *         The operand for this new unary expression.
	 * @post   The operand for this new unary expression is the
	 *         same as the given operand.
	 *       | new.getOperand() == operand
	 * @throws IllegalOperandException
	 *         This new unary expression cannot have the given
	 *         operand as its operand.
	 *       | ! canHaveAsOperand(operand)
	 */
	@Model
	protected UnaryExpression(Expression operand){
//		if (!canHaveAsOperand(operand))
//			throw new IllegalOperandException(this, operand);
		setOperandAt(1, operand);
	}

	/**
	 * Return the number of operands involved in this unary expression.
	 *
	 * @return A unary expression always involves a single operand.
	 *       | result == 1
	 */
	@Override
	@Basic
	public final int getNbOperands() {
		return 1;
	}

	/**
	 * Check whether this unary expression can have the given
	 * number as its number of operands.
	 *
	 * @return True if and only if the given number is 1.
	 *       | result == (number == 1)
	 */
	@Override
	@Raw
	public final boolean canHaveAsNbOperands(int number) {
		return number == 1;
	}

	/**
	 * Return the operand of this unary expression at the given index.
	 * 
	 * @return The one and only operand of this unary expression.
	 *       | result == getOperand()
	 */
	@Override
	public final Expression getOperandAt(int index)
			throws IndexOutOfBoundsException {
		if (index != 1)
			throw new IndexOutOfBoundsException();
		return getOperand();
	}

	/**
	 * Return the operand of this unary expression.
	 */
	@Basic
	public Expression getOperand() {
		return operand;
	}

	/**
	 * Set the operand for this unary expression at the given
	 * index to the given operand.
	 */
	@Override
	protected void setOperandAt(int index, Expression operand) {
		this.operand = operand;
	}

	/**
	 * Variable referencing the operand of this unary expression.
	 * 
	 * @note    This variable is not qualified final, such that operands
	 *          can be changed in cloning unary expressions.
	 */
	private Expression operand;
}
