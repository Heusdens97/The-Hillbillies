package hillbillies.model;

import java.util.Arrays;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import ogp.framework.util.ModelException;
/**
 * 
 * A class of objects, this the super class of Boulder and Log.
 *
 * @author  Bart Jacobs and Jordy Heusdens
 * @version 1.0
 */
public abstract class Objects {
	/**
	 * Create an object with the following parameters
	 * @param world
	 * 		The world we are currently using
	 * @param position
	 * 		The position where the object should be
	 */
	protected Objects(World world, int[] position){
		this.world = world;
		double[] doubleposition = {position[0]+0.5,position[1]+0.5,position[2]+0.5};
		setPosition(doubleposition);
	}
	
	private World world;
	
	private World getWorld(){
		return this.world;
	}
	/**
	 * 
	 * @param position
	 *		The position where the object should be.
	 * @post
	 * 		|if(position is not valid)
	 * 		|	the object will fall untill it reaches a valid position.
	 */
	public void setPosition(double[] position){
		this.position = position;
		if (!isValidPosition(position))
			this.fall();
	}
	/**
	 * 
	 * @return the position of the object.
	 */
	public double[] getPosition(){
		return this.position;
	}
	
	private double[] position;
	
	private boolean isValidPosition(double[] position){
		int[] pos = {(int)position[0], (int)position[1],(int) position[2]};
		int[] posUnder = {pos[0],pos[1],pos[2]-1};
		if (getWorld().isPassableTerrain(pos)){
			if (pos[2] == 0)
				return true;
			else{
				return getWorld().isImpassableTerrain(posUnder);
			}
		}
		return false;

	}
	/**
	 * 
	 * @return the weight of an object.
	 */
	public int getWeight(){
		return this.weight;
	}
	
	private int max = 50;
	private int min = 10;
	
	private final int weight = new Random().nextInt((max - min) + 1) + min;

	/**
	 * 
	 * @param dt
	 * 		Time that passed since the last advancetime call.
	 * @post
	 * 		|if(current position != validposition)
	 * 		|	The object will start falling
	 * 		|if(position!=destiny)
	 * 		|	continue falling
	 * 		|else
	 * 		|	stop falling
	 */
	public void advanceTime(double dt) {
		if ((isFalling())||(!isValidPosition(this.getPosition()))){
			fall();
		}
		if ((this.getPosition() != null)&&(this.getDestiny()!=null)){
			if (!Arrays.equals(this.getPosition(), this.getDestiny())){
				double d = Math.sqrt(Math.pow((this.getDestiny()[0]-this.getPosition()[0]),2)+Math.pow((this.getDestiny()[1]-this.getPosition()[1]),2)+Math.pow((this.getDestiny()[2]-this.getPosition()[2]),2));
				double[] v = {this.getSpeed()*((this.getDestiny()[0]-this.getPosition()[0])/(double)d),this.getSpeed()*((this.getDestiny()[1]-this.getPosition()[1])/(double)d),this.getSpeed()*((this.getDestiny()[2]-this.getPosition()[2])/(double)d)};
				double[] New = {this.getPosition()[0] + v[0]*dt,this.getPosition()[1] + v[1]*dt,this.getPosition()[2] + v[2]*dt};
				setPosition(New);
			}
			if ((Unit.round(this.getPosition()[0],1) == this.getDestiny()[0])&&(Unit.round(this.getPosition()[1],1) == this.getDestiny()[1])&&(Unit.round(this.getPosition()[2],1) == this.getDestiny()[2])){
				this.position = this.getDestiny();	
				setSpeed(0);
			}
		}
	}
	
	private boolean falling;
	
	private boolean isFalling(){
		return this.falling;
	}
	
	/**
	 * 
	 * @return the int coordinate of an object.
	 */
	@Basic @Raw
	public int[] getCubeCoordinate() {
		return new int[] {(int) this.getPosition()[0],(int) this.getPosition()[1],(int) this.getPosition()[2]};
	}
	
	private void fall() {
		int[] posUnder = {this.getCubeCoordinate()[0],this.getCubeCoordinate()[1],this.getCubeCoordinate()[2]-1};
		if (world.isPassableTerrain(posUnder)){
			setSpeed(3);
			moveDown(-1);
			this.falling = true;
		} else if ((world.isImpassableTerrain(posUnder))||(this.getCubeCoordinate()[2]==0)){
			this.falling = false;
		}
	}
	
	/**
	 * 
	 * The unit moves to the adjacent position
	 * 
	 * @param 	dx
	 * 			position will be the position + dx
	 * @param 	dy
	 * 			position will be the position + dy
	 * @param 	dz
	 * 			position will be the position + dz
	 * @throws 	ModelException
	 * 			If it's not a valid position.
	 */
	private void moveDown(int dz){
		if (!isFalling()){
			double[] Adjacent = {this.getPosition()[0],this.getPosition()[1],this.getPosition()[2]+dz};
			int[] intAdjacent = {(int)Adjacent[0],(int)Adjacent[1],(int)Adjacent[2]};
			if ((getWorld().isPassableTerrain(intAdjacent)||intAdjacent[2]==0)){
				setDestiny(Adjacent);
			}
		}
	}
	
	/**
	 * Set the destiny of this unit to the position witch is given.
	 * 
	 * @param  	position
	 * 			| the destiny of the unit
	 *
	 * @post   	if it's a valid position, it will be the destiny
	 * 			| if (isValidPosition(position)
	 * 			|	then this.destiny = position
	 * 			|	
	 */
	@Raw
	private void setDestiny(double[] position){
		if (isValidPosition(position))
			this.destiny = position;
	}
	
	/**
	 * Return the destiny of this unit.
	 */
	@Basic @Raw
	private double[] getDestiny(){
		return this.destiny;
	}
	
	private double[] destiny;
	
	private int speed;
	
	private int getSpeed(){
		return this.speed;
	}
	
	private void setSpeed(int speed){
		this.speed = speed;
	}
	
}
