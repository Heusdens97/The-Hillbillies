package hillbillies.statements;

import java.util.ArrayList;
import java.util.List;

public class StatementSequence extends Statement {

	public StatementSequence(List<Statement> statements) {
		setSequence(statements);
	}

	private List<Statement> sequence;
	
	public List<Statement> getSequence() {
		return sequence;
	}

	public void setSequence(List<Statement> sequence) {
		this.sequence = sequence;
	}

	@Override
	public void execute() { 
		for (Statement s: sequence){
			if (executing == null){
				s.unit = unit;
				s.execute();
				if (s instanceof StatementWithPosition || s instanceof StatementWithUnit )
					this.executing = s;
			}
		}
	}
}
