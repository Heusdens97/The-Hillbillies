package hillbillies.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hillbillies.expressions.*;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.positionExpressions.*;
import hillbillies.statements.*;
import hillbillies.unitExpressions.*;

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
		return new StatementAssignment(variableName, value);
	}

	@Override
	public Statement createWhile(Expression<?> condition, Statement body, SourceLocation sourceLocation) {
		return new StatementWhile(condition, body);
	}

	@Override
	public Statement createIf(Expression<?> condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		return new StatementIf(condition, ifBody, elseBody);
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createPrint(Expression<?> value, SourceLocation sourceLocation) {
		return new StatementPrint(value);
	}

	@Override
	public Statement createSequence(List<Statement> statements, SourceLocation sourceLocation) {
		return new StatementSequence(statements);
	}

	@Override
	public Statement createMoveTo(Expression<?> position, SourceLocation sourceLocation) {
		return new StatementWithPosition(position){
			@Override
			public void execute(){
				getExpression().unit = unit;
				getExpression().execute();
				setPosition((int[]) getExpression().getResult());
				if (!Arrays.equals(pos, getUnit().getCubeCoordinate()))
					unit.moveTo(pos);
			}
		};
	}

	@Override
	public Statement createWork(Expression<?> position, SourceLocation sourceLocation) {
		return new StatementWithPosition(position);
	}

	@Override
	public Statement createFollow(Expression<?> unit, SourceLocation sourceLocation) {
		return new StatementWithUnit(unit);
	}

	@Override
	public Statement createAttack(Expression<?> unit, SourceLocation sourceLocation) {
		return new StatementWithUnit(unit){
			@Override
			public void execute(){
				getExpression().unit = unit;
				getExpression().execute();
				setfollowUnit((Unit) getExpression().getResult());
				unit.fight(followUnit);
			}
		};
	}

	@Override
	public Expression<String> createReadVariable(String variableName, SourceLocation sourceLocation) {
		return new VariableName<String>(variableName);
	}

	@Override
	public Expression<?> createIsSolid(Expression<?> position, SourceLocation sourceLocation) {
		return new UnitAttribute<Boolean>(position){
			@Override
			public void execute() {
				getExpression().unit = unit;
				getExpression().execute();
				int[] pos = (int[]) getExpression().getResult();
				if (getUnit().world.isImpassableTerrain(pos)){
					setResult(true);
				} else {
					setResult(false);
				}
			}
		};
	}

	@Override
	public Expression<?> createIsPassable(Expression<?> position, SourceLocation sourceLocation) {
		return new UnitAttribute<Boolean>(position){
			@Override
			public void execute() {
				getExpression().unit = unit;
				getExpression().execute();
				int[] pos = (int[]) getExpression().getResult();
				if (getUnit().world.isPassableTerrain(pos)){
					setResult(true);
				} else {
					setResult(false);
				}
			}
		};
	}

	@Override
	public Expression<?> createIsFriend(Expression<?> unit, SourceLocation sourceLocation) {
		return new UnitAttribute<Boolean>(unit){
			@Override
			public void execute() {
				getExpression().unit = unit;
				getExpression().execute();
				Unit u = (Unit) getExpression().getResult();
				if (!getExpression().getUnit().isEnemy(u)){
					setResult(true);
				} else {
					setResult(false);
				}
			}
		};
	}

	@Override
	public Expression<?> createIsEnemy(Expression<?> unit, SourceLocation sourceLocation) {
		return new UnitAttribute<Boolean>(unit){
			@Override
			public void execute() {
				getExpression().unit = unit;
				getExpression().execute();
				Unit u = (Unit) getExpression().getResult();
				if (getExpression().getUnit().isEnemy(u)){
					setResult(true);
				} else {
					setResult(false);
				}
			}
		};
	}

	@Override
	public Expression<?> createIsAlive(Expression<?> unit, SourceLocation sourceLocation) {
		return new UnitAttribute<Boolean>(unit){
			@Override
			public void execute() {
				getExpression().unit = unit;
				getExpression().execute();
				Unit u = (Unit) getExpression().getResult();
				if (u.isAlive()){
					setResult(true);
				} else {
					setResult(false);
				}
			}
		};
	}

	@Override
	public Expression<?> createCarriesItem(Expression<?> unit, SourceLocation sourceLocation) {
		return new UnitAttribute<Boolean>(unit);
		
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
			// checken of equals werkt
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
