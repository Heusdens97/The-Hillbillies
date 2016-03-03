package hillbillies.tests.unit;

import static org.junit.Assert.*;
import static hillbillies.tests.util.PositionAsserts.assertDoublePositionEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Unit;
import ogp.framework.util.ModelException;

public class UnitTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructor_LegalName() throws ModelException {
		Unit name = new Unit("James O'Hara",new int[] { 1, 2, 3 }, 50,50,50,50);
		assertEquals("James O'Hara",name.getName());
	}
	
	@Test(expected = ModelException.class)
	public void constructor_IllegalName() throws ModelException {
		new Unit("B@rt", new int[] { 1, 2, 3 },50,50,50,50);
	}
	
	@Test
	public void constructor_LegalPosition() throws ModelException {
		Unit position_0 = new Unit("Test",new int[] { 0, 0, 0 },50,50,50,50);
		assertDoublePositionEquals(0.5,0.5,0.5,position_0.getPosition());
		int n = Unit.getMaxSize()-1;
		Unit position_49 = new Unit("Test",new int[] {n, n, n},50,50,50,50);
		assertDoublePositionEquals(n+0.5,n+0.5,n+0.5,position_49.getPosition());
	}
	
	@Test (expected = ModelException.class)
	public void constructor_IllegalPosition() throws ModelException{
	  new Unit("Test", new int []{ Unit.getMaxSize(),Unit.getMaxSize(),Unit.getMaxSize()},50,50,50,50);
	}
	
	@Test
	public void constructor_LegalWeight() throws ModelException {
		Unit Weight_25 = new Unit("Test",new int[] { 0, 0, 0 },25,50,50,50);
		assertEquals(25,Weight_25.getWeight());
		Unit Weight_100 = new Unit("Test",new int[] { 0, 0, 0 },100,50,50,50);
		assertEquals(100,Weight_100.getWeight());
		
	}
	
	@Test
	public void constructor_IllegalWeight() throws ModelException{
		int weight = 101;
		Unit Weight = new Unit("Test",new int[] { 0, 0, 0 } ,weight,50,50,50);
		assertTrue(25 <= Weight.getWeight() && Weight.getWeight() <= 100);
	}
	
	@Test
	public void constructor_LegalAgility() throws ModelException {
		Unit Agility_25 = new Unit("Test",new int[] { 0, 0, 0 },50,25,50,50);
		assertEquals(25,Agility_25.getAgility());
		Unit Agility_100 = new Unit("Test",new int[] { 0, 0, 0 },50,100,50,50);
		assertEquals(100,Agility_100.getAgility());
	}
	
	@Test
	public void constructor_IllegalAgility() throws ModelException{
		int agility = 200;
		Unit Agility = new Unit("Test",new int[] { 0, 0, 0 } ,50, agility,50,50,false);
		assertTrue(25 <= Agility.getAgility() && Agility.getAgility() <= 100);
	}
	
	@Test
	public void constructor_LegalStrength() throws ModelException {
		Unit strength_25 = new Unit("Test",new int[] { 0, 0, 0 },50,50,25,50,false);
		assertEquals(25,strength_25.getStrength());
		Unit strength_100 = new Unit("Test",new int[] { 0, 0, 0 },50,50,100,50,false);
		assertEquals(100,strength_100.getStrength());
	}
	
	@Test
	public void constructor_IllegalStrength() throws ModelException{
		int strength = 150;
		Unit Strength = new Unit("Test",new int[] { 0, 0, 0 } ,50, strength,50,50,false);
		assertTrue(25 <= Strength.getStrength() && Strength.getStrength() <= 100);
	}
	
	@Test
	public void constructor_LegalMoveTo() throws ModelException {
		Unit MoveTo = new Unit("Test",new int[] { 0, 0, 0 },50,50,50,50,false);
		int[] cube = {49,40,19};
		MoveTo.moveTo(cube);
		assertDoublePositionEquals(49.5,40.5,19.5,MoveTo.getPosition());
		
	}
	
	@Test
	public void constructor_LegalMoveToAdjacent() throws ModelException {
		Unit MoveTo = new Unit("Test",new int[] { 0, 0, 0 },50,50,50,50,false);
		MoveTo.moveToAdjacent(1, 0, 1);
		assertDoublePositionEquals(1.5,0.5,1.5,MoveTo.getPosition());
		
	}
}