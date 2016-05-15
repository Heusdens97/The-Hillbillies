package hillbillies.model;

import java.util.ArrayList;
import java.util.List;

import hillbillies.expressions.*;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.positionExpressions.PositionBoulder;
import hillbillies.positionExpressions.PositionHere;
import hillbillies.positionExpressions.PositionLog;
import hillbillies.positionExpressions.PositionNextTo;
import hillbillies.positionExpressions.PositionOf;
import hillbillies.positionExpressions.PositionSelected;
import hillbillies.positionExpressions.PositionWorkshop;
import hillbillies.positionExpressions.PositionXYZ;
import hillbillies.unitExpressions.UnitAny;
import hillbillies.unitExpressions.UnitEnemy;
import hillbillies.unitExpressions.UnitFriend;
import hillbillies.unitExpressions.UnitThis;

public class TaskFactory implements ITaskFactory<Expression<?>, Statement, Task>{

	@Override
	public List<Task> createTasks(String name, int priority, Statement activity, List<int[]> selectedCubes) {
		List<Task> adder = new ArrayList<Task>();
		for (int[] pos: selectedCubes){	
			adder.add(new Task(name,priority,activity,pos));
		}
		//zie comment selected
		return adder;
	}

	@Override
	public Statement createAssignment(String variableName, Expression<?> value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createWhile(Expression<?> condition, Statement body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createIf(Expression<?> condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createPrint(Expression<?> value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSequence(List<Statement> statements, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createMoveTo(Expression<?> position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createWork(Expression<?> position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createFollow(Expression<?> unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createAttack(Expression<?> unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<String> createReadVariable(String variableName, SourceLocation sourceLocation) {
		return new VariableName<String>(variableName);
	}

	@Override
	public Expression<?> createIsSolid(Expression<?> position, SourceLocation sourceLocation) {
		int[] pos = (int[]) position.getResult();
		if (Faction.world.isImpassableTerrain(pos)){
			return createTrue(sourceLocation);
		} else {
			return createFalse(sourceLocation);
		}
	}

	@Override
	public Expression<?> createIsPassable(Expression<?> position, SourceLocation sourceLocation) {
		int[] pos = (int[]) position.getResult();
		if (Faction.world.isPassableTerrain(pos)){
			return createTrue(sourceLocation);
		} else {
			return createFalse(sourceLocation);
		}
	}

	@Override
	public Expression<?> createIsFriend(Expression<?> unit, SourceLocation sourceLocation) {
		Unit u = (Unit) unit.getResult();
		Expression<?> unitthis = createThis(sourceLocation);
		Unit u2 = (Unit) unitthis.getResult();
		if (u.isEnemy(u2)){
			return createFalse(sourceLocation);
		} else {
			return createTrue(sourceLocation);
		}
	}

	@Override
	public Expression<?> createIsEnemy(Expression<?> unit, SourceLocation sourceLocation) {
		Unit u = (Unit) unit.getResult();
		Expression<?> unitthis = createThis(sourceLocation);
		Unit u2 = (Unit) unitthis.getResult();
		if (u.isEnemy(u2)){
			return createTrue(sourceLocation);
		} else {
			return createFalse(sourceLocation);
		}
	}

	@Override
	public Expression<?> createIsAlive(Expression<?> unit, SourceLocation sourceLocation) {
		Unit u = (Unit) unit.getResult();
		if (u.isAlive()){
			return createTrue(sourceLocation);
		} else {
			return createFalse(sourceLocation);
		}
	}

	@Override
	public Expression<?> createCarriesItem(Expression<?> unit, SourceLocation sourceLocation) {
		Unit u = (Unit) unit.getResult();
		if (u.isCarryingBoulder() || u.isCarryingLog()){
			return createTrue(sourceLocation);
		} else {
			return createFalse(sourceLocation);
		}
		
	}

	@Override
	public Expression<?> createNot(Expression<?> expression, SourceLocation sourceLocation) {
		if (expression.getResult().equals(true)){
			return createFalse(sourceLocation);
		}else{
			return createTrue(sourceLocation);
		}
	}

	@Override
	public Expression<?> createAnd(Expression<?> left, Expression<?> right, SourceLocation sourceLocation) {
		if(left.getResult().equals(true)&& right.getResult().equals(true)){
			return createTrue(sourceLocation);
		} else {
			return createFalse(sourceLocation);
		}
	}

	@Override
	public Expression<?> createOr(Expression<?> left, Expression<?> right, SourceLocation sourceLocation) {
		if (left.getResult().equals(false)&& right.getResult().equals(false)){
			return createFalse(sourceLocation);
		} else {
			return createTrue(sourceLocation);
		}
	}

	@Override
	public Expression<?> createHerePosition(SourceLocation sourceLocation) {
		return new PositionHere<Integer[]>();
	}

	@Override
	public Expression<?> createLogPosition(SourceLocation sourceLocation) {
		return new PositionLog<Integer[]>();
	}

	@Override
	public Expression<?> createBoulderPosition(SourceLocation sourceLocation) {
		return new PositionBoulder<Integer[]>();
	}

	@Override
	public Expression<?> createWorkshopPosition(SourceLocation sourceLocation) {
		return new PositionWorkshop<Integer[]>();
	}

	@Override
	public Expression<?> createSelectedPosition(SourceLocation sourceLocation) {
		return new PositionSelected<Integer[]>();
	}

	@Override
	public Expression<?> createNextToPosition(Expression<?> position, SourceLocation sourceLocation) {
		return new PositionNextTo<Integer[]>(position);
	}

	@Override
	public Expression<?> createPositionOf(Expression<?> unit, SourceLocation sourceLocation) {
		return new PositionOf<Integer[]>(unit);
	}

	@Override
	public Expression<?> createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		return new PositionXYZ<>(x, y, z);
	}

	@Override
	public Expression<?> createThis(SourceLocation sourceLocation) {
		return new UnitThis<Unit>();
	}

	@Override
	public Expression<?> createFriend(SourceLocation sourceLocation) {
		return new UnitFriend<Unit>();
	}

	@Override
	public Expression<?> createEnemy(SourceLocation sourceLocation) {
		return new UnitEnemy<Unit>();
	}

	@Override
	public Expression<?> createAny(SourceLocation sourceLocation) {
		return new UnitAny<Unit>();
	}

	@Override
	public Expression<?> createTrue(SourceLocation sourceLocation) {
		return new BooleanExpression<Boolean>(true);
	}

	@Override
	public Expression<?> createFalse(SourceLocation sourceLocation) {
		return new BooleanExpression<Boolean>(false);
	}


	
}
