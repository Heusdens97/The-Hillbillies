package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.statements.Statement;

public abstract class Expression<T> extends Statement{
	
	/**
	 * Return the value of this expression.
	 */
	public abstract T getResult();

//	/**
//	 * Check whether this expression is equal to the given object.
//	 * 
//	 * @return If the given object is the same as this expression,
//	 *         always true.
//	 *       | if (this == other)
//	 *       |   then result == true
//	 * @return If the given object is not effective, always false.
//	 *       | if (other == null)
//	 *       |   then result == false
//	 * @return If this expression and the given object do not belong
//	 *         to the same (concrete) class, always false.
//	 *       | if (getClass() != other.getClass())
//	 *       |   then result == false
//	 * @return If the given object is an expression and its value
//	 *         differs from the value of this expression, always false.
//	 *       | if ( (other instanceof Expression) &&
//	 *       |      (getValue() != ((Expression)other).getValue()) )
//	 *       |   then result == false
//	 * @return If this expression is mutable, true if and only if this
//	 *         expression and the given object are the same.
//	 *       | if (this.isMutable())
//	 *       |   then result == (this == other)
//	 * @note   We could have worked out a partial implementation at
//	 *         this level, as we do for the method isIdenticalTo.
//	 */
//	@Override
//	public abstract boolean equals(Object other);
	
	
	//mogelijkheden:
	//	- extra functie toevoegen getBoolean voor de composedExpressions
	//	- principe van liskov toepassen op getValue met Object, Long en Boolean
	//  - generics
	//  - interfaces maken met generics voor Unit, Position, etc en dan iplements I...<Unit>

}
