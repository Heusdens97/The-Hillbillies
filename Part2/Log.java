package hillbillies.model;

public class Log extends Objects {
	
	public Log(World world, int[] position){
		super(world,position);
		world.logs.add(this);
	}
	
//	private World world;
//	
//	private World getWorld(){
//		return this.world;
//	}
//	public void setPosition(double[] position){
//		if (!isValidPosition(position)){
//			//fall
//		} else {
//			this.position = position;
//		}
//	}
//	
//	public double[] getPosition(){
//		return this.position;
//	}
//	
//	private double[] position;
//	
//	private boolean isValidPosition(double[] position){
//		int[] pos = {(int)position[0], (int)position[1],(int) position[2]};
//		int[] posUnder = {pos[0],pos[1],pos[2]-1};
//		if (getWorld().isPassableTerrain(pos)){
//			if (pos[2] == 0)
//				return true;
//			else{
//				return getWorld().isImpassableTerrain(posUnder);
//			}
//		}
//		return false;
//
//	}
//	
//	public int getWeight(){
//		return this.weight;
//	}
//	
//	private int max = 50;
//	private int min = 10;
//	
//	public void advanceTime(double dt) throws ModelException {
//		if (isFalling()){
//			fall();
//		}
//	}
//	
//	private final int weight = new Random().nextInt((max - min) + 1) + min;
}
