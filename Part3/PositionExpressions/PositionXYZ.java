package hillbillies.positionExpressions;


public class PositionXYZ extends Position {

	public PositionXYZ(int x, int y, int z){
		int[] pos = {x,y,z};
		this.position = IntToInteger(pos);
	}
	
	public Integer[] position;
	@Override
	public Integer[] getResult() {
		return this.position;
	}

	
}
