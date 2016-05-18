package hillbillies.statements;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public class StatementPrint extends Statement {

	public StatementPrint(Expression<?> value) {
		setValue(value);
	}
	
	private Expression<?> value;

	public Expression<?> getValue() {
		return value;
	}

	public void setValue(Expression<?> value) {
		this.value = value;
	}

	public void execute() {
		getValue().unit = unit;
		getValue().execute();
		System.out.println(value.getResult());
	}
	
	
}
