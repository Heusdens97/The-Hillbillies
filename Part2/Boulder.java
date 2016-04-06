package hillbillies.model;

import java.util.Random;

import ogp.framework.util.ModelException;

public class Boulder {

	public Boulder(World world, int[] position) throws ModelException{
		this.world = world;
		double[] doubleposition = {position[0]+0.5,position[1]+0.5,position[2]+0.5};
		setPosition(doubleposition);
		world.boulders.add(this);
		
	}
	
	private World world;
	
	private World getWorld(){
		return this.world;
	}
	
	private void setPosition(double[] position) throws ModelException{
		if (!isValidPosition(position)){
			throw new ModelException();
		}
		this.position = position;
	}
	
	public double[] getPosition(){
		return this.position;
	}
	
	
	public int getWeight(){
		return this.weight;
	}
	
	double[] position;
	
	private int max = 50;
	private int min = 10;
	
	final int weight = new Random().nextInt((max - min) + 1) + min;
	
	private boolean isValidPosition(double[] position){ // geeft nog error met z = 0 en is validpos werkt niet te goei
		int[] pos = {(int)position[0], (int)position[1],(int) position[2]};
		int[] posUnder = {pos[0],pos[1],pos[2]-1};
		return (getWorld().isPassableTerrain(pos)&&(!getWorld().isPassableTerrain(posUnder)));

	}
}
