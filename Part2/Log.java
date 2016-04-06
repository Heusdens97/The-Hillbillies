package hillbillies.model;

public class Log {
	
	public Log(World world, int[] position){
		double[] doubleposition = {position[0]+0.5,position[1]+0.5,position[2]+0.5};
		setPosition(doubleposition);
		world.logs.add(this);
	}
	
	private void setPosition(double[] position){
		this.position = position;
	}
	
	public double[] getPosition(){
		return this.position;
	}
	
	double[] position;
}
