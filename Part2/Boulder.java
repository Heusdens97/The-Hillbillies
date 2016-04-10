package hillbillies.model;


public class Boulder extends Objects{

	public Boulder(World world, int[] position) {
		super(world,position);
		world.boulders.add(this);
	}
}
