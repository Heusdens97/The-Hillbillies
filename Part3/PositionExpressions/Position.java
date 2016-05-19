package hillbillies.positionExpressions;

import java.util.Arrays;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expressions.Expression;
import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Objects;

public abstract class Position<T> extends Expression<T> {

	
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
		return (position[0]< getUnit().getWorld().getX() && position[0] >= 0) && (position[1]< getUnit().getWorld().getY() && position[1] >= 0) && (position[2]< getUnit().getWorld().getZ() && position[2] >= 0);
	}
	
	public double distanceOf(int[] pos1, int[] pos2){
		return Math.sqrt(Math.pow((pos2[0]-pos1[0]),2)+Math.pow((pos2[1]-pos1[1]),2)+Math.pow((pos2[2]-pos1[2]),2));
	}
}
