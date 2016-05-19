package hillbillies.model;
/**
 * 
 * A class of logs that extends the class Objects.
 *
 * @author  Bart Jacobs and Jordy Heusdens
 * @version 1.0
 */
public class Log extends Objects {
	
	/**
	 * 
	 * @param world
	 * 		This is the world in which the log exists.
	 * @param position
	 * 		The position within the world where the log is located.
	 */
	public Log(World world, int[] position){
		super(world,position);
		world.logs.add(this);
	}
	
}
