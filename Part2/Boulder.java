package hillbillies.model;


public class Boulder extends Objects{

	public Boulder(World world, int[] position) {
		super(world,position);
		world.boulders.add(this);
	}
	
//	private World world;
//	
//	private World getWorld(){
//		return this.world;
//	}
//	
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
//	
//	public int getWeight(){
//		return this.weight;
//	}
//	
//	private double[] position;
//	
//	private int max = 50;
//	private int min = 10;
//	
//	private final int weight = new Random().nextInt((max - min) + 1) + min;
//	
//	private boolean isValidPosition(double[] position){ // geeft nog error met z = 0 en is validpos werkt niet te goei
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
//	}
//	
//	public void advanceTime(double dt) throws ModelException {
//		if (isFalling()){
//			fall();
//		}
//	}
//	
//	private int speed;
//	private int z_falling;
//	
//	private void fall() throws ModelException {
//		if (!startfalling){
//			this.z_falling= this.getCubeCoordinate()[2];
//		}
//		this.startfalling = true;
//		int[] posUnder = {this.getCubeCoordinate()[0],this.getCubeCoordinate()[1],this.getCubeCoordinate()[2]-1};
//		if (world.isPassableTerrain(posUnder)){
//			moveToAdjacent(0, 0, -1);
//			this.sprinting = false;
//		} else if ((world.isImpassableTerrain(posUnder))||(this.getCubeCoordinate()[2]==0)){
//			this.startfalling = false;
//			int dz = this.z_falling - this.getCubeCoordinate()[2];
//			int hitpoints = this.getHitpoints()-(dz*10);
//			if (hitpoints < 0)
//				setHitpoints(0);
//			else {
//				setHitpoints(hitpoints);
//			}
//		}
//	}
}
