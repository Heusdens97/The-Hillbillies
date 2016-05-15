package hillbillies.model;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class Statement<T> {
	
	private Statement statement;

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}
}
