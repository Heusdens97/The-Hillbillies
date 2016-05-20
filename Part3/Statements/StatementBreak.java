package hillbillies.statements;

import java.util.List;

public class StatementBreak extends Statement {

	@Override
	public void execute() {
		List<Statement> sequence = getUnit().getTask().sequence;
		for (Statement s: sequence){
			if (s instanceof StatementBreak){
				for (Statement stat: sequence){
					if (stat instanceof StatementWhile){
						int index = sequence.indexOf(stat);
						sequence.remove(index);
					}
				}
			}
		}
	}

	
	
}
