package hillbillies.statements;	

import java.util.ArrayList;
import java.util.List;

import hillbillies.model.*;
import hillbillies.part2.facade.Facade;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

public abstract class Statement{
	
	public Unit unit;

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public abstract void execute();
	
	public Statement executing;
	
}
