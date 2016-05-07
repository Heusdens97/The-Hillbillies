package hillbillies.expressions;

public abstract class BasicExpression extends Expression {

	/**
	 * Check whether this basic expression has the given expression as
	 * one of its subexpressions.
	 *
	 * @return  True if and only if the given expression is the same
	 *          expression as this basic expression.
	 *        | result == (expression == this)
	 */
	@Override
	public final boolean hasAsSubExpression(Expression expression) {
		return expression == this;
	}
}
