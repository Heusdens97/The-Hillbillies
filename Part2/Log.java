package hillbillies.model;

public class Log extends Objects {
	
	public Log(World world, int[] position){
		super(world,position);
		world.logs.add(this);
	}
	
}
