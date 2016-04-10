package hillbillies.model;

public abstract class Objects {

	protected Objects(World world, int[] position){
		this.world = world;
		double[] doubleposition = {position[0]+0.5,position[1]+0.5,position[2]+0.5};
		setPosition(doubleposition);
	}
	
	private World world;
	
	private World getWorld(){
		return this.world;
	}
	public void setPosition(double[] position){
		this.position = position;
		if (!isValidPosition(position))
			this.fall();
	}
	
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
	
	public int getWeight(){
		return this.weight;
	}
	
	private int max = 50;
	private int min = 10;
	
	public void advanceTime(double dt) {
		if (isFalling()){
			fall();
		}
	}
	
	private void fall() {
		if (!startfalling){
			this.z_falling= this.getCubeCoordinate()[2];

		this.startfalling = true;
		int[] posUnder = {this.getCubeCoordinate()[0],this.getCubeCoordinate()[1],this.getCubeCoordinate()[2]-1};
		if (world.isPassableTerrain(posUnder)){
			moveToAdjacent(0, 0, -1);
			this.sprinting = false;
		} else if ((world.isImpassableTerrain(posUnder))||(this.getCubeCoordinate()[2]==0)){
			this.startfalling = false;
			int dz = this.z_falling - this.getCubeCoordinate()[2];
			int hitpoints = this.getHitpoints()-(dz*10);
			if (hitpoints < 0)
				setHitpoints(0);
			else {
				setHitpoints(hitpoints);
			}
		}
	}
	
	private int speed;
	
	private final int weight = new Random().nextInt((max - min) + 1) + min;
}
}
