package hillbillies.statements;

import java.util.ArrayList;
import java.util.Iterator;
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
			for (Iterator<Statement> i = sequence.iterator(); i.hasNext();) {
				if (!getUnit().isExecutingTask()){
					Statement s = i.next();
					s.unit = unit;
//					if (!(s instanceof StatementSequence)) {
//						s.execute();
//					} else {
//						getUnit().getTask().sequence.add(s);
//					}
					if (!(s instanceof StatementSequence)){
						s.execute();
						i.remove();
					} else if (s instanceof StatementSequence){
						getUnit().getTask().sequence.add(s);
						if (((StatementSequence) s).getSequence().isEmpty()){
							i.remove();
						}
					}
				} else {
					break;
				}
			}
		}
}
