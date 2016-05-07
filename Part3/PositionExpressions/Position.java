package hillbillies.positionExpressions;

import java.util.Arrays;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expressions.BasicExpression;
import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Objects;

public abstract class Position extends BasicExpression {

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public boolean equals(Object other) {
	//	return Arrays.equals(this.getposition, other.getposition)
		return false;
	}
	
	// controleer bij uitvoer
	public Integer[] get(Set<?> set){
		for (Object obj: set){
			IntToInteger(((Objects) obj).getCubeCoordinate());
		}
		return null;
	}
	
	public Integer[] IntToInteger(int[] pos){
		int[] oldArray = pos;
		Integer[] newArray = new Integer[oldArray.length];
		int i = 0;
		for (int value : oldArray) {
		    newArray[i++] = Integer.valueOf(value);
		}
		return newArray;
	}
	
	/**
	 * 
	 * Check whether this Unit can have the given position as its position.
	 * 
	 * @param 	position
	 * 			The position of the unit	
	 * @return	true if the position is valid: the position is less than getMaxSize() and more or equal than 0
	 * 			| if (position[0]< getMaxSize() && position[0] >= 0) && (position[1]< getMaxSize() && position[1] >= 0) && (position[2]< getMaxSize() && position[2] >= 0)
	 * 			|	then result == true
	 * 			|	else result == false
	 */			
	@Raw
	public boolean isValidPosition(int[] position){
		return (position[0]< getMaxSize() && position[0] >= 0) && (position[1]< getMaxSize() && position[1] >= 0) && (position[2]< getMaxSize() && position[2] >= 0);
	}

	/**
	 * Return the maximal size of the game world.
	 * 
	 */
	@Basic
	public int getMaxSize(){
		return Math.max(Faction.world.getX(), Math.max(Faction.world.getY(),Faction.world.getZ()));
	}
}
