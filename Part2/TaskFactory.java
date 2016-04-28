package hillbillies.model;

import java.util.ArrayList;
import java.util.List;

import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;

public class TaskFactory implements ITaskFactory<MyExpression, MyStatement, Task>{

	@Override
	public List<Task> createTasks(String name, int priority, MyStatement activity, List<int[]> selectedCubes) {
		List<Task> adder = new ArrayList<Task>();
		for (int[] pos: selectedCubes){
			adder.add(new Task(name,priority,activity,pos));
		}
		//zie comment selected
		return adder;
	}

	@Override
	public MyStatement createAssignment(String variableName, MyExpression value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyStatement createWhile(MyExpression condition, MyStatement body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyStatement createIf(MyExpression condition, MyStatement ifBody, MyStatement elseBody,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyStatement createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyStatement createPrint(MyExpression value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyStatement createSequence(List<MyStatement> statements, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyStatement createMoveTo(MyExpression position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyStatement createWork(MyExpression position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyStatement createFollow(MyExpression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyStatement createAttack(MyExpression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createReadVariable(String variableName, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createIsSolid(MyExpression position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createIsPassable(MyExpression position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createIsFriend(MyExpression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createIsEnemy(MyExpression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createIsAlive(MyExpression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createCarriesItem(MyExpression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createNot(MyExpression expression, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createAnd(MyExpression left, MyExpression right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createOr(MyExpression left, MyExpression right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createHerePosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createLogPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createBoulderPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createWorkshopPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createSelectedPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createNextToPosition(MyExpression position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createThis(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createFriend(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createEnemy(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createAny(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createTrue(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createFalse(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyExpression createPositionOf(MyExpression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
