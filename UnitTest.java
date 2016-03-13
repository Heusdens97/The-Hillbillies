package hillbillies.tests.unit;

import static org.junit.Assert.*;
import static hillbillies.tests.util.PositionAsserts.assertDoublePositionEquals;
import static hillbillies.tests.util.PositionAsserts.assertIntegerPositionEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Unit;
import hillbillies.part1.facade.IFacade;
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
		Unit name = new Unit("James O'Hara",new int[] { 1, 2, 3 }, 50,50,50,50,false);
		assertEquals("James O'Hara",name.getName());
	}
	
	@Test(expected = ModelException.class)
	public void constructor_IllegalSetName() throws ModelException {
		new Unit("B@rt", new int[] { 1, 2, 3 },50,50,50,50,false);
	}
	
	@Test
	public void constructor_LegalPosition() throws ModelException {
		Unit position_0 = new Unit("Test",new int[] { 0, 0, 0 },50,50,50,50,false);
		assertDoublePositionEquals(0.5,0.5,0.5,position_0.getPosition());
		int n = Unit.getMaxSize()-1;
		Unit position_49 = new Unit("Test",new int[] {n, n, n},50,50,50,50,false);
		assertDoublePositionEquals(n+0.5,n+0.5,n+0.5,position_49.getPosition());
	}
	
	@Test (expected = ModelException.class)
	public void constructor_IllegalPosition() throws ModelException{
	  new Unit("Test", new int []{ Unit.getMaxSize(),Unit.getMaxSize(),Unit.getMaxSize()},50,50,50,50,false);
	}
	
	@Test
	public void constructor_LegalWeight() throws ModelException {
		Unit Weight_25 = new Unit("Test",new int[] { 0, 0, 0 },25,50,50,50,false);
		assertEquals(25,Weight_25.getWeight());
		Unit Weight_100 = new Unit("Test",new int[] { 0, 0, 0 },100,50,50,50,false);
		assertEquals(100,Weight_100.getWeight());
		
	}
	
	@Test
	public void constructor_IllegalWeight() throws ModelException{
		int weight = 101;
		Unit Weight = new Unit("Test",new int[] { 0, 0, 0 } ,weight,50,50,50,false);
		assertTrue(25 <= Weight.getWeight() && Weight.getWeight() <= 100);
	}
	
	@Test
	public void constructor_LegalAgility() throws ModelException {
		Unit Agility_25 = new Unit("Test",new int[] { 0, 0, 0 },50,25,50,50,false);
		assertEquals(25,Agility_25.getAgility());
		Unit Agility_100 = new Unit("Test",new int[] { 0, 0, 0 },50,100,50,50,false);
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
	public void constructor_LegalToughness() throws ModelException {
		Unit Toughness_25 = new Unit("Test",new int[] { 0, 0, 0 },50,50,50,25,false);
		assertEquals(25,Toughness_25.getToughness());
		Unit Toughness_100 = new Unit("Test",new int[] { 0, 0, 0 },50,50,50,100,false);
		assertEquals(100,Toughness_100.getToughness());
	}
	
	@Test
	public void constructor_IllegalToughness() throws ModelException{
		int toughness = 150;
		Unit Toughness = new Unit("Test",new int[] { 0, 0, 0 } ,50, toughness,50,50,false);
		assertTrue(25 <= Toughness.getToughness() && Toughness.getToughness() <= 100);
	}
	
	@Test
	public void constructor_LegalOrientation() throws ModelException{
		Unit name = new Unit("TestUnit",new int[] { 6, 1, 12 }, 77,33,90,60,false);
		assertEquals(Math.PI/2,name.getOrientation(),1e-10);
	}
	
	
	@Test
	public void constructor_LegalMoveToAdjacent() throws ModelException {
		Unit MoveToA = new Unit("Test",new int[] { 0, 0, 0 },50,50,50,50,false);
		MoveToA.moveToAdjacent(1, 0, 1);
		assertEquals(0.75,MoveToA.getSpeed(),1e-2);
		double speed = MoveToA.getSpeed();
		double distance = Math.sqrt(2);
		double time = distance / speed;
		advanceTimeFor(MoveToA, time, 0.05);
		assertDoublePositionEquals(1.5,0.5,1.5,MoveToA.getPosition());
	}
	
	@Test
	public void constructor_IllegalMoveToAdjacent() throws ModelException {
		Unit MoveToA = new Unit("Test",new int[] { 0, 0, 0 },50,50,50,50,false);
		MoveToA.moveToAdjacent(-1, 0, 0);
		double distance = Math.sqrt(Math.pow((-1-0),2)+Math.pow((0-0),2)+Math.pow((0-0),2));
		double time = distance;
		advanceTimeFor(MoveToA, time, 0.05);
		assertDoublePositionEquals(0.5,0.5,0.5,MoveToA.getPosition());
	}
	
	@Test
	public void constructor_LegalMoveTo() throws ModelException {
		Unit MoveTo = new Unit("Test",new int[] { 0, 0, 0 },50,50,50,50,false);
		MoveTo.moveTo(new int[] { 25, 25, 25 });
		assertTrue(MoveTo.isMoving());
		MoveTo.startSprinting();
		assertTrue(MoveTo.isSprinting());
		double speed = MoveTo.getSpeed();
		double distance = Math.sqrt(Math.pow((49-0),2)+Math.pow((49-0),2)+Math.pow((49-0),2));
		double time = distance / speed;
		advanceTimeFor(MoveTo, time, 0.05);
		assertDoublePositionEquals(25.5,25.5,25.5,MoveTo.getPosition());
	}
	
	@Test
	public void constructor_IllegalMoveTo() throws ModelException {
		Unit MoveTo = new Unit("Test",new int[] { 3, 1, 5 },50,50,50,50,false);
		MoveTo.moveTo(new int[] { -1, 50, 0 });
		double distance = Math.sqrt(Math.pow((-1-3),2)+Math.pow((1-50),2)+Math.pow((0-5),2));
		double time = distance;
		advanceTimeFor(MoveTo, time, 0.05);
		assertDoublePositionEquals(3.5,1.5,5.5,MoveTo.getPosition());
	}
	
	@Test
	public void constructor_LegalWork() throws ModelException {
		Unit work = new Unit("Test",new int[] { 3, 1, 5 },50,50,50,50,false);
		work.work();
		double time = 500/(double)work.getStrength();
		assertTrue(work.isWorking());
		advanceTimeFor(work, time, 0.05);
		assertFalse(work.isWorking());
	}
	
	@Test
	public void constructor_getCubeCoordinates() throws ModelException {
		Unit cube = new Unit("Test",new int[] { 10, 12, 33 },50,50,50,50,false);
		assertIntegerPositionEquals(10,12,33,cube.getCubeCoordinate());
	}
	
	@Test
	public void constructor_Fight() throws ModelException {
		Unit attacker = new Unit("Attack",new int[] { 3, 1, 0 },50,50,50,50,false);
		Unit defender = new Unit("Defend",new int[] { 3, 2, 0 },50,50,50,50,false);
		attacker.fight(defender);
		assertTrue(attacker.isAttacking());
		assertTrue((50 == defender.getHitpoints()) || (45 == defender.getHitpoints()));
		assertTrue((attacker.getPosition() == defender.getPosition()) || (attacker.getPosition() != defender.getPosition()));
		advanceTimeFor(attacker, 1, 0.05);
		advanceTimeFor(defender, 1, 0.05);
		assertFalse(attacker.isAttacking());
	}
	
	@Test
	public void constructor_rest() throws ModelException {
		Unit rest = new Unit("Rest",new int[] { 0,0, 0 },50,50,50,50,false);
		rest.moveTo(new int[]{5,2,7});
		rest.startSprinting();
		double speed = rest.getSpeed();
		double distance = Math.sqrt(Math.pow((10-0),2)+Math.pow((2-0),2)+Math.pow((12-0),2));
		double time = distance / speed;
		advanceTimeFor(rest, time, 0.05);
		assertDoublePositionEquals(5.5,2.5,7.5,rest.getPosition());
		rest.rest();
		assertTrue(rest.isResting());
		advanceTimeFor(rest, 50/((1/(double)2)/(1/(double)5)), 0.05);
		assertEquals(rest.getMaxStaminaAndHitPoints(),rest.getStamina());
		assertFalse(rest.isResting());
	}
	
	@Test
	public void constructor_DefaultBehaviourEnabled() throws ModelException {
		Unit defaultbehaviour = new Unit("Test",new int[] { 0,0, 0 },50,50,50,50,false);
		defaultbehaviour.setDefaultBehaviourEnabled(true);
		assertTrue(defaultbehaviour.isDefaultBehaviourEnabled());
		assertTrue(defaultbehaviour.isMoving()||defaultbehaviour.isResting()||defaultbehaviour.isWorking());	
	}
	
	@Test
	public void constructor_DefaultBehaviourDisabled() throws ModelException {
		Unit defaultbehaviour = new Unit("Test",new int[] { 0,0, 0 },50,50,50,50,false);
		defaultbehaviour.setDefaultBehaviourEnabled(false);
		assertFalse(defaultbehaviour.isDefaultBehaviourEnabled());
	}
	
	private static void advanceTimeFor(Unit unit, double time, double step) throws ModelException {
		int n = (int) (time / step);
		for (int i = 0; i < n; i++)
			unit.advanceTime(step);
		unit.advanceTime(time - n * step);
	}
}