package hillbillies.statements;

import hillbillies.expressions.Expression;

public class StatementAssignment extends Statement {

	public StatementAssignment(String variableName, Expression<?> value) {
		setValue(value);
		setVariableName(variableName);
	}
	
	private String variableName;
	private Expression<?> value;
	
	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public Expression<?> getValue() {
		return value;
	}

	public void setValue(Expression<?> value) {
		this.value = value;
	}
	
	public void execute(){
		getValue().unit = unit;
		getValue().execute();
		getUnit().getTask().assignment.put(getVariableName(), getValue());
	}
}
