package hillbillies.model;

/**
 * 
 * A class of boulders that extends the class Objects.
 *
 * @author  Bart Jacobs and Jordy Heusdens
 * @version 1.0
 */
public class Boulder extends Objects{
/**
 * 
 * @param world
 * 		This is the world in which the boulder exists.
 * @param position
 * 		The position within the world where the boulder is located.
 */
	public Boulder(World world, int[] position) {
		super(world,position);
		world.boulders.add(this);
	}
}
