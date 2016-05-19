package hillbillies.statements;	

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import hillbillies.model.*;

public abstract class Statement implements Serializable {
	
	public Unit unit;

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public abstract void execute();
	
	/**
	 * 
	 * Makes a deepclone of an object. We assume ....
	 * 
	 * source: http://alvinalexander.com/java/java-deep-clone-example-source-code
	 * 
	 * @return
	 */
	public Object deepClone() {
		   try {
		     ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     ObjectOutputStream oos = new ObjectOutputStream(baos);
		     oos.writeObject(this);
		     ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		     ObjectInputStream ois = new ObjectInputStream(bais);
		     return ois.readObject();
		   }
		   catch (Exception e) {
		     e.printStackTrace();
		     return null;
		   }
		 }
	
}
