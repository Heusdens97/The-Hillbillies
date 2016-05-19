package hillbillies.statements;

import java.util.List;

public class StatementBreak extends Statement {

	@Override
	public void execute() {
		List<Statement> sequence = getUnit().getTask().sequence;
		for (Statement s: sequence){
			if (s instanceof StatementBreak){
				int index = sequence.indexOf(s);
				sequence.remove(index+1);
			}
		}
	}

	
	
}
