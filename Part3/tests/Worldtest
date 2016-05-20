package hillbillies.test.model.World;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.*;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.facade.IFacade;
import ogp.framework.util.ModelException;

public class worldTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private Facade facade;

	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;

	@Before
	public void setup() {
		this.facade = new Facade();
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testRandomUnit() throws ModelException {
		int[][][] types = new int[3][3][3];
		World world = new World(types, new DefaultTerrainChangeListener());
		Unit testunit = world.randomUnit(false);
		assertTrue(25 <= testunit.getWeight() && testunit.getWeight() <= 100);
		assertTrue(25 <= testunit.getAgility() && testunit.getAgility() <= 100);
		assertTrue(25 <= testunit.getStrength() && testunit.getStrength() <= 100);
		
	}
	@Test
	public void testBorder(){
		int[][][] types = new int[3][3][3];
		for (int i = 0; i < types.length; i++) {
			for (int j = 0; j < types.length; j++) {
				for (int k = 0; k < types.length; k++) {
					types[i][j][k] = TYPE_AIR;
				}
			}
		}
		types[2][1][1] = TYPE_ROCK;
		types[1][1][1] = TYPE_ROCK;
		int[] position = {1,1,1};
		World world = new World(types, new DefaultTerrainChangeListener());
		assertTrue(world.isSolidConnectedToBorder(1, 1, 1));
		assertTrue(world.isImpassableTerrain(position));
		assertFalse(world.isPassableTerrain(position));
	}
	@Test
	public void testBoulderandLogSet(){
		int[][][] types = new int[3][3][3];
		World world = new World(types, new DefaultTerrainChangeListener());
		int[] position = {1,1,1};
		double[] Dposition = {1.5,1.5,1.5};
		Boulder boulder = new Boulder(world, position);
		world.boulders.add(boulder);
		assertTrue(world.getBoulders().size() == 1);
		world.removeBoulder(Dposition);
		assertFalse(world.getBoulders().size() == 1);
	}

	@Test
	public void testCollapse(){
		int[][][] types = new int[3][3][3];
		types[1][1][1] = TYPE_ROCK;
		World world = new World(types, new DefaultTerrainChangeListener());
		int[] position = {1,1,1};
		assertFalse(world.isPassableTerrain(position));
		world.collapse(1, 1, 1);
		assertTrue(world.isPassableTerrain(position));
	}
//	@Test
//	public void testAdvancetime() throws ModelException{
//		int[][][] types = new int[2][2][2];
//		for (int j = 0; j < types.length; j++) {
//			for (int k = 0; k < types.length; k++) {
//				types[k][0][j] = TYPE_ROCK;
//			}
//		}
//		World world = new World(types, new DefaultTerrainChangeListener());
//		Unit unitToDie = world.randomUnit(false);
//		Unit unitKiller = world.randomUnit(false);
//		while (unitToDie.getHitpoints() > 0){
//			unitKiller.fight(unitToDie);
//			advanceTimeFor(facade, world, 1, 0.02);
//		}
//		assertFalse(!unitToDie.isAlive()|| !unitKiller.isAlive());
//	}
//		
		
	private static void advanceTimeFor(IFacade facade, World world, double time, double step) throws ModelException {
		int n = (int) (time / step);
		for (int i = 0; i < n; i++)
			facade.advanceTime(world, step);
		facade.advanceTime(world, time - n * step);
	}
}
